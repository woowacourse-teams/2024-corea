package corea.alarm.domain;

import corea.alarm.repository.ServerToUserAlarmRepository;
import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.global.annotation.Writer;
import corea.member.domain.Member;
import lombok.RequiredArgsConstructor;

@Writer
@RequiredArgsConstructor
public class ServerToUserAlarmWriter {

    private final ServerToUserAlarmRepository serverToUserAlarmRepository;

    public ServerToUserAlarm create(ServerToUserAlarm serverToUserAlarm) {
        return serverToUserAlarmRepository.save(serverToUserAlarm);
    }

    public ServerToUserAlarm check(Member member, ServerToUserAlarm serverToUserAlarm) {
        if (serverToUserAlarm.isNotReceiver(member)) {
            throw new CoreaException(ExceptionType.NOT_RECEIVED_ALARM);
        }
        serverToUserAlarm.read();
        return serverToUserAlarm;
    }
}
