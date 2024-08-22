package corea.member.repository;

import corea.fixture.MemberFixture;
import corea.member.domain.Member;
import corea.member.domain.Profile;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EntityManager entityManager;

    @Transactional
    @Test
    @DisplayName("Member 엔티티를 저장하면 Profile 엔티티도 저장된다.")
    void cascadeSave() {
        Member member = MemberFixture.MEMBER_PORORO();
        Member savedMember = memberRepository.save(member);

        entityManager.flush();
        entityManager.clear();

        Member findMember = entityManager.find(Member.class, savedMember.getId());
        assertThat(findMember).isNotNull();
        assertThat(findMember.getProfile().getId()).isNotNull();

        Profile findProfile = entityManager.find(Profile.class, findMember.getProfile().getId());
        assertThat(findProfile).isNotNull();
    }

    @Transactional
    @Test
    @DisplayName("Member 엔티티를 삭제하면 Profile 엔티티도 삭제된다.")
    void cascadeDelete() {
        Member member = MemberFixture.MEMBER_PORORO();
        Member savedMember = memberRepository.save(member);

        memberRepository.delete(savedMember);
        entityManager.flush();
        entityManager.clear();

        Member findMember = entityManager.find(Member.class, savedMember.getId());
        assertThat(findMember).isNull();

        Profile findProfile = entityManager.find(Profile.class, savedMember.getProfile().getId());
        assertThat(findProfile).isNull();
    }
}
