package corea.member.domain;

public enum AuthRole {
    REVIEWEE,
    REVIEWER;

    public boolean isReviewer() {
        return this == REVIEWER;
    }
}
