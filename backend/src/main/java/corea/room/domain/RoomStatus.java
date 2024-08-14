package corea.room.domain;

public enum RoomStatus {

    OPENED, CLOSED;

    public boolean isClosed() {
        return this == CLOSED;
    }

    public boolean isNotOpened() {
        return this != OPENED;
    }
}
