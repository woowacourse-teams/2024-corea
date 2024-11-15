package corea.alarm.domain;

import corea.alarm.repository.ServerToUserAlarmRepository;
import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.global.annotation.Reader;
import corea.member.domain.Member;
import lombok.RequiredArgsConstructor;

import java.util.EnumMap;
import java.util.stream.Collectors;

@Reader
@RequiredArgsConstructor
public class ServerToUserAlarmReader {

    private final ServerToUserAlarmRepository serverToUserAlarmRepository;

    public long countReceivedAlarm(Member member, boolean isRead) {
        return serverToUserAlarmRepository.findAllByReceiverId(member.getId())
                .stream()
                .filter(alarm -> alarm.isStatus(isRead))
                .count();
    }

    public ServerToUserAlarm find(long actionId) {
        return serverToUserAlarmRepository.findById(actionId)
                .orElseThrow(() -> new CoreaException(ExceptionType.NOT_RECEIVED_ALARM));
    }

    public AlarmsByActionType findAllByReceiver(Member member) {
        return new AlarmsByActionType(serverToUserAlarmRepository.findAllByReceiverId(member.getId())
                .stream()
                .collect(Collectors.groupingBy(
                        ServerToUserAlarm::getAlarmActionType,
                        () -> new EnumMap<>(AlarmActionType.class),
                        Collectors.toList()
                )));
    }
}
