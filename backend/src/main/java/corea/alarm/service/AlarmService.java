package corea.alarm.service;

import corea.alarm.domain.UserToUserAlarmReader;
import corea.alarm.dto.AlarmCountResponse;
import corea.member.domain.Member;
import corea.member.domain.MemberReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlarmService {

    private static final boolean NOT_READ = false;

    private final MemberReader memberReader;
    private final UserToUserAlarmReader userToUserAlarmReader;

    public AlarmCountResponse getNotReadAlarmCount(long userId) {
        Member member = memberReader.findOne(userId);
        long userToUserAlarmCount = userToUserAlarmReader.countReceivedAlarm(member, NOT_READ);
        return new AlarmCountResponse(userToUserAlarmCount);
    }
}
