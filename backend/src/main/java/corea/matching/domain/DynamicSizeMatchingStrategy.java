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

    private void additionalMatching(List<Participation> participations, List<Participation> participationsWithoutReviewer, List<Pair> pairs, int roomMatchingSize) {
        int max = participations.stream().map(Participation::getMatchingSize).mapToInt(x -> x).max().orElseThrow(() -> new CoreaException(ExceptionType.MEMBER_NOT_FOUND));

        participations = optimizeParticipation(participations, roomMatchingSize);
        participationsWithoutReviewer = optimizeParticipation(participationsWithoutReviewer, roomMatchingSize);

        int currentMatchingSize = roomMatchingSize + 1;

        while (currentMatchingSize <= max && !participationsWithoutReviewer.isEmpty()) {
            additionalMatchingCycle(participations, participationsWithoutReviewer, pairs);
            participations = optimizeParticipation(participations, currentMatchingSize);
            participationsWithoutReviewer = optimizeParticipation(participationsWithoutReviewer, currentMatchingSize);
            currentMatchingSize += 1;
        }
    }

    private void additionalMatchingCycle(List<Participation> participations, List<Participation> participationWithoutReviewer, List<Pair> pairs) {
        List<Member> reviewers = new ArrayList<>(participations.stream().map(Participation::getMember).toList());
        Collections.shuffle(reviewers);
        ArrayDeque<Member> shuffledReviewers = new ArrayDeque<>(reviewers);
        List<Member> reviewees = new ArrayList<>(participationWithoutReviewer.stream().map(Participation::getMember).toList());
        Collections.shuffle(reviewees);
        ArrayDeque<Member> revieweeShuffledIds = new ArrayDeque<>(reviewees);

        while (!revieweeShuffledIds.isEmpty()) {
            Member reviewer = shuffledReviewers.pollFirst();
            Member reviewee = revieweeShuffledIds.pollFirst();

            int reviewerSearchCount = 0;
            int originSize = shuffledReviewers.size();

            while (reviewerSearchCount++ < originSize && !possiblePair(reviewer, reviewee, pairs)) {
                shuffledReviewers.add(reviewer);
                reviewer = shuffledReviewers.pollFirst();
            }

            if (reviewerSearchCount <= originSize) {
                pairs.add(new Pair(reviewer, reviewee));
            }
        }
    }

    private boolean possiblePair(Member reviewerId, Member revieweeId, List<Pair> pairs) {
        if (reviewerId == revieweeId) {
            return false;
        }

        return pairs.stream().noneMatch(
                pair -> pair.getDeliver().equals(reviewerId) &&
                        pair.getReceiver().equals(revieweeId));
    }

    private List<Participation> optimizeParticipation(List<Participation> participations, int count) {
        return participations.stream()
                .filter(participation -> participation.getMatchingSize() > count)
                .toList();
    }
}
