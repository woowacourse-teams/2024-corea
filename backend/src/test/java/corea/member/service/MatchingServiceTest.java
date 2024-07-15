package corea.member.service;

import corea.domain.Member;
import corea.member.repository.MatchedGroupRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MatchingServiceTest {

    private final MatchingService matchingService;
    private final MatchedGroupRepository matchedGroupRepository;

    @Autowired
    public MatchingServiceTest(MatchingService matchingService, MatchedGroupRepository matchedGroupRepository) {
        this.matchingService = matchingService;
        this.matchedGroupRepository = matchedGroupRepository;
    }

    @Test
    @DisplayName("멤버 리스트를 받아 매칭 결과를 반환한다.")
    void matchMaking() {
        List<Member> members = List.of(
                new Member(1L, "test1@email.com"),
                new Member(2L, "test2@email.com"),
                new Member(3L, "test3@email.com"),
                new Member(4L, "test4@email.com")
        );
        int matchingSize = 2;

        matchingService.matchMaking(members, matchingSize);

        assertThat(matchedGroupRepository.findAll()).hasSize(4);
    }

}
