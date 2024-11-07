package corea.alarm.dto;

import corea.alarm.domain.AlarmActionType;
import corea.alarm.domain.UserToUserAlarm;

public record CreateUserToUserAlarmInput(
        AlarmActionType alarmType,
        long actorId,
        long receiverId,
        long interactionId
) {
    public UserToUserAlarm toEntity() {
        return new UserToUserAlarm(
                alarmType,
                actorId,
                receiverId,
                interactionId,
                false
        );
    }
}
