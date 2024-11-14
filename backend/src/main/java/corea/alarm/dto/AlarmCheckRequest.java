package corea.alarm.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "알림 체크 요청")
public record AlarmCheckRequest(
        @Schema(description = "액션 아이디", example = "4")
        long alarmId,

        @Schema(description = "알림 타입", example = "USER")
        String alarmType) {
}
