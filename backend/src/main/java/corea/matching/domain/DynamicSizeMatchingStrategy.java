package corea.matching.domain;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.member.domain.Member;
import corea.participation.domain.Participation;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Primary
@RequiredArgsConstructor
public class DynamicSizeMatchingStrategy implements MatchingStrategy{

    private final PlainRandomMatching strategy;

    private List<Pair> pairs;

    public List<Pair> matchPairs(List<Participation> participations, int roomMatchingSize) {
        List<Participation> participationWithoutReviewer = participations.stream()
                .filter(participation -> !participation.getMemberRole().isReviewer())
                .toList();
        pairs = strategy.matchPairs(participationWithoutReviewer, roomMatchingSize);
        additionalMatching(participations, participationWithoutReviewer, roomMatchingSize);
        return pairs;
    }

    private void additionalMatching(List<Participation> participations, List<Participation> participationsWithoutReviewer, int roomMatchingSize) {
        int max = participations.stream()
                .mapToInt(Participation::getMatchingSize)
                .max()
                .orElseThrow(() -> new CoreaException(ExceptionType.PARTICIPANT_SIZE_LACK));

        for (int currentMatchingSize = roomMatchingSize; currentMatchingSize <= max; currentMatchingSize++) {
            participations = filterUnderMatchedParticipants(participations, currentMatchingSize);
            participationsWithoutReviewer = filterUnderMatchedParticipants(participationsWithoutReviewer, currentMatchingSize);
            additionalMatchingCycle(participations, participationsWithoutReviewer);
            if (participationsWithoutReviewer.isEmpty()) {
                break;
            }
        }
    }

    private void additionalMatchingCycle(List<Participation> participations, List<Participation> participationWithoutReviewer) {
        ArrayDeque<Member> reviewers = new ArrayDeque<>(participations.stream()
                .map(Participation::getMember)
                .toList());

        ArrayDeque<Member> reviewees = new ArrayDeque<>(participationWithoutReviewer.stream()
                .map(Participation::getMember)
                .toList());

        while (!reviewees.isEmpty()) {
            Member reviewee = reviewees.pollFirst();

            for (int reviewerIndex = 0; reviewerIndex < reviewers.size(); reviewerIndex++) {
                Member reviewer = reviewers.pollFirst();

                if (possiblePair(reviewer, reviewee)) {
                    pairs.add(new Pair(reviewer, reviewee));
                    continue;
                }

                reviewers.add(reviewer);
            }
        }
    }

    private boolean possiblePair(Member reviewer, Member reviewee) {
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
