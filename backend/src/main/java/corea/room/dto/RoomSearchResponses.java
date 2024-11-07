package corea.room.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "검색된 방들의 정보 응답")
public record RoomSearchResponses(@Schema(description = "방 정보들")
                                  List<RoomResponse> rooms) {

    public static RoomSearchResponses of(List<RoomResponse> rooms) {
        return new RoomSearchResponses(rooms);
    }
}
