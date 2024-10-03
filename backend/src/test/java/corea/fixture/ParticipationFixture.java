package corea.fixture;

import corea.participation.domain.Participation;
import corea.room.domain.Room;

import java.util.List;

public class ParticipationFixture {

    public static List<Participation> PARTICIPATIONS_EIGHT(Room room) {
        return List.of(
                new Participation(room, 1L),
                new Participation(room, 2L),
                new Participation(room, 3L),
                new Participation(room, 4L),
                new Participation(room, 5L),
                new Participation(room, 6L),
                new Participation(room, 7L),
                new Participation(room, 8L)
        );
    }

    public static List<Participation> PARTICIPATIONS_EIGHT_WITH_DIFFERENT_MATCHING_SIZE(Room room) {
        return List.of(
                new Participation(room, 9L, "", 2),
                new Participation(room, 10L, "", 3),
                new Participation(room, 11L, "", 2),
                new Participation(room, 12L, "", 4),
                new Participation(room, 13L, "", 6),
                new Participation(room, 14L, "", 5),
                new Participation(room, 15L, "", 2),
                new Participation(room, 16L, "", 3)
        );
    }
}
