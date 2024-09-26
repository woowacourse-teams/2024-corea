package corea.scheduler.repository;

import corea.scheduler.domain.AutomaticUpdate;
import corea.scheduler.domain.ScheduleStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AutomaticUpdateRepository extends JpaRepository<AutomaticUpdate, Long> {

    Optional<AutomaticUpdate> findByRoomId(long roomId);

    List<AutomaticUpdate> findAllByStatus(ScheduleStatus status);

    void deleteByRoomId(long roomId);
}
