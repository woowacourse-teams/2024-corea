package corea.room.dto;

import corea.room.domain.Room;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;

import java.util.List;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

@Schema(description = "방들의 정보 응답")
public record RoomResponses(@Schema(description = "방 정보들")
                            List<RoomResponse> rooms,

                            @Schema(description = "마지막 페이지 여부", example = "false")
                            boolean isLastPage,

                            @Schema(description = "현재 페이지 번호", example = "0")
                            int pageNumber
) {

    public static RoomResponses of(List<Room> rooms, boolean isParticipated, boolean isLastPage, int pageNumber) {
        return rooms.stream()
                .map(room -> RoomResponse.of(room, isParticipated))
                .collect(collectingAndThen(toList(), responses -> new RoomResponses(responses, isLastPage, pageNumber)));
    }

    public static RoomResponses from(Page<Room> roomsWithPage, boolean isParticipated, int pageNumber) {
        return of(roomsWithPage.getContent(), isParticipated, roomsWithPage.isLast(), pageNumber);
    }
}
