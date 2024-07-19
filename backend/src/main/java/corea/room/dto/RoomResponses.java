package corea.room.dto;

import corea.room.domain.Room;

import java.util.List;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

public record RoomResponses(List<RoomResponse> rooms) {

    public static RoomResponses from(List<Room> rooms) {
        return rooms.stream()
                .map(RoomResponse::from)
                .collect(collectingAndThen(toList(), RoomResponses::new));
    }
}
