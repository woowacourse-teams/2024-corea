package corea.feedback.repository;

import corea.feedback.domain.FeedbackKeyword;
import corea.feedback.domain.RevieweeToReviewer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RevieweeToReviewerRepository extends JpaRepository<RevieweeToReviewer, Integer> {

    @Query("SELECT r.keywords FROM RevieweeToReviewer r WHERE r.reviewerId = :reviewerId")
    List<List<FeedbackKeyword>> findAllKeywordsByReviewerId(long reviewerId);
}
