package corea.feedback.repository;

import corea.feedback.domain.FeedbackKeyword;
import corea.feedback.domain.ReviewerToRevieweeFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewerToRevieweeFeedbackRepository extends JpaRepository<ReviewerToRevieweeFeedback, Long> {

    @Query("SELECT r.keywords FROM ReviewerToRevieweeFeedback r WHERE r.revieweeId = :revieweeId")
    List<List<FeedbackKeyword>> findAllKeywordsByRevieweeId(long revieweeId);
}
