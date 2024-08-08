package corea.profile.repository;

import corea.member.domain.Member;
import corea.profile.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findByMemberId(long memberId);

    @Query("SELECT p.deliverCount FROM Profile p WHERE p.member = :member")
    long findDeliverCountByMember(Member member);
}
