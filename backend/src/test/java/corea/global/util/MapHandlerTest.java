package corea.global.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class MapHandlerTest {

    @Test
    @DisplayName("여러개의 맵에서 중복되지 않은 키 스트림을 가져온다.")
    void some() {
        Map<Long, String> map1 = new HashMap<>();
        map1.put(1L, "A");
        map1.put(2L, "B");

        Map<Long, Character> map2 = new HashMap<>();
        map2.put(2L, 'C');
        map2.put(3L, 'D');

        Map<Long, Long> map3 = new HashMap<>();
        map3.put(2L, 3L);
        map3.put(3L, 5L);

        Stream<Long> keyStreams = MapHandler.extractDistinctKeyStreams(map1, map2, map3);
        assertThat(keyStreams).hasSize(3);
    }
}
