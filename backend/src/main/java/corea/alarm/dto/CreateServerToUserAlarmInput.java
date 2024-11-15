package corea.alarm.dto;

import corea.alarm.domain.AlarmActionType;
import corea.alarm.domain.ServerToUserAlarm;

public record CreateServerToUserAlarmInput(AlarmActionType alarmType,
                                           long receiverId,
                                           long interactionId) {

    public ServerToUserAlarm toEntity() {
        return new ServerToUserAlarm(
                alarmType,
                receiverId,
                interactionId,
                false
        );
    }
}
