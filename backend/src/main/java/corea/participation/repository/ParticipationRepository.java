package corea.participation.repository;

import corea.participation.domain.Participation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {

    Optional<Participation> findByRoomIdAndMemberId(long roomId, long memberId);

    List<Participation> findAllByRoomId(long roomId);

    List<Participation> findAllByMemberId(long memberId);

    boolean existsByRoomIdAndMemberId(long roomId, long memberId);

    void deleteAllByRoomId(long roomId);
}
