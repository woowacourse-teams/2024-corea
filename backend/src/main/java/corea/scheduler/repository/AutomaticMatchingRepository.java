package corea.scheduler.repository;

import corea.scheduler.domain.AutomaticMatching;
import corea.scheduler.domain.MatchingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AutomaticMatchingRepository extends JpaRepository<AutomaticMatching, Long> {

    List<AutomaticMatching> findAllByStatus(MatchingStatus status);
}
