package corea.feedback.repository;

import corea.feedback.domain.FeedbackKeyword;
import corea.feedback.domain.RevieweeToReviewerFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RevieweeToReviewerFeedbackRepository extends JpaRepository<RevieweeToReviewerFeedback, Long> {

    @Query("SELECT r.keywords FROM RevieweeToReviewerFeedback r WHERE r.reviewerId = :reviewerId")
    List<List<FeedbackKeyword>> findAllKeywordsByReviewerId(long reviewerId);

}
