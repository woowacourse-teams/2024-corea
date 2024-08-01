package corea.feedback.repository;

import corea.feedback.domain.ReviewerToRevieweeFeedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewerToRevieweeRepository extends JpaRepository<ReviewerToRevieweeFeedback, Long> {

    Optional<ReviewerToRevieweeFeedback> findByRoomIdAndReviewerIdAndRevieweeUsername(long roomId, long reviewerId, String username);

    boolean existsByRoomIdAndReviewerIdAndRevieweeId(long roomId, long reviewerId, long revieweeId);
}
