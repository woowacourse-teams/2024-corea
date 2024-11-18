package corea.room.repository;

import corea.room.domain.Room;
import corea.room.domain.RoomClassification;
import corea.room.domain.RoomStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long>, JpaSpecificationExecutor<Room> {

    Page<Room> findAllByStatus(RoomStatus status, Pageable pageable);

    Page<Room> findAllByClassificationAndStatus(RoomClassification classification, RoomStatus status, Pageable pageable);

    List<Room> findAllByIdInOrderByRoomDeadline_ReviewDeadlineAsc(List<Long> ids);
}
