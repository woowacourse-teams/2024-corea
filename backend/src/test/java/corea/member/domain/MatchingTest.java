package corea.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class MatchingTest {

    private final Matching matching = new Matching();

    @Test
    @DisplayName("멤버 리스트를 받아서 매칭 결과를 반환한다.")
    void matchGroup() {
        ArrayList<Long> members = new ArrayList<>(List.of(
                1L, 2L, 3L, 4L
        ));
        int matchingSize = 2;

        Map<Long, List<Long>> results = matching.matchGroup(members, matchingSize);

        assertThat(results).hasSize(2);
        assertThat(results.get(1L)).hasSize(2);
        assertThat(results.get(2L)).hasSize(2);
    }
}
