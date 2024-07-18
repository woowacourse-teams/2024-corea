package corea.matching.repository;

import corea.matching.domain.Participation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {

    List<Participation> findAllByRoomId(long roomId);

    boolean existsByRoomIdAndMemberId(long roomId, long memberId);
}
