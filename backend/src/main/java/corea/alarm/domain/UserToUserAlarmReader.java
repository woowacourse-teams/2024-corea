package corea.alarm.domain;

import corea.global.annotation.Reader;
import corea.member.domain.Member;
import lombok.RequiredArgsConstructor;

import java.util.EnumMap;
import java.util.stream.Collectors;

@Reader
@RequiredArgsConstructor
public class UserToUserAlarmReader {

    private final UserToUserAlarmRepository userToUserAlarmRepository;

    public long countReceivedAlarm(Member member, boolean isRead) {
        return userToUserAlarmRepository.findAllByReceiverId(member.getId())
                .stream()
                .filter(alarm -> alarm.isStatus(isRead))
                .count();
    }

    public UserAlarmsByActionType findAllByReceiver(Member member) {
        return new UserAlarmsByActionType(userToUserAlarmRepository.findAllByReceiverId(member.getId())
                .stream()
                .collect(Collectors.groupingBy(
                        UserToUserAlarm::getAlarmActionType,
                        () -> new EnumMap<>(AlarmActionType.class),
                        Collectors.toList()
                )));
    }
}
