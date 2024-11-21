package corea.alarm.dto;

import corea.alarm.domain.AlarmType;
import corea.alarm.domain.ServerToUserAlarm;
import corea.alarm.domain.UserToUserAlarm;
import corea.member.domain.Member;
import corea.room.domain.Room;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record AlarmResponse(
        @Schema(description = "알림 아이디", example = "1")
        long alarmId,

        @Schema(description = "액션 타입", example = "REVIEW_COMPLETE")
        String actionType,

        @Schema(description = "발송자 \n 서버가 일괄 생성한 알림은 없을 수 있다.", nullable = true)
        MemberResponse actor,

        @Schema(description = "상호작용 \n 방,피드백 등을 가질 수 있다.")
        InteractionResponse interaction,

        @Schema(description = "알림을 읽었는지", example = "false")
        boolean isRead,

        @Schema(description = "알림이 생성된 시간", example = "2024-11-07T14:48:01.733862")
        LocalDateTime createAt,

        @Schema(description = "알림 타입", example = "USER")
        String alarmType
) {

    public static AlarmResponse of(UserToUserAlarm alarm, Member member, Room room) {
        return new AlarmResponse(alarm.getId(), alarm.getActionType(), MemberResponse.from(member),
                InteractionResponse.from(room), alarm.isRead(), alarm.getCreateAt(), AlarmType.USER.name());
    }

    public static AlarmResponse of(ServerToUserAlarm alarm, Room room) {
        return new AlarmResponse(alarm.getId(), alarm.getActionType(), null,
                InteractionResponse.from(room), alarm.isRead(), alarm.getCreateAt(), AlarmType.SERVER.name());
    }
}
