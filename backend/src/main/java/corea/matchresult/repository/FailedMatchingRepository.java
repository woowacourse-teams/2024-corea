package corea.matchresult.repository;

import corea.matchresult.domain.FailedMatching;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FailedMatchingRepository extends JpaRepository<FailedMatching, Long> {
}
