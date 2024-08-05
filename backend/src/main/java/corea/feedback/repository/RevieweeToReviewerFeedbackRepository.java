package corea.feedback.repository;

import corea.feedback.domain.FeedbackKeyword;
import corea.feedback.domain.RevieweeToReviewerFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RevieweeToReviewerFeedbackRepository extends JpaRepository<RevieweeToReviewerFeedback, Long> {

    @Query("SELECT r.keywords FROM RevieweeToReviewerFeedback r WHERE r.reviewer.id = :reviewerId")
    List<List<FeedbackKeyword>> findAllKeywordsByReviewerId(long reviewerId);

    Optional<RevieweeToReviewerFeedback> findByRoomIdAndRevieweeIdAndReviewerUsername(long roomId, long revieweeId, String username);

    boolean existsByRoomIdAndReviewerIdAndRevieweeId(long roomId, long reviewerId, long revieweeId);
}
