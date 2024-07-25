package corea.matching.repository;

import corea.matching.domain.MatchResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MatchResultRepository extends JpaRepository<MatchResult, Long> {

    List<MatchResult> findAllByRevieweeIdAndRoomId(long revieweeId, long roomId);

    List<MatchResult> findAllByReviewerIdAndRoomId(long reviewerId, long roomId);

    Optional<MatchResult> findByRoomIdAndReviewerIdAndRevieweeId(long roomId, long reviewerId, long revieweeId);
}
