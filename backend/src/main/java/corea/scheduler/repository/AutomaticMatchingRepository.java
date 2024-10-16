package corea.scheduler.repository;

import corea.scheduler.domain.AutomaticMatching;
import corea.scheduler.domain.ScheduleStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutomaticMatchingRepository extends JpaRepository<AutomaticMatching, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT am FROM AutomaticMatching am WHERE am.roomId = :roomId AND am.status = :status")
    Optional<AutomaticMatching> findByRoomIdAndStatusForUpdate(long roomId, ScheduleStatus status);

    List<AutomaticMatching> findAllByStatus(ScheduleStatus status);

    void deleteByRoomId(long roomId);
}
