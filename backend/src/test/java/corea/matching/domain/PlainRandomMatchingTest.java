package corea.matching.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PlainRandomMatchingTest {

    private final PlainRandomMatching plainRandomMatching = new PlainRandomMatching();

    @Test
    @DisplayName("아이디의 리스트가 들어오면 매칭 사이즈 만큼 매칭된 결과를 반환한다.")
    void matchPairs_1() {
        List<Long> memberIds = List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L);
        int matchingSize = 3;

        List<Pair> result = plainRandomMatching.matchPairs(memberIds, matchingSize);

        assertThat(result).hasSize(matchingSize * memberIds.size());
    }

    @Test
    @DisplayName("매칭을 수행할 때에, 본인을 제외한 멤버 중에서 매칭이 된다.")
    void matchPairs_2() {
        List<Long> memberIds = List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L);
        int matchingSize = 3;

        List<Pair> result = plainRandomMatching.matchPairs(memberIds, matchingSize);

        for (Pair pair : result) {
            assertThat(pair.getFromMemberId()).isNotEqualTo(pair.getToMemberId());
        }
    }
}
