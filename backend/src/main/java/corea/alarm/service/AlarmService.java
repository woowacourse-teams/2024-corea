package corea.alarm.service;

import corea.alarm.domain.*;
import corea.alarm.dto.*;
import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.member.domain.Member;
import corea.member.domain.MemberReader;
import corea.participation.domain.Participation;
import corea.participation.domain.ParticipationReader;
import corea.room.domain.Room;
import corea.room.domain.RoomReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlarmService {

    private static final boolean UNREAD = false;

    private final MemberReader memberReader;
    private final RoomReader roomReader;
    private final ParticipationReader participationReader;
    private final UserToUserAlarmReader userToUserAlarmReader;
    private final UserToUserAlarmWriter userToUserAlarmWriter;
    private final ServerToUserAlarmReader serverToUserAlarmReader;
    private final ServerToUserAlarmWriter serverToUserAlarmWriter;

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

    @Transactional
    public void createFeedbackAlarm(long deliverId, long receiverId, long roomId) {
        try {
            CreateUserToUserAlarmInput input = new CreateUserToUserAlarmInput(AlarmActionType.FEEDBACK_CREATED, deliverId, receiverId, roomId);
            userToUserAlarmWriter.create(input.toEntity());
        } catch (Exception e) {
            log.warn("피드백 작성 알림 생성을 실패했습니다. 작성자 ID={},수신자 ID={},방 ID={}",
                    deliverId, receiverId, roomId);
        }
    }

    @Transactional
    public void createMatchingCompletedAlarm(long roomId) {
        participationReader.findAllByRoomId(roomId).stream()
                .map(Participation::getMember)
                .forEach(member -> createMatchingResultAlarm(AlarmActionType.MATCH_COMPLETE, roomId, member.getId()));
    }

    @Transactional
    public void createMatchingFailedAlarm(long roomId) {
        participationReader.findAllByRoomId(roomId).stream()
                .map(Participation::getMember)
                .forEach(member -> createMatchingResultAlarm(AlarmActionType.MATCH_FAIL, roomId, member.getId()));
    }

    private void createMatchingResultAlarm(AlarmActionType alarmActionType, long roomId, long memberId) {
        try {
            CreateServerToUserAlarmInput input = new CreateServerToUserAlarmInput(alarmActionType, memberId, roomId);
            serverToUserAlarmWriter.create(input.toEntity());
        } catch (Exception e) {
            log.warn("매칭 결과 알림 생성을 실패했습니다. 참여자 ID={},방 ID={}", memberId, roomId);
        }
    }

    @Transactional
    public void checkAlarm(long userId, AlarmCheckRequest request) {
        Member member = memberReader.findOne(userId);
        AlarmType alarmType = AlarmType.from(request.alarmType());

        if (alarmType == AlarmType.USER) {
            UserToUserAlarm userToUserAlarm = userToUserAlarmReader.find(request.alarmId());
            userToUserAlarmWriter.check(member, userToUserAlarm);
        }
        if (alarmType == AlarmType.SERVER) {
            ServerToUserAlarm serverToUserAlarm = serverToUserAlarmReader.find(request.alarmId());
            serverToUserAlarmWriter.check(member, serverToUserAlarm);
        }
    }

    public AlarmCountResponse getUnReadAlarmCount(long userId) {
        Member member = memberReader.findOne(userId);
        long userToUserAlarmCount = userToUserAlarmReader.countReceivedAlarm(member, UNREAD);
        long serverToUserAlarmCount = serverToUserAlarmReader.countReceivedAlarm(member, UNREAD);

        return new AlarmCountResponse(userToUserAlarmCount + serverToUserAlarmCount);
    }

    public AlarmResponses getAlarm(long userId) {
        Member member = memberReader.findOne(userId);
        AlarmsByActionType userAlarms = userToUserAlarmReader.findAllByReceiver(member);
        AlarmsByActionType serverAlarms = serverToUserAlarmReader.findAllByReceiver(member);

        List<Alarm> allAlarms = mergeAlarms(userAlarms, serverAlarms);

        Map<Long, Member> actors = memberReader.findMembersMappedById(userAlarms.getActorIds());
        Map<Long, Room> userAlarmRooms = roomReader.findRoomsMappedById(userAlarms.getRoomIds());
        Map<Long, Room> serverAlarmRooms = roomReader.findRoomsMappedById(serverAlarms.getRoomIds());

        return AlarmResponses.of(allAlarms, actors, userAlarmRooms, serverAlarmRooms);
    }

    private List<Alarm> mergeAlarms(AlarmsByActionType userAlarms, AlarmsByActionType serverAlarms) {
        return Stream.concat(
                userAlarms.getList().stream(),
                serverAlarms.getList().stream()
        ).toList();
    }
}
