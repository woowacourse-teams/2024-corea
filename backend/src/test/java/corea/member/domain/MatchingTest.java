package corea.member.domain;

import corea.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class MatchingTest {

    private final Matching matching = new Matching();

    @Test
    @DisplayName("멤버 리스트를 받아서 매칭 결과를 반환한다.")
    void matchGroup(){
        List<Member> members = List.of(
                new Member(1L, "test1@email.com"),
                new Member(2L, "test2@email.com"),
                new Member(3L, "test3@email.com"),
                new Member(4L, "test4@email.com")
                );
        int matchingSize = 2;

        Map<Long, List<Long>> results = matching.matchGroup(members, matchingSize);

        assertThat(results).hasSize(2);
        assertThat(results.get(1L)).hasSize(2);
        assertThat(results.get(2L)).hasSize(2);
    }
}
