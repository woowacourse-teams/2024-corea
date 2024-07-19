package corea.room.repository;

import corea.room.domain.Classification;
import corea.room.domain.Room;
import corea.room.domain.RoomStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("""
            SELECT r FROM Room r 
            LEFT JOIN Participation p 
            ON r.id = p.roomId AND p.memberId = :memberId 
            WHERE p.id IS NULL AND r.status = :status AND r.manager.id <> :memberId
            """)
    Page<Room> findAllByMemberAndStatus(long memberId, RoomStatus status, PageRequest pageRequest);

    @Query("""
            SELECT r FROM Room r 
            LEFT JOIN Participation p 
            ON r.id = p.roomId AND p.memberId = :memberId 
            WHERE p.id IS NULL AND r.classification = :classification AND r.status = :status AND r.manager.id <> :memberId
            """)
    Page<Room> findAllByMemberAndClassificationAndStatus(long memberId, Classification classification, RoomStatus status, Pageable pageable);

    Page<Room> findAllByStatus(RoomStatus status, PageRequest pageRequest);

    Page<Room> findAllByClassificationAndStatus(Classification classification, RoomStatus status, Pageable pageable);
}
