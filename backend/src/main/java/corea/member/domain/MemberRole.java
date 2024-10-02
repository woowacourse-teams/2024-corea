package corea.member.domain;

public enum MemberRole {

    REVIEWER, REVIEWEE;

    public boolean isReviwer() {
        return this == REVIEWER;
    }
}
