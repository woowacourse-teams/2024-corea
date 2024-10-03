package corea.member.repository;

import corea.fixture.MemberFixture;
import corea.member.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProfileRepository profileRepository;

    private Member member;

    @BeforeEach
    void setUp() {
        member = MemberFixture.MEMBER_PORORO();
        memberRepository.save(member);
    }

    @Test
    @DisplayName("Member 엔티티를 저장하면 Profile 엔티티도 저장된다.")
    void cascadeSave() {
        long profileId = member.getProfile().getId();

        assertThat(profileRepository.findById(profileId)).isNotEmpty();
    }

    @Test
    @DisplayName("Member 엔티티를 삭제하면 Profile 엔티티도 삭제된다.")
    void cascadeDelete() {
        memberRepository.delete(member);

        long profileId = member.getProfile().getId();
        assertThat(profileRepository.findById(profileId)).isEmpty();
    }
}
