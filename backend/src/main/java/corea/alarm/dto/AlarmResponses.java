package corea.alarm.dto;

import corea.alarm.domain.Alarm;
import corea.alarm.domain.ServerToUserAlarm;
import corea.alarm.domain.UserToUserAlarm;
import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.member.domain.Member;
import corea.room.domain.Room;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public record AlarmResponses(
        @Schema(description = "알림 리스트")
        List<AlarmResponse> data) {

    public static AlarmResponses of(List<Alarm> alarms, Map<Long, Member> members, Map<Long, Room> userAlarmRooms, Map<Long, Room> serverAlarmRooms) {
        //@formatter:off
        return new AlarmResponses(alarms.stream()
                .map(alarm -> {
                    if (alarm instanceof UserToUserAlarm userAlarm) {
                        return AlarmResponse.of(userAlarm, members.get(userAlarm.getActorId()), userAlarmRooms.get(userAlarm.getInteractionId()));
                    }
                    if (alarm instanceof ServerToUserAlarm serverAlarm){
                        return AlarmResponse.of(serverAlarm, serverAlarmRooms.get(serverAlarm.getInteractionId()));
                    }
                    throw new CoreaException(ExceptionType.UNDEFINED_ALARM_TYPE);
                })
                .sorted(Comparator.comparing(AlarmResponse::createAt).reversed())
                .toList());
        //@formatter:on
    }
}
