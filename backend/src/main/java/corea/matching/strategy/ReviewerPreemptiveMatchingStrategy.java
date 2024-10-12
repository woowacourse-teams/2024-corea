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
public class ReviewerPreemptiveMatchingStrategy implements MatchingStrategy {

    private final PlainRandomMatchingStrategy strategy;

    @Override
    public List<Pair> matchPairs(List<Participation> participations, int matchingSize) {
        List<Participation> reviewers = participations.stream()
                .filter(Participation::isReviewer)
                .toList();
        List<Participation> nonReviewers = participations.stream()
                .filter(participation -> !participation.isReviewer())
                .sorted(Comparator.comparing(Participation::getMatchingSize))
                .toList();

        validateSize(nonReviewers, matchingSize);
        List<Pair> pairs = strategy.matchPairs(nonReviewers, matchingSize);

        matchAmongReviewees(nonReviewers, matchingSize, pairs);
        if (!reviewers.isEmpty()) {
            matchReviewers(reviewers, nonReviewers, pairs);
        }
        return pairs;
    }

    private void validateSize(List<Participation> participations, int matchingSize) {
        if (participations.size() <= matchingSize) {
            throw new CoreaException(ExceptionType.PARTICIPANT_SIZE_LACK);
        }
    }

    private void matchAmongReviewees(List<Participation> nonReviewers, int roomMatchingSize, List<Pair> pairs) {
        List<Participation> participations = nonReviewers.stream().toList();
        int max = getMaxMatchingSize(participations, roomMatchingSize);

        for (int currentMatchingSize = roomMatchingSize + 1; currentMatchingSize <= max; currentMatchingSize++) {
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

        if (revieweesPerReviewer == 0) {
            matchRevieweeByReviewer(reviewers, reviewees, pairs);
            return;
        }

        for (int index = 0; index < reviewers.size(); index++) {
            Member reviewer = reviewers.get(index).getMember();
            matchRevieweesToReviewer(reviewer, reviewees, pairs, index, revieweesPerReviewer);

            if (index == reviewers.size() - 1) {
                matchRemainingReviewees(reviewer, reviewees, pairs, (index + 1) * revieweesPerReviewer);
            }
        }
    }

    private void matchRevieweeByReviewer(List<Participation> reviewers, List<Participation> reviewees, List<Pair> pairs) {
        for (int index = 0; index < reviewees.size(); index++) {
            Member reviewer = reviewers.get(index).getMember();
            Member reviewee = reviewees.get(index).getMember();
            if (isPossiblePair(reviewer, reviewee, pairs)) {
                pairs.add(new Pair(reviewer, reviewee));
            }
        }
    }

    private void matchRevieweesToReviewer(Member reviewer, List<Participation> reviewees, List<Pair> pairs, int reviewerIndex, int revieweesPerReviewer) {
        for (int index = 0; index < revieweesPerReviewer; index++) {
            Member reviewee = reviewees.get(reviewerIndex * revieweesPerReviewer + index).getMember();
            if (isPossiblePair(reviewer, reviewee, pairs)) {
                pairs.add(new Pair(reviewer, reviewee));
            }
        }
    }

    private void matchRemainingReviewees(Member reviewer, List<Participation> reviewees, List<Pair> pairs, int startIndex) {
        for (int index = startIndex; index < reviewees.size(); index++) {
            Member reviewee = reviewees.get(index).getMember();
            if (isPossiblePair(reviewer, reviewee, pairs)) {
                pairs.add(new Pair(reviewer, reviewee));
            }
        }
    }
}
