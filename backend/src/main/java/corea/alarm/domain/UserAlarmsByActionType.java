package corea.alarm.domain;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public record UserAlarmsByActionType(Map<AlarmActionType, List<UserToUserAlarm>> data) {
    public Set<Long> getActorIds() {
        return data.values()
                .stream()
                .flatMap(Collection::stream)
                .map(UserToUserAlarm::getActorId)
                .collect(Collectors.toSet());
    }

    public Set<Long> getRoomIds() {
        return data.values()
                .stream()
                .flatMap(Collection::stream)
                .filter(alarm -> alarm.getAlarmActionType() == AlarmActionType.REVIEW_COMPLETE)
                .map(UserToUserAlarm::getInteractionId)
                .collect(Collectors.toSet());
    }

    public List<UserToUserAlarm> getList() {
        return data.values()
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}
