package corea.alarm.domain;

import corea.global.annotation.Writer;
import lombok.RequiredArgsConstructor;

@Writer
@RequiredArgsConstructor
public class UserToUserAlarmWriter {

    private final UserToUserAlarmRepository userToUserAlarmRepository;

    public UserToUserAlarm create(UserToUserAlarm userToUserAlarm) {
        return userToUserAlarmRepository.save(userToUserAlarm);
    }
}
