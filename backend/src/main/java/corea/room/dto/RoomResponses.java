package corea.room.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "방들의 정보 응답")
public record RoomResponses(@Schema(description = "방 정보들")
                            List<RoomResponse> rooms,

                            @Schema(description = "마지막 페이지 여부", example = "false")
                            boolean isLastPage,

                            @Schema(description = "현재 페이지 번호", example = "0")
                            int pageNumber
) {

    public static RoomResponses of(List<RoomResponse> rooms, boolean isLastPage, int pageNumber) {
        return new RoomResponses(rooms, isLastPage, pageNumber);
    }
}
