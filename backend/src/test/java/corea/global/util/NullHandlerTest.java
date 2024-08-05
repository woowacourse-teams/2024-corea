package corea.global.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class NullHandlerTest {

    @Test
    @DisplayName("값이 null 이 아니면 리스트를 그대로 반환한다.")
    void some() {
        List<Integer> list = List.of(1, 2, 3);
        List<Integer> result = NullHandler.emptyListIfNull(list);
        assertThat(result).isEqualTo(list);
    }

    @Test
    @DisplayName("값이 null 이면 빈 List를 반환한다.")
    void some1() {
        List<Integer> result = NullHandler.emptyListIfNull(null);
        assertThat(result).isEqualTo(List.of());
    }
}
