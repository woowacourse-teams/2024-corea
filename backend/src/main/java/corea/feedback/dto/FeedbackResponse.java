package corea.feedback.dto;

import corea.feedback.domain.RevieweeToReviewerFeedback;
import corea.feedback.domain.ReviewerToRevieweeFeedback;
import corea.feedback.util.FeedbackKeywordConverter;

import java.util.List;

public record FeedbackResponse(long feedbackId, long roomId, long revieweeId, String profile, String username,
                               List<String> feedbackKeywords, int evaluationPoint, String feedbackText) {

    public static FeedbackResponse from(ReviewerToRevieweeFeedback reviewerToRevieweeFeedback) {
        return new FeedbackResponse(
                reviewerToRevieweeFeedback.getId(),
                reviewerToRevieweeFeedback.getRoomId(),
                reviewerToRevieweeFeedback.getReviewee().getId(),
                reviewerToRevieweeFeedback.getReviewee().getProfileLink(),
                reviewerToRevieweeFeedback.getReviewee().getUsername(),
                FeedbackKeywordConverter.convertToMessages(reviewerToRevieweeFeedback.getKeywords()),
                reviewerToRevieweeFeedback.getEvaluatePoint(),
                reviewerToRevieweeFeedback.getFeedBackText()
        );
    }

    public static FeedbackResponse from(RevieweeToReviewerFeedback revieweeToReviewerFeedback) {
        return new FeedbackResponse(
                revieweeToReviewerFeedback.getId(),
                revieweeToReviewerFeedback.getRoomId(),
                revieweeToReviewerFeedback.getReviewer().getId(),
                revieweeToReviewerFeedback.getReviewer().getProfileLink(),
                revieweeToReviewerFeedback.getReviewer().getUsername(),
                FeedbackKeywordConverter.convertToMessages(revieweeToReviewerFeedback.getKeywords()),
                revieweeToReviewerFeedback.getEvaluatePoint(),
                revieweeToReviewerFeedback.getFeedBackText()
        );
    }
}