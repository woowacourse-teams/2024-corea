package corea.matching.domain;

import corea.member.domain.Member;
import corea.participation.domain.Participation;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.List;

@Component
@Primary
@RequiredArgsConstructor
public class DynamicSizeMatchingStrategy implements MatchingStrategy {

    private final PlainRandomMatching strategy;

    public List<Pair> matchPairs(List<Participation> participations, int roomMatchingSize) {
        List<Participation> participationWithoutReviewer = participations.stream()
                .filter(participation -> !participation.getMemberRole().isReviewer())
                .toList();
        List<Pair> pairs = strategy.matchPairs(participationWithoutReviewer, roomMatchingSize);
        handleAdditionalMatching(participations, participationWithoutReviewer, roomMatchingSize, pairs);
        return pairs;
    }

    private void handleAdditionalMatching(List<Participation> participations, List<Participation> participationsWithoutReviewer, int roomMatchingSize, List<Pair> pairs) {
        int max = getMaxMatchingSize(participations, roomMatchingSize);

        for (int currentMatchingSize = roomMatchingSize + 1; currentMatchingSize <= max; currentMatchingSize++) {
            participations = filterUnderMatchedParticipants(participations, currentMatchingSize, roomMatchingSize);
            participationsWithoutReviewer = filterUnderMatchedParticipants(participationsWithoutReviewer, currentMatchingSize, roomMatchingSize);

            performAdditionalMatching(participations, participationsWithoutReviewer, pairs);
            if (participationsWithoutReviewer.isEmpty()) {
                break;
            }
        }
    }

    private int getMaxMatchingSize(List<Participation> participations, int roomMatchingSize) {
        return participations.stream()
                .mapToInt(Participation::getMatchingSize)
                .max()
                .orElse(roomMatchingSize);
    }

    private void performAdditionalMatching(List<Participation> participations, List<Participation> participationWithoutReviewer, List<Pair> pairs) {
        ArrayDeque<Member> reviewers = extractMember(participations);
        ArrayDeque<Member> reviewees = extractMember(participationWithoutReviewer);

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

    private void tryToMatch(ArrayDeque<Member> reviewers, Member reviewee, List<Pair> pairs) {
        for (int reviewerIndex = 0; reviewerIndex < reviewers.size(); reviewerIndex++) {
            Member reviewer = reviewers.pollFirst();

            if (isPossiblePair(reviewer, reviewee, pairs)) {
                pairs.add(new Pair(reviewer, reviewee));
                return;
            }

            reviewers.add(reviewer);
        }
    }

    private boolean isPossiblePair(Member reviewer, Member reviewee, List<Pair> pairs) {
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

    private boolean isUnderMatchedParticipants(Participation participation, int count, int roomMatchingSize) {
        if (participation.getMemberRole().isReviewer()) {
            return participation.getMatchingSize() + roomMatchingSize >= count;
        }
        return participation.getMatchingSize() >= count;
    }
}
