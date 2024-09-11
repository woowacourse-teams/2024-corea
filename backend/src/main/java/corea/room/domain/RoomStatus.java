package corea.room.domain;

import lombok.Getter;

@Getter
public enum RoomStatus {

    OPENED("OPENED"),
    PROGRESS("PROGRESS"),
    CLOSED("CLOSED");

    private final String status;

    RoomStatus(String status) {
        this.status = status;
    }

    public boolean isClosed() {
        return this == CLOSED;
    }

    public boolean isProgress() {
        return this == PROGRESS;
    }

    public boolean isNotOpened() {
        return this != OPENED;
    }
}
