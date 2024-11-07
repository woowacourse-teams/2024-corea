package corea.alarm.dto;

import corea.alarm.domain.UserToUserAlarm;
import corea.alarm.domain.UserToUserAlarmType;

public record CreateUserToUserAlarmInput(
        UserToUserAlarmType alarmType,
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
