package corea.room.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "방 참여 멤버들의 정보 응답")
public record RoomParticipantResponses(
        @Schema(description = "방 참여 멤버 정보들")
        List<RoomParticipantResponse> participants,

        @Schema(description = "방 참여 인원 수")
        int size
) {
}
