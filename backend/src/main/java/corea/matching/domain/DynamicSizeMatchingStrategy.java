package corea.matching.domain;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.member.domain.Member;
import corea.member.repository.MemberRepository;
import corea.participation.domain.Participation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DynamicSizeMatchingStrategy {

    private final MatchingStrategy strategy;

    private final MemberRepository memberRepository;

    public List<Pair> matchPairs(List<Participation> participations, int roomMatchingSize) {
        List<Participation> participationWithoutReviewer = participations.stream()
                .filter(participation -> !participation.getMemberRole().isReviewer()).toList();
        List<Pair> pairs = strategy.matchPairs(participationWithoutReviewer, roomMatchingSize);
        additionalMatching(participations, participationWithoutReviewer, pairs, roomMatchingSize);
        return pairs;
    }

    private void additionalMatching(List<Participation> participations, List<Participation> participationWithoutReviewer, List<Pair> pairs, int roomMatchingSize) {
        Map<Long, Member> memberCache = participations.stream()
                .map(participation -> memberRepository.findById(participation.getMemberId())
                        .orElseThrow(() -> new CoreaException(ExceptionType.MEMBER_NOT_FOUND)))
                .collect(Collectors.toMap(Member::getId, Function.identity()));

        ArrayDeque<Long> reviewerIds = getReviewerMemberId(participations, roomMatchingSize);

        List<Long> revieweeIds = new ArrayList<>(participationWithoutReviewer.stream().map(Participation::getMemberId).toList());

        Collections.shuffle(revieweeIds);
        int index = 0;
        int revieweeSize = revieweeIds.size();

        while (!reviewerIds.isEmpty()) {
            long reviewerId = reviewerIds.pollFirst();

            do {
                index = (index + 1) % revieweeSize;
            } while (!possiblePair(reviewerId, revieweeIds.get(index), pairs));

            pairs.add(new Pair(memberCache.get(reviewerId), memberCache.get(revieweeIds.get(index))));
        }
    }

    private ArrayDeque<Long> getReviewerMemberId(List<Participation> participations, int roomMatchingSize) {
        ArrayDeque<Long> reviewerIds = new ArrayDeque<>();

        int maxSize = Math.max(participations.stream()
                .map(Participation::getMatchingSize)
                .max(Integer::compareTo).orElse(0), roomMatchingSize);

        for (int i = 0; i < maxSize; i++) {
            for (Participation participation : participations) {
                // 리뷰어
                if (!participation.getMemberRole().isReviewer() && i < roomMatchingSize) {
                    continue;
                }
                if (participation.getMatchingSize() > i) {
                    reviewerIds.add(participation.getMemberId());
                }
            }
        }

        return reviewerIds;
    }

    private boolean possiblePair(long reviewerId, long revieweeId, List<Pair> pairs) {
        if (reviewerId == revieweeId) {
            return false;
        }

        return pairs.stream().noneMatch(
                pair -> pair.getDeliver().getId().equals(reviewerId) &&
                        pair.getReceiver().getId().equals(revieweeId));
    }
}
