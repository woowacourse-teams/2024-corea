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
        handleAdditionalMatching(participations, participationWithoutReviewer, roomMatchingSize);
        return pairs;
    }

    private void handleAdditionalMatching(List<Participation> participations, List<Participation> participationsWithoutReviewer, int roomMatchingSize) {
        int max = getMaxMatchingSize(participations);

        for (int currentMatchingSize = roomMatchingSize; currentMatchingSize <= max; currentMatchingSize++) {
            participations = filterUnderMatchedParticipants(participations, currentMatchingSize, roomMatchingSize);
            participationsWithoutReviewer = filterUnderMatchedParticipants(participationsWithoutReviewer, currentMatchingSize, roomMatchingSize);

            performAdditionalMatching(participations, participationsWithoutReviewer);
            if (participationsWithoutReviewer.isEmpty()) {
                break;
            }
        }
    }

    private int getMaxMatchingSize(List<Participation> participations) {
        return participations.stream()
                .mapToInt(Participation::getMatchingSize)
                .max()
                .orElseThrow(() -> new CoreaException(ExceptionType.PARTICIPANT_SIZE_LACK));
    }

    private void performAdditionalMatching(List<Participation> participations, List<Participation> participationWithoutReviewer) {
        ArrayDeque<Member> reviewers = extractMember(participations);
        ArrayDeque<Member> reviewees = extractMember(participationWithoutReviewer);

        while (!reviewees.isEmpty()) {
            Member reviewee = reviewees.pollFirst();
            tryToMatch(reviewers, reviewee);
        }
    }

    private ArrayDeque<Member> extractMember(List<Participation> participations) {
        return new ArrayDeque<>(participations.stream()
                .map(Participation::getMember)
                .toList());
    }

    private void tryToMatch(ArrayDeque<Member> reviewers, Member reviewee) {
        for (int reviewerIndex = 0; reviewerIndex < reviewers.size(); reviewerIndex++) {
            Member reviewer = reviewers.pollFirst();

            if (isPossiblePair(reviewer, reviewee)) {
                pairs.add(new Pair(reviewer, reviewee));
                return;
            }

            reviewers.add(reviewer);
        }
    }

    private boolean isPossiblePair(Member reviewer, Member reviewee) {
        if (reviewer.equals(reviewee)) {
            return false;
        }

        return pairs.stream().noneMatch(
                pair -> pair.getDeliver().equals(reviewer) &&
                        pair.getReceiver().equals(reviewee));
    }

    private List<Participation> filterUnderMatchedParticipants(List<Participation> participations, int count, int roomMatchingSize) {
        return participations.stream()
                .filter(participation -> isUnderMatchedParticipants(participation, count, roomMatchingSize))
                .toList();
    }

    private static boolean isUnderMatchedParticipants(Participation participation, int count, int roomMatchingSize) {
        if (participation.getMemberRole().isReviewer()) {
            return participation.getMatchingSize() + roomMatchingSize > count;
        }
        return participation.getMatchingSize() > count;
    }
}
