package corea.scheduler.repository;

import corea.scheduler.domain.AutomaticUpdate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AutomaticUpdateRepository extends JpaRepository<AutomaticUpdate, Long> {

    Optional<AutomaticUpdate> findByRoomId(long roomId);
}
