package corea.matching.service;

import config.ServiceTest;
import corea.exception.CoreaException;
import corea.fixture.ParticipationFixture;
import corea.matching.domain.MatchingStrategy;
import corea.participation.domain.Participation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static corea.exception.ExceptionType.PARTICIPANT_SIZE_LACK;
import static org.assertj.core.api.Assertions.*;

@ServiceTest
class MatchingStrategyTest {

    @Autowired
    private MatchingStrategy matchingStrategy;

    @Test
    @DisplayName("인원 수가 매칭 사이즈보다 큰 경우 매칭을 수행한다.")
    void matchMaking() {
        List<Participation> participations = new ArrayList<>();
        int matchingSize = 3;

        for (int i = 0; i < 4; i++) {
            participations.add(new Participation(1L, 1L));
            participations.add(new Participation(1L, 2L));
        }

        assertThatCode(() -> matchingStrategy.matchPairs(participations, matchingSize))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("매칭을 수행할 때에, 인원 수가 매칭 사이즈보다 작거나 같으면 예외를 발생한다.")
    void matchMakingLackOfParticipationException() {
        List<Participation> participations = ParticipationFixture.PARTICIPATIONS_EIGHT();
        int matchingSize = 9;

        assertThatThrownBy(() -> matchingStrategy.matchPairs(participations, matchingSize))
                .isInstanceOf(CoreaException.class)
                .satisfies(exception -> {
                    CoreaException coreaException = (CoreaException) exception;
                    assertThat(coreaException.getExceptionType()).isEqualTo(PARTICIPANT_SIZE_LACK);
                });
    }
}
