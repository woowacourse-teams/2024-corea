package corea.alarm.service;

import corea.alarm.domain.*;
import corea.alarm.dto.AlarmCheckRequest;
import corea.alarm.dto.AlarmCountResponse;
import corea.alarm.dto.AlarmResponses;
import corea.alarm.domain.AlarmActionType;
import corea.alarm.domain.UserAlarmsByActionType;
import corea.alarm.domain.UserToUserAlarmReader;
import corea.alarm.domain.UserToUserAlarmWriter;
import corea.alarm.dto.CreateUserToUserAlarmInput;
import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.member.domain.Member;
import corea.member.domain.MemberReader;
import corea.room.domain.Room;
import corea.room.domain.RoomReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlarmService {

    private static final boolean UNREAD = false;

    private final MemberReader memberReader;
    private final RoomReader roomReader;
    private final UserToUserAlarmWriter userToUserAlarmWriter;
    private final UserToUserAlarmReader userToUserAlarmReader;

    public AlarmCountResponse getUnReadAlarmCount(long userId) {
        Member member = memberReader.findOne(userId);
        long userToUserAlarmCount = userToUserAlarmReader.countReceivedAlarm(member, UNREAD);
        return new AlarmCountResponse(userToUserAlarmCount);
    }

    @Transactional
    public void createReviewAlarm(long reviewerId, long revieweeId, long roomId) {
        try {
            CreateUserToUserAlarmInput input = new CreateUserToUserAlarmInput(AlarmActionType.REVIEW_COMPLETE, reviewerId, revieweeId, roomId);
            userToUserAlarmWriter.create(input.toEntity());
        } catch (Exception e) {
            log.warn("리뷰 알림 생성을 실패했습니다. 리뷰어 ID={},리뷰이 ID={},방 ID={}",
                    reviewerId, revieweeId, roomId);
        }
    }

    public AlarmResponses getAlarm(long userId) {
        Member member = memberReader.findOne(userId);
        UserAlarmsByActionType userToUserAlarms = userToUserAlarmReader.findAllByReceiver(member);
        Map<Long, Member> actors = memberReader.findMembersMappedById(userToUserAlarms.getActorIds());
        Map<Long, Room> rooms = roomReader.findRoomsMappedById(userToUserAlarms.getRoomIds());
        return AlarmResponses.from(userToUserAlarms.getList(), actors, rooms);
    }

    @Transactional
    public void checkAlarm(long userId, AlarmCheckRequest request) {
        Member member = memberReader.findOne(userId);
        AlarmType alarmType = AlarmType.from(request.alarmType());
        if (alarmType == AlarmType.USER) {
            UserToUserAlarm userToUserAlarm = userToUserAlarmReader.find(request.actionId());
            userToUserAlarmWriter.check(member, userToUserAlarm);
        }
    }

    @Transactional
    public void createUrgeAlarm(long revieweeId, long reviewerId, long roomId) {
        boolean unReadUrgeAlarmExist = userToUserAlarmReader.existUnReadUrgeAlarm(revieweeId, reviewerId, roomId);
        if (unReadUrgeAlarmExist) {
            log.warn("리뷰 재촉 알림 생성을 실패했습니다. 리뷰어 ID={},리뷰이 ID={},방 ID={}",
                    reviewerId, revieweeId, roomId);
            throw new CoreaException(ExceptionType.SAME_UNREAD_ALARM_EXIST);
        }
        CreateUserToUserAlarmInput input = new CreateUserToUserAlarmInput(AlarmActionType.REVIEW_URGE, revieweeId, reviewerId, roomId);
        userToUserAlarmWriter.create(input.toEntity());
    }
}
