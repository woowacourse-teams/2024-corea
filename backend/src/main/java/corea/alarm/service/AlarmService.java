package corea.alarm.service;

import corea.alarm.domain.UserAlarmsByActionType;
import corea.alarm.domain.UserToUserAlarmReader;
import corea.alarm.dto.AlarmCountResponse;
import corea.alarm.dto.AlarmResponses;
import corea.member.domain.Member;
import corea.member.domain.MemberReader;
import corea.room.domain.Room;
import corea.room.domain.RoomReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlarmService {

    private static final boolean UNREAD = false;

    private final MemberReader memberReader;
    private final UserToUserAlarmReader userToUserAlarmReader;
    private final RoomReader roomReader;

    public AlarmCountResponse getUnReadAlarmCount(long userId) {
        Member member = memberReader.findOne(userId);
        long userToUserAlarmCount = userToUserAlarmReader.countReceivedAlarm(member, UNREAD);
        return new AlarmCountResponse(userToUserAlarmCount);
    }

    public AlarmResponses getAlarm(long userId) {
        Member member = memberReader.findOne(userId);
        UserAlarmsByActionType userToUserAlarms = userToUserAlarmReader.findAllByReceiver(member);
        Map<Long, Member> actors = memberReader.findToMap(userToUserAlarms.getActorIds());
        Map<Long, Room> rooms = roomReader.findToMap(userToUserAlarms.getRoomIds());
        return AlarmResponses.from(userToUserAlarms.getList(), actors, rooms);
    }
}
