package corea.member.domain;

public enum ProfileCountType {

    DELIVER, RECEIVE;

    public boolean isDeliver() {
        return this == DELIVER;
    }
}
