package corea.matchresult.repository;

import corea.matchresult.domain.FailedMatching;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FailedMatchingRepository extends JpaRepository<FailedMatching, Long> {

    Optional<FailedMatching> findByRoomId(long roomId);
}
