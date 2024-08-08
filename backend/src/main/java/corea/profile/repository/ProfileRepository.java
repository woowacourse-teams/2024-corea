package corea.profile.repository;

import corea.profile.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findByMemberId(long memberId);

    @Query("SELECT COALESCE(p.deliverCount, 0) FROM Profile p WHERE p.member.id = :memberId")
    long findDeliverCountByMemberId(long memberId);
}
