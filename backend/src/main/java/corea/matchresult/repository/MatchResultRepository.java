package corea.matchresult.repository;

import corea.matchresult.domain.MatchResult;
import corea.matchresult.domain.ReviewStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MatchResultRepository extends JpaRepository<MatchResult, Long> {

    List<MatchResult> findAllByRevieweeIdAndRoomId(long revieweeId, long roomId);

    List<MatchResult> findAllByReviewerIdAndRoomId(long reviewerId, long roomId);

    List<MatchResult> findAllByRoomIdAndReviewStatus(long roomId, ReviewStatus reviewStatus);

    Optional<MatchResult> findByRoomIdAndReviewerIdAndRevieweeId(long roomId, long reviewerId, long revieweeId);
}
