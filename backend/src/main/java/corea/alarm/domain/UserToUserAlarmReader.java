package corea.alarm.domain;

import corea.global.annotation.Reader;
import corea.member.domain.Member;
import lombok.RequiredArgsConstructor;

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
}
