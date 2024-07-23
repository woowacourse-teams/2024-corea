package corea.room.dto;

import corea.room.domain.Room;
import org.springframework.data.domain.Page;

import java.util.List;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

public record RoomResponses(List<RoomResponse> rooms) {

    public static RoomResponses of(List<Room> rooms, boolean participateFlag) {
        return rooms.stream()
                .map(room -> RoomResponse.of(room, participateFlag))
                .collect(collectingAndThen(toList(), RoomResponses::new));
    }

    public static RoomResponses from(Page<Room> roomsWithPage) {
        return of(roomsWithPage.getContent(), false);
    }
}
