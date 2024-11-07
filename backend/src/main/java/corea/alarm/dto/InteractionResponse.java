package corea.alarm.dto;

import corea.room.domain.Room;
import io.swagger.v3.oas.annotations.media.Schema;

public record InteractionResponse(

        @Schema(description = "상호작용 아이디 \n 방,피드백 등의 아이디를 가질 수 있다.", example = "1")
        long interactionId,
        @Schema(description = "상호작용 정보 \n 방 제목, 개발 피드백 등의 정보를 가질 수 있다.", example = "TDD 자바")
        String info
) {
    public static InteractionResponse from(Room room) {
        return new InteractionResponse(
                room.getId(),
                room.getTitle()
        );
    }
}
