package corea.member.repository;

import corea.member.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    @Query("SELECT p.deliverCount FROM Profile p WHERE p = :profile")
    long findDeliverCountByProfile(Profile profile);
}
