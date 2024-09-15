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
}
