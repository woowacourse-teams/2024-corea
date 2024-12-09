package corea.fixture;

import corea.alarm.domain.AlarmActionType;
import corea.alarm.domain.ServerToUserAlarm;
import corea.alarm.domain.UserToUserAlarm;

public class AlarmFixture {
    public static UserToUserAlarm REVIEW_COMPLETE(long actorId, long receiverId, long interactionId) {
        return new UserToUserAlarm(AlarmActionType.REVIEW_COMPLETE, actorId, receiverId, interactionId, false);
    }

    public static UserToUserAlarm READ_REVIEW_COMPLETE(long actorId, long receiverId, long interactionId) {
        return new UserToUserAlarm(AlarmActionType.REVIEW_COMPLETE, actorId, receiverId, interactionId, true);
    }

    public static UserToUserAlarm URGE_REVIEW(long actorId, long receiverId, long interactionId) {
        return new UserToUserAlarm(AlarmActionType.REVIEW_URGE, actorId, receiverId, interactionId, false);
    }

    public static UserToUserAlarm READ_URGE_REVIEW(long actorId, long receiverId, long interactionId) {
        return new UserToUserAlarm(AlarmActionType.REVIEW_URGE, actorId, receiverId, interactionId, true);
    }

    public static ServerToUserAlarm MATCH_COMPLETE(long receiverId, long interactionId) {
        return new ServerToUserAlarm(AlarmActionType.MATCH_COMPLETE, receiverId, interactionId, false);
    }

    public static ServerToUserAlarm READ_MATCH_COMPLETE(long receiverId, long interactionId) {
        return new ServerToUserAlarm(AlarmActionType.MATCH_COMPLETE, receiverId, interactionId, true);
    }

    public static ServerToUserAlarm MATCH_FAIL(long receiverId, long interactionId) {
        return new ServerToUserAlarm(AlarmActionType.MATCH_FAIL, receiverId, interactionId, false);
    }
}
