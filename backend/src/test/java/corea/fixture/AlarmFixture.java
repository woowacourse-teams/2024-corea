package corea.fixture;

import corea.alarm.domain.UserToUserAlarm;
import corea.alarm.domain.UserToUserAlarmType;

public class AlarmFixture {
    public static UserToUserAlarm REVIEW_COMPLETE(long actorId, long receiverId, long interactionId) {
        return new UserToUserAlarm(UserToUserAlarmType.REVIEW_COMPLETE, actorId, receiverId, interactionId, false);
    }

    public static UserToUserAlarm READ_REVIEW_COMPLETE(long actorId, long receiverId, long interactionId) {
        return new UserToUserAlarm(UserToUserAlarmType.REVIEW_COMPLETE, actorId, receiverId, interactionId, true);
    }
}
