package corea.member.repository;

import corea.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUsername(String username);

    @Query("SELECT COALESCE(m.profile.deliverCount, 0) FROM Member m WHERE m = :member")
    long findDeliverCountByMember(Member member);
}
