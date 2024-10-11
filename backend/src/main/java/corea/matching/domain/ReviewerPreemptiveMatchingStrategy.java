package corea.matching.domain;

import corea.member.domain.Member;
import corea.participation.domain.Participation;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ReviewerPreemptiveMatchingStrategy implements MatchingStrategy {

    @Override
    public List<Pair> matchPairs(List<Participation> participations, int matchingSize) {
        List<Participation> reviewers = participations.stream()
                .filter(Participation::isReviewer)
                .toList();
        List<Participation> nonReviewers = participations.stream()
                .filter(participation -> !participation.isReviewer())
                .sorted(Comparator.comparing(Participation::getMatchingSize))
                .toList();

        List<Pair> pairs = new ArrayList<>();

        matchAmongReviewees(nonReviewers, matchingSize, pairs);
        matchReviewers(reviewers, nonReviewers, pairs);

        return pairs;
    }

    private void matchAmongReviewees(List<Participation> participations, int roomMatchingSize, List<Pair> pairs) {
        int max = getMaxMatchingSize(participations, roomMatchingSize);

        for (int currentMatchingSize = 1; currentMatchingSize <= max; currentMatchingSize++) {
            participations = filterUnderMatchedParticipants(participations, currentMatchingSize);

            if (participations.isEmpty()) {
                break;
            }
            performAdditionalMatching(participations, pairs);
        }
    }

    private int getMaxMatchingSize(List<Participation> participations, int roomMatchingSize) {
        return participations.stream()
                .mapToInt(Participation::getMatchingSize)
                .max()
                .orElse(roomMatchingSize);
    }

    private void performAdditionalMatching(List<Participation> participations, List<Pair> pairs) {
        List<Participation> reviewersArray = new ArrayList<>(participations);
        ArrayDeque<Member> reviewers = extractMember(reviewersArray);
        Collections.reverse(reviewersArray);
        ArrayDeque<Member> reviewees = extractMember(reviewersArray);

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
        int reviewerSize = reviewers.size();
        for (int reviewerIndex = 0; reviewerIndex < reviewerSize; reviewerIndex++) {
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
                pair -> pair.hasReviewer(reviewer) &&
                        pair.hasReviewee(reviewee));
    }

    private List<Participation> filterUnderMatchedParticipants(List<Participation> participations, int currentMatchingSize) {
        return participations.stream()
                .filter(participation -> isUnderMatchedParticipants(participation, currentMatchingSize))
                .toList();
    }

    private boolean isUnderMatchedParticipants(Participation participation, int currentMatchingSize) {
        if (participation.isReviewer()) {
            return false;
        }
        return participation.getMatchingSize() >= currentMatchingSize;
    }

    private void matchReviewers(List<Participation> reviewers, List<Participation> reviewees, List<Pair> pairs) {
        int revieweesPerReviewer = reviewees.size() / reviewers.size();

        for (int index = 0; index < reviewers.size(); index++) {
            Member reviewer = reviewers.get(index).getMember();
            matchRevieweesToReviewer(reviewer, reviewees, pairs, index, revieweesPerReviewer);

            if (index == reviewers.size() - 1) {
                matchRemainingReviewees(reviewer, reviewees, pairs, (index + 1) * revieweesPerReviewer);
            }
        }
    }

    private void matchRevieweesToReviewer(Member reviewer, List<Participation> reviewees, List<Pair> pairs, int reviewerIndex, int revieweesPerReviewer) {
        for (int index = 0; index < revieweesPerReviewer; index++) {
            Member reviewee = reviewees.get(reviewerIndex * revieweesPerReviewer + index).getMember();
            pairs.add(new Pair(reviewer, reviewee));
        }
    }

    private void matchRemainingReviewees(Member reviewer, List<Participation> reviewees, List<Pair> pairs, int startIndex) {
        for (int index = startIndex; index < reviewees.size(); index++) {
            pairs.add(new Pair(reviewer, reviewees.get(index).getMember()));
        }
    }
}
