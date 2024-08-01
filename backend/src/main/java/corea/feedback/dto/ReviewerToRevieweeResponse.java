package corea.feedback.dto;

import corea.feedback.domain.ReviewerToReviewee;
import corea.feedback.util.FeedbackKeywordConverter;

import java.util.List;

public record ReviewerToRevieweeResponse(long feedbackId, long revieweeId, int evaluationPoint,
                                         List<String> feedbackKeywords,
                                         String feedbackText, int recommendationPoint) {

    public static ReviewerToRevieweeResponse of(ReviewerToReviewee reviewerToReviewee) {
        return new ReviewerToRevieweeResponse(
                reviewerToReviewee.getId(),
                reviewerToReviewee.getReviewee()
                        .getId(),
                reviewerToReviewee.getEvaluatePoint(),
                FeedbackKeywordConverter.convertToMessages(reviewerToReviewee.getKeywords()),
                reviewerToReviewee.getFeedBackText(),
                reviewerToReviewee.getRecommendationPoint()
        );
    }
}
