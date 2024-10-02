package corea.member.domain;

public enum MemberRole {

    REVIEWER, REVIEWEE;

    public boolean isReviewer() {
        return this == REVIEWER;
    }
}
