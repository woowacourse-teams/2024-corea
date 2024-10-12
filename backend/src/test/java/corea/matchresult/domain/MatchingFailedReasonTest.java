package corea.matchresult.domain;

import corea.exception.ExceptionType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MatchingFailedReasonTest {

    @ParameterizedTest
    @CsvSource(value = {"ROOM_NOT_FOUND, ROOM_NOT_FOUND", "AUTOMATIC_MATCHING_NOT_FOUND, AUTOMATIC_MATCHING_NOT_FOUND"})
    @DisplayName("ExceptionType을 통해 매칭이 실패한 이유를 찾을 수 있다.")
    void from(ExceptionType exceptionType, MatchingFailedReason expected) {
        MatchingFailedReason actual = MatchingFailedReason.from(exceptionType);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("ExceptionType을 통해 매칭 실패 이유를 찾을 수 없다면 UNKNOWN 반환한다.")
    void unknownReason() {
        MatchingFailedReason reason = MatchingFailedReason.from(ExceptionType.ALREADY_COMPLETED_FEEDBACK);

        assertThat(reason).isEqualTo(MatchingFailedReason.UNKNOWN);
    }
}
