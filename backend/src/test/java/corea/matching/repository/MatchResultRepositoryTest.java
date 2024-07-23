package corea.matching.repository;

import corea.fixture.MemberFixture;
import corea.matching.domain.MatchResult;
import corea.member.domain.Member;
import corea.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MatchResultRepositoryTest {

    @Autowired
    private MatchResultRepository matchResultRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("사용자가 입장한 방에서 사용자를 리뷰할 사람들을 조회할 수 있다.")
    void findAllByRoomIdAndToMemberId() {
        Member member1 = memberRepository.save(MemberFixture.MEMBER_JOYSON());
        Member member2 = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());
        Member member3 = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());

        MatchResult matchResult1 = new MatchResult(1L, member1, member2, null);
        MatchResult matchResult2 = new MatchResult(1L, member2, member2, null);
        MatchResult matchResult3 = new MatchResult(1L, member3, member2, null);
        MatchResult matchResult4 = new MatchResult(1L, member2, member1, null);

        matchResultRepository.save(matchResult1);
        matchResultRepository.save(matchResult2);
        matchResultRepository.save(matchResult3);
        matchResultRepository.save(matchResult4);

        List<MatchResult> results = matchResultRepository.findAllByRevieweeIdAndRoomId(2L, 1L);

        assertThat(results).hasSize(3);
    }
}
