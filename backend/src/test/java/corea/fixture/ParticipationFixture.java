package corea.fixture;

import corea.matching.domain.Participation;

import java.util.List;

public class ParticipationFixture {

    public static List<Participation> PARTICIPATIONS_EIGHT() {
        return List.of(
                new Participation(1L, 1L),
                new Participation(1L, 2L),
                new Participation(1L, 3L),
                new Participation(1L, 4L),
                new Participation(1L, 5L),
                new Participation(1L, 6L),
                new Participation(1L, 7L),
                new Participation(1L, 8L)
        );
    }
}
