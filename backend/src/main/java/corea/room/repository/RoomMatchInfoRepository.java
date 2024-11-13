package corea.room.repository;

import corea.room.domain.RoomMatchInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomMatchInfoRepository extends JpaRepository<RoomMatchInfo, Long> {

    Optional<RoomMatchInfo> findByRoomId(long roomId);
}
