package corea.feedback.repository;

import corea.feedback.domain.FeedbackKeyword;
import corea.feedback.domain.ReviewerToReviewee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewerToRevieweeRepository extends JpaRepository<ReviewerToReviewee, Integer> {

    @Query("SELECT r.keywords FROM ReviewerToReviewee r WHERE r.revieweeId = :revieweeId")
    List<List<FeedbackKeyword>> findAllKeywordsByRevieweeId(long revieweeId);
}
