package corea.alarm.domain;

public enum AlarmType {
    // 유저간 상호작용으로 인한 알람
    USER,
    SERVER;

    public static AlarmType from(String value) {
        return AlarmType.valueOf(value);
    }
}
