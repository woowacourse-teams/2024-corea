package corea.feedback.repository;

import corea.feedback.domain.ReviewerToReviewee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewerToRevieweeRepository extends JpaRepository<ReviewerToReviewee, Long> {

    Optional<ReviewerToReviewee> findByRoomIdAndReviewerIdAndRevieweeUsername(long roomId, long reviewerId, String username);

    boolean existsByRoomIdAndReviewerIdAndRevieweeId(long roomId, long reviewerId, long revieweeId);
}
