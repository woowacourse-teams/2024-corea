package corea.matchresult.domain;

public enum ReviewStatus {

    COMPLETE, INCOMPLETE;

    public boolean isComplete() {
        return this == COMPLETE;
    }
}
