package corea.matching.domain;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.participation.domain.Participation;

import java.util.ArrayList;
import java.util.List;

public class ParticipationFilter {

    private final List<Participation> participations;
    private final int matchingSize;

    public ParticipationFilter(List<Participation> participations, int matchingSize) {
        validateParticipationSize(participations, matchingSize);
        this.participations = participations;
        this.matchingSize = matchingSize;
    }

    private void validateParticipationSize(List<Participation> participations, int matchingSize) {
        if (participations.size() <= matchingSize) {
            throw new CoreaException(ExceptionType.PARTICIPANT_SIZE_LACK);
        }
    }

    public List<Participation> filterPRSubmittedParticipation(PullRequestInfo pullRequestInfo) {
        List<Participation> prSubmittedParticipation = findPRSubmittedParticipation(pullRequestInfo);
        validatePRSubmittedParticipationSize(prSubmittedParticipation);

        return prSubmittedParticipation;
    }

    private List<Participation> findPRSubmittedParticipation(PullRequestInfo pullRequestInfo) {
        List<Participation> result = new ArrayList<>();

        for (Participation participation : participations) {
            if (participation.getMemberRole().isReviewer()) {
                result.add(participation);
                continue;
            }

            invalidateIfNotSubmitPR(pullRequestInfo, participation);

            if (hasSubmittedPR(pullRequestInfo, participation)) {
                result.add(participation);
            }
        }
        return result;
    }

    private void invalidateIfNotSubmitPR(PullRequestInfo pullRequestInfo, Participation participation) {
        if (!hasSubmittedPR(pullRequestInfo, participation)) {
            participation.invalidate();
        }
    }

    private boolean hasSubmittedPR(PullRequestInfo pullRequestInfo, Participation participation) {
        return pullRequestInfo.containsGithubMemberId(participation.getMemberGithubId());
    }

    private void validatePRSubmittedParticipationSize(List<Participation> prSubmittedParticipation) {
        if (prSubmittedParticipation.size() <= matchingSize) {
            throw new CoreaException(ExceptionType.PARTICIPANT_SIZE_LACK_DUE_TO_PULL_REQUEST);
        }
    }
}
