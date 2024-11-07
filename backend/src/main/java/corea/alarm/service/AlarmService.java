package corea.alarm.service;

import corea.alarm.domain.UserToUserAlarmReader;
import corea.alarm.domain.UserToUserAlarmType;
import corea.alarm.domain.UserToUserAlarmWriter;
import corea.alarm.dto.AlarmCountResponse;
import corea.alarm.dto.CreateUserToUserAlarmInput;
import corea.member.domain.Member;
import corea.member.domain.MemberReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlarmService {

    private static final boolean UNREAD = false;

    private final MemberReader memberReader;
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
            CreateUserToUserAlarmInput input = new CreateUserToUserAlarmInput(UserToUserAlarmType.REVIEW_COMPLETE, reviewerId, revieweeId, roomId);
            userToUserAlarmWriter.create(input.toEntity());
        } catch (Exception e) {
            log.warn("리뷰 알림 생성을 실패했습니다. 리뷰어 ID={},리뷰이 ID={},방 ID={}",
                    reviewerId, revieweeId, roomId);
        }
    }
}
