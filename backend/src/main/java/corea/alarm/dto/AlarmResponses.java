package corea.alarm.dto;

import corea.alarm.domain.UserToUserAlarm;
import corea.member.domain.Member;
import corea.room.domain.Room;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public record AlarmResponses(
        @Schema(description = "알림 리스트")
        List<AlarmResponse> data) {

    public static AlarmResponses from(List<UserToUserAlarm> responses, Map<Long, Member> members, Map<Long, Room> rooms) {
        //@formatter:off
        return new AlarmResponses(responses.stream()
                .map(alarm -> AlarmResponse.from(alarm, members.get(alarm.getActorId()), rooms.get(alarm.getInteractionId())))
                .sorted(Comparator.comparing(AlarmResponse::createAt).reversed())
                .toList());
        //@formatter:on
    }
}
