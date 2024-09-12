package corea.room.domain;

public enum RoomStatus {

    OPENED, PROGRESS, CLOSED;

    public boolean isClosed() {
        return this == CLOSED;
    }

    public boolean isNotOpened() {
        return this != OPENED;
    }

    public String getStatus() {
        return this.name();
    }
}
