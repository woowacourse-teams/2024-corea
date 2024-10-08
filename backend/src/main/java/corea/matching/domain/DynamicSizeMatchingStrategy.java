package corea.matching.domain;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.member.domain.Member;
import corea.participation.domain.Participation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class DynamicSizeMatchingStrategy {

    private final MatchingStrategy strategy;

    public List<Pair> matchPairs(List<Participation> participations, int roomMatchingSize) {
        List<Participation> participationWithoutReviewer = participations.stream()
                .filter(participation -> !participation.getMemberRole().isReviewer())
                .toList();
        List<Pair> pairs = strategy.matchPairs(participationWithoutReviewer, roomMatchingSize);
        additionalMatching(participations, participationWithoutReviewer, pairs, roomMatchingSize);
        return pairs;
    }

    private void additionalMatching(List<Participation> participations, List<Participation> participationsWithoutReviewer, List<Pair> pairs, int roomMatchingSize) {
        int max = participations.stream()
                .mapToInt(Participation::getMatchingSize)
                .max()
                .orElseThrow(() -> new CoreaException(ExceptionType.PARTICIPANT_SIZE_LACK));

        participations = filterUnderMatchedParticipants(participations, roomMatchingSize);
        participationsWithoutReviewer = filterUnderMatchedParticipants(participationsWithoutReviewer, roomMatchingSize);

        int currentMatchingSize = roomMatchingSize + 1;

        while (currentMatchingSize <= max && !participationsWithoutReviewer.isEmpty()) {
            additionalMatchingCycle(participations, participationsWithoutReviewer, pairs);
            participations = filterUnderMatchedParticipants(participations, currentMatchingSize);
            participationsWithoutReviewer = filterUnderMatchedParticipants(participationsWithoutReviewer, currentMatchingSize);
            currentMatchingSize += 1;
        }
    }

    private void additionalMatchingCycle(List<Participation> participations, List<Participation> participationWithoutReviewer, List<Pair> pairs) {
        List<Member> reviewers = new ArrayList<>(participations.stream()
                .map(Participation::getMember)
                .toList());
        Collections.shuffle(reviewers);
        ArrayDeque<Member> shuffledReviewers = new ArrayDeque<>(reviewers);

        List<Member> reviewees = new ArrayList<>(participationWithoutReviewer.stream()
                .map(Participation::getMember)
                .toList());
        Collections.shuffle(reviewees);
        ArrayDeque<Member> shuffledReviewees = new ArrayDeque<>(reviewees);

        while (!shuffledReviewees.isEmpty()) {
            Member reviewer = shuffledReviewers.pollFirst();
            Member reviewee = shuffledReviewees.pollFirst();

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

    private boolean possiblePair(Member reviewer, Member reviewee, List<Pair> pairs) {
        if (reviewer == reviewee) {
            return false;
        }

        return pairs.stream().noneMatch(
                pair -> pair.getDeliver().equals(reviewer) &&
                        pair.getReceiver().equals(reviewee));
    }

    private List<Participation> filterUnderMatchedParticipants(List<Participation> participations, int count) {
        return participations.stream()
                .filter(participation -> participation.getMatchingSize() > count)
                .toList();
    }
}
