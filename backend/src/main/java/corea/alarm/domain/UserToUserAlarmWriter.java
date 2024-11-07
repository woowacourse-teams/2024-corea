package corea.alarm.domain;


import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.global.annotation.Writer;
import corea.member.domain.Member;
import lombok.RequiredArgsConstructor;

@Writer
@RequiredArgsConstructor
public class UserToUserAlarmWriter {

    public UserToUserAlarm check(Member member, UserToUserAlarm userToUserAlarm) {
        if (userToUserAlarm.isNotReceiver(member)) {
            throw new CoreaException(ExceptionType.NOT_RECEIVED_ALARM);
        }
        userToUserAlarm.read();
        return userToUserAlarm;
    }
}
