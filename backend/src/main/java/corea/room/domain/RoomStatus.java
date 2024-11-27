package corea.room.domain;

public enum RoomStatus {

    OPEN, PROGRESS, CLOSE, FAIL;

    public boolean isClosed() {
        return this == CLOSE;
    }

    public boolean isNotOpened() {
        return this != OPEN;
    }

    public boolean isNotProgress() {
        return this != PROGRESS;
    }

    public String getStatus() {
        return this.name();
    }

    public RoomStatus get() {
        return this;
    }
}
