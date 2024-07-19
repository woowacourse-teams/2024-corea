package corea.room.domain;

public enum RoomStatus {

    OPENED, CLOSED;
    public boolean isOpen(){
        return this==OPENED;
    }
    public boolean isClosed(){
        return this==CLOSED;
    }
}
