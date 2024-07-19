package corea.matching.service;

import config.ServiceTest;
import corea.exception.CoreaException;
import corea.fixture.MemberFixture;
import corea.fixture.ParticipationFixture;
import corea.matching.domain.Participation;
import corea.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ServiceTest
class MatchingServiceTest {

    @Autowired
    private MatchingService matchingService;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("인원 수가 매칭 사이즈보다 큰 경우 매칭을 수행한다.")
    void matchMaking() {
        List<Participation> participations = new ArrayList<>();
        int matchingSize = 3;

        for (int i = 0; i < 4; i++) {
            participations.add(new Participation(1L, memberRepository.save(MemberFixture.MEMBER_DOMAIN()).getId()));
            participations.add(new Participation(1L, memberRepository.save(MemberFixture.MEMBER_MANAGER()).getId()));
        }

        assertThatCode(() -> matchingService.matchMaking(participations, matchingSize))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("매칭을 수행할 때에, 인원 수가 매칭 사이즈보다 작거나 같으면 예외를 발생한다.")
    void matchMakingLackOfParticipationException() {
        List<Participation> participations = ParticipationFixture.PARTICIPATIONS_EIGHT();
        int matchingSize = 9;

        assertThatThrownBy(() -> matchingService.matchMaking(participations, matchingSize))
                .isInstanceOf(CoreaException.class)
                .hasMessage("참여 인원 수가 부족합니다.");
    }
}
