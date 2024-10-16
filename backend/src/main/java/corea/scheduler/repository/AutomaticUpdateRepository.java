package corea.scheduler.repository;

import corea.scheduler.domain.AutomaticUpdate;
import corea.scheduler.domain.ScheduleStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutomaticUpdateRepository extends JpaRepository<AutomaticUpdate, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT au FROM AutomaticUpdate au WHERE au.roomId = :roomId AND au.status = :status")
    Optional<AutomaticUpdate> findByRoomIdAndStatusForUpdate(long roomId, ScheduleStatus status);

    List<AutomaticUpdate> findAllByStatus(ScheduleStatus status);

    Optional<AutomaticUpdate> findByRoomId(long roomId);

    void deleteByRoomId(long roomId);
}
