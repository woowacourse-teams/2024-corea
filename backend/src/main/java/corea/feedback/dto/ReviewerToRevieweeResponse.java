package corea.feedback.dto;

import corea.feedback.domain.ReviewerToRevieweeFeedback;
import corea.feedback.util.FeedbackKeywordConverter;

import java.util.List;

public record ReviewerToRevieweeResponse(long feedbackId, long revieweeId, int evaluationPoint,
                                         List<String> feedbackKeywords,
                                         String feedbackText, int recommendationPoint) {

    public static ReviewerToRevieweeResponse of(ReviewerToRevieweeFeedback reviewerToRevieweeFeedback) {
        return new ReviewerToRevieweeResponse(
                reviewerToRevieweeFeedback.getId(),
                reviewerToRevieweeFeedback.getReviewee()
                        .getId(),
                reviewerToRevieweeFeedback.getEvaluatePoint(),
                FeedbackKeywordConverter.convertToMessages(reviewerToRevieweeFeedback.getKeywords()),
                reviewerToRevieweeFeedback.getFeedBackText(),
                reviewerToRevieweeFeedback.getRecommendationPoint()
        );
    }
}
