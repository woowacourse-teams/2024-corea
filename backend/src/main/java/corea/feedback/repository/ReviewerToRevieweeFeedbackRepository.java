package corea.feedback.repository;

import corea.feedback.domain.FeedbackKeyword;
import corea.feedback.domain.ReviewerToRevieweeFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReviewerToRevieweeFeedbackRepository extends JpaRepository<ReviewerToRevieweeFeedback, Long> {

    @Query("SELECT r.keywords FROM ReviewerToRevieweeFeedback r WHERE r.reviewee.id = :revieweeId")
    List<List<FeedbackKeyword>> findAllKeywordsByRevieweeId(long revieweeId);

    Optional<ReviewerToRevieweeFeedback> findByRoomIdAndReviewerIdAndRevieweeUsername(long roomId, long reviewerId, String username);

    boolean existsByRoomIdAndReviewerIdAndRevieweeId(long roomId, long reviewerId, long revieweeId);
}
