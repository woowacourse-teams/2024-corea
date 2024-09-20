package corea.room.domain;

public enum RoomStatus {

    OPEN, PROGRESS, CLOSE;

    public boolean isClosed() {
        return this == CLOSE;
    }

    public boolean isNotOpened() {
        return this != OPEN;
    }

    public String getStatus() {
        return this.name();
    }
}
