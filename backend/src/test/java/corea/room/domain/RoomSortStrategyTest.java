package corea.room.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class RoomSortStrategyTest {

    @ParameterizedTest
    @CsvSource(value = {"OPEN, RECRUITMENT_DEADLINE_DESC", "CLOSE, REVIEW_DEADLINE_DESC"})
    @DisplayName("방 상태에 따라 방 정렬 방식을 얻을 수 있다.")
    void from(RoomStatus status, RoomSortStrategy expected) {
        RoomSortStrategy actual = RoomSortStrategy.from(status);

        assertThat(actual).isEqualTo(expected);
    }
}
