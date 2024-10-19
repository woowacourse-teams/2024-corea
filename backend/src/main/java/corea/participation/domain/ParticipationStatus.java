package corea.participation.domain;

public enum ParticipationStatus {

    PARTICIPATED,
    NOT_PARTICIPATED,
    PULL_REQUEST_NOT_SUBMITTED,
    MANAGER;

    public boolean isPullRequestNotSubmitted() {
        return this == PULL_REQUEST_NOT_SUBMITTED;
    }
}
