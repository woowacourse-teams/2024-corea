package corea.room.repository;

import corea.room.domain.Room;
import corea.room.domain.RoomClassification;
import corea.room.domain.RoomStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    Page<Room> findAllByStatusOrderByRecruitmentDeadline(RoomStatus status, Pageable pageable);

    Page<Room> findAllByClassificationAndStatusOrderByRecruitmentDeadline(RoomClassification classification, RoomStatus status, Pageable pageable);

    List<Room> findAllByIdInOrderByReviewDeadlineAsc(List<Long> ids);
}
