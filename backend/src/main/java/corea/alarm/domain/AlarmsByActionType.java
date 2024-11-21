package corea.alarm.domain;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public record AlarmsByActionType(Map<AlarmActionType, List<Alarm>> data) {

    public Set<Long> getActorIds() {
        return data.values()
                .stream()
                .flatMap(Collection::stream)
                .map(Alarm::getActorId)
                .collect(Collectors.toSet());
    }

    public Set<Long> getRoomIds() {
        return data.values()
                .stream()
                .flatMap(Collection::stream)
                //.filter(alarm -> alarm.getAlarmActionType() == AlarmActionType.REVIEW_COMPLETE)
                .map(Alarm::getInteractionId)
                .collect(Collectors.toSet());
    }

    public List<Alarm> getList() {
        return data.values()
                .stream()
                .flatMap(Collection::stream)
                .toList();
    }
}
