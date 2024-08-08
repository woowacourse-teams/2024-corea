package corea.profile.repository;

import corea.fixture.MemberFixture;
import corea.member.domain.Member;
import corea.member.repository.MemberRepository;
import corea.profile.domain.Profile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProfileRepositoryTest {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("사용자가 쓴 피드백의 개수를 셀 수 있다.")
    void findDeliverCountByMember() {
        Member member = memberRepository.save(MemberFixture.MEMBER_PORORO());
        profileRepository.save(new Profile(member, 3, 2, 1, 5.0f, 5.0f));

        long result = profileRepository.findDeliverCountByMember(member);

        assertThat(result).isEqualTo(1);
    }
}
