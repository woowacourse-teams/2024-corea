package corea.member.service;

import config.ServiceTest;
import corea.domain.Participation;
import corea.member.repository.MatchedGroupRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ServiceTest
class MatchingServiceTest {

    @Autowired
    MatchingService matchingService;

    @Autowired
    MatchedGroupRepository matchedGroupRepository;

    @Test
    @DisplayName("멤버 리스트를 받아 매칭 결과를 반환한다.")
    void matchMaking() {
        List<Participation> members = List.of(
                new Participation(1, 1),
                new Participation(1, 2),
                new Participation(1, 3),
                new Participation(1, 4)
        );
        int matchingSize = 2;

        matchingService.matchMaking(members, matchingSize);

        assertThat(matchedGroupRepository.findAll()).hasSize(4);
    }
}
