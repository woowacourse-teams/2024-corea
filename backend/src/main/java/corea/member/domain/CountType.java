package corea.member.domain;

public enum CountType {

    FEEDBACK, DELIVER, RECEIVE;

    public boolean isFeedback() {
        return this == FEEDBACK;
    }

    public boolean isDeliver() {
        return this == DELIVER;
    }
}
