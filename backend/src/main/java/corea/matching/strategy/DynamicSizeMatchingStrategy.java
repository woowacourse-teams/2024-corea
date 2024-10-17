package corea.matching.strategy;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.matching.domain.Pair;
import corea.member.domain.Member;
import corea.participation.domain.Participation;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Primary
@RequiredArgsConstructor
public class DynamicSizeMatchingStrategy implements MatchingStrategy {

    private final PlainRandomMatchingStrategy strategy;

    public List<Pair> matchPairs(List<Participation> participations, int roomMatchingSize) {
        List<Participation> reviewers = participations.stream()
                .filter(Participation::isReviewer)
                .toList();
        List<Participation> nonReviewers = participations.stream()
                .filter(Participation::isNotReviewer)
                .sorted(Comparator.comparing(Participation::getMatchingSize))
                .toList();
        validateNonReviewerSize(nonReviewers, roomMatchingSize);
        // MemberRole.REVIEWER 인 사람을 제외하고 기존 로직으로 roomMatchingSize 만큼 선 매칭
        List<Pair> pairs = strategy.matchPairs(nonReviewers, roomMatchingSize);
        // 이후 추가적으로 matchingSize 에 따라 매칭 시도
        handleAdditionalMatching(nonReviewers, roomMatchingSize, pairs);
        // TODO: 리뷰이 매칭 필요
        return pairs;
    }

    private void validateNonReviewerSize(List<Participation> participations, int roomMatchingSize) {
        if (participations.size() <= roomMatchingSize) {
            throw new CoreaException(ExceptionType.PARTICIPANT_SIZE_LACK);
        }
    }

    private void handleAdditionalMatching(List<Participation> nonReviewers, int roomMatchingSize, List<Pair> pairs) {
        List<Participation> participations = nonReviewers.stream().toList();
        // 참여자들의 matchingSize 중 최대값
        int max = getMaxMatchingSize(nonReviewers, roomMatchingSize);

        // currentMatchingSize 이상의 matchingSize 를 가진 참여자들끼리 추가적인 매칭을 참여자별로 1회씩 시도
        for (int currentMatchingSize = roomMatchingSize + 1; currentMatchingSize <= max; currentMatchingSize++) {
            // currentMatchingSize 미만의 matchingSize 를 가진 참여자 제외
            participations = filterUnderMatchedParticipants(participations, currentMatchingSize);

            if (participations.isEmpty()) {
                break;
            }

            // 지금 가능한 reviewers, reviewees 사이에 매칭 시도
            performAdditionalMatching(participations, pairs);

        }
    }

    // 참여자들의 matchingSize 중 가장 큰 값을 반환, participations 가 빈 경우 roomMatchingSize 를 반환해 이후 매칭 로직 돌지 않게 방지
    private int getMaxMatchingSize(List<Participation> participations, int roomMatchingSize) {
        return participations.stream()
                .mapToInt(Participation::getMatchingSize)
                .max()
                .orElse(roomMatchingSize);
    }

    // 현재 reviewees 를 기준으로, 모든 reviewee 가 한 번씩 현재 reviewers 에서 가능한 매칭을 시도
    private void performAdditionalMatching(List<Participation> participations, List<Pair> pairs) {
        List<Participation> reviewersArray = new ArrayList<>(participations);
        ArrayDeque<Member> reviewers = extractMember(reviewersArray);
        Collections.reverse(reviewersArray);
        ArrayDeque<Member> reviewees = extractMember(reviewersArray);

        // reviewee 를 한 명 뽑아 가능한 reviewer 검색
        while (!reviewees.isEmpty()) {
            Member reviewee = reviewees.pollFirst();
            tryToMatch(reviewers, reviewee, pairs);
        }
    }

    private ArrayDeque<Member> extractMember(List<Participation> participations) {
        return new ArrayDeque<>(participations.stream()
                .map(Participation::getMember)
                .toList());
    }

    // 선택된 reviewee 를 기준으로 reviewers 에서 리뷰가 가능한 참여자를 찾아 매칭
    private void tryToMatch(ArrayDeque<Member> reviewers, Member reviewee, List<Pair> pairs) {
        int reviewerSize = reviewers.size();
        for (int reviewerIndex = 0; reviewerIndex < reviewerSize; reviewerIndex++) {
            Member reviewer = reviewers.pollFirst();

            // reviewer와 reviewee 가 매칭이 가능한 경우 pairs 에 더해주고 메서드 종료
            if (isPossiblePair(reviewer, reviewee, pairs)) {
                pairs.add(new Pair(reviewer, reviewee));
                return;
            }

            // reviewer 와 reviewee 가 매칭이 불가능한 경우 이후 다른 reviewee 가 매칭을 시도할 수 있도록 해당 reviewer 를 다시 Deque tail 에 삽입
            reviewers.add(reviewer);
        }
    }

    //선택된 reviewer 와 reviewee 가 매칭 가능한 상태인지 판단
    private boolean isPossiblePair(Member reviewer, Member reviewee, List<Pair> pairs) {
        // reviewer 와 reviewee 가 동일한 경우 false 반환
        if (reviewer == reviewee) {
            return false;
        }

        // reviewer 와 reviewee 가 매칭된 기록이 있는 경우 false 반환, 없으면 true 반환
        return pairs.stream().noneMatch(
                pair -> pair.hasReviewer(reviewer) &&
                        pair.hasReviewee(reviewee));
    }

    // currentMatchingSize 미만의 matchingSize 를 가지는 참여자를 제외
    private List<Participation> filterUnderMatchedParticipants(List<Participation> participations, int currentMatchingSize) {
        return participations.stream()
                .filter(participation -> isUnderMatchedParticipants(participation, currentMatchingSize))
                .toList();
    }

    private boolean isUnderMatchedParticipants(Participation participation, int currentMatchingSize) {
        // MemberRole.REVIEWER 인 경우 제외
        if (participation.isReviewer()) {
            return false;
        }
        // MemberRole.BOTH 인 경우 matchingSize 와 currentMatchingSize 를 비교
        return participation.getMatchingSize() >= currentMatchingSize;
    }
}
