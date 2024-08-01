package corea.feedback.dto;

import corea.feedback.domain.ReviewerToRevieweeFeedback;
import corea.feedback.util.FeedbackKeywordConverter;
import corea.member.domain.Member;

import java.util.List;

public record ReviewerToRevieweeRequest(long revieweeId, int evaluationPoint,
                                        List<String> feedbackKeywords,
                                        String feedbackText, int recommendationPoint) {
    public ReviewerToRevieweeFeedback toEntity(long roomId, Member reviewer, Member reviewee) {
        return new ReviewerToRevieweeFeedback(
                roomId,
                reviewer,
                reviewee,
                evaluationPoint,
                FeedbackKeywordConverter.convertToKeywords(feedbackKeywords),
                feedbackText,
                recommendationPoint
        );
    }

    public ReviewerToRevieweeFeedback toEntity(long feedbackId, long roomId, Member reviewer, Member reviewee) {
        return new ReviewerToRevieweeFeedback(
                feedbackId,
                roomId,
                reviewer,
                reviewee,
                evaluationPoint,
                FeedbackKeywordConverter.convertToKeywords(feedbackKeywords),
                feedbackText,
                recommendationPoint
        );
    }
}
