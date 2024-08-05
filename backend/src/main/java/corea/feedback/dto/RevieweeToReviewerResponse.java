package corea.feedback.dto;

import corea.feedback.domain.RevieweeToReviewerFeedback;
import corea.feedback.util.FeedbackKeywordConverter;

import java.util.List;

public record RevieweeToReviewerResponse(long feedbackId, long reviewerId, int evaluationPoint,
                                         List<String> feedbackKeywords,
                                         String feedbackText) {

    public static RevieweeToReviewerResponse of(RevieweeToReviewerFeedback revieweeToReviewerFeedback) {
        return new RevieweeToReviewerResponse(
                revieweeToReviewerFeedback.getId(),
                revieweeToReviewerFeedback.getReviewer().getId(),
                revieweeToReviewerFeedback.getEvaluatePoint(),
                FeedbackKeywordConverter.convertToMessages(revieweeToReviewerFeedback.getKeywords()),
                revieweeToReviewerFeedback.getFeedBackText()
        );
    }
}
