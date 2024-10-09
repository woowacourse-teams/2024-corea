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

        for (int currentMatchingSize = roomMatchingSize; currentMatchingSize <= max; currentMatchingSize++) {
            participations = filterUnderMatchedParticipants(participations, currentMatchingSize);
            participationsWithoutReviewer = filterUnderMatchedParticipants(participationsWithoutReviewer, currentMatchingSize);
            additionalMatchingCycle(participations, participationsWithoutReviewer, pairs);
            if (participationsWithoutReviewer.isEmpty()) {
                break;
            }
        }
    }

    private void additionalMatchingCycle(List<Participation> participations, List<Participation> participationWithoutReviewer, List<Pair> pairs) {
        ArrayDeque<Member> reviewers = new ArrayDeque<>(participations.stream()
                .map(Participation::getMember)
                .toList());

        ArrayDeque<Member> reviewees = new ArrayDeque<>(participationWithoutReviewer.stream()
                .map(Participation::getMember)
                .toList());

        while (!reviewees.isEmpty()) {
            Member reviewer = reviewers.pollFirst();
            Member reviewee = reviewees.pollFirst();

            int reviewerSearchCount = 0;
            int originSize = reviewers.size();

            while (reviewerSearchCount++ < originSize && !possiblePair(reviewer, reviewee, pairs)) {
                reviewers.add(reviewer);
                reviewer = reviewers.pollFirst();
            }

            if (reviewerSearchCount <= originSize) {
                pairs.add(new Pair(reviewer, reviewee));
                continue;
            }

            reviewers.add(reviewer);
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
