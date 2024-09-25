package corea.scheduler.repository;

import corea.scheduler.domain.AutomaticMatching;
import corea.scheduler.domain.ScheduleStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AutomaticMatchingRepository extends JpaRepository<AutomaticMatching, Long> {

    Optional<AutomaticMatching> findByRoomId(long roomId);

    List<AutomaticMatching> findAllByStatus(ScheduleStatus status);

    void deleteByRoomId(long roomId);
}
