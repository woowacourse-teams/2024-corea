package corea.feedback.dto;

import corea.feedback.domain.RevieweeToReviewerFeedback;
import corea.feedback.util.FeedbackKeywordConverter;
import corea.member.domain.Member;

import java.util.List;

public record RevieweeToReviewerFeedbackRequest(long reviewerId, int evaluationPoint,
                                                List<String> feedbackKeywords,
                                                String feedbackText) {
    public RevieweeToReviewerFeedback toEntity(long roomId, Member reviewer, Member reviewee) {
        return new RevieweeToReviewerFeedback(
                null,
                roomId,
                reviewer,
                reviewee,
                evaluationPoint,
                FeedbackKeywordConverter.convertToKeywords(feedbackKeywords),
                feedbackText
        );
    }

    public RevieweeToReviewerFeedback toEntity(long feedbackId, long roomId, Member reviewer, Member reviewee) {
        return new RevieweeToReviewerFeedback(
                feedbackId,
                roomId,
                reviewer,
                reviewee,
                evaluationPoint,
                FeedbackKeywordConverter.convertToKeywords(feedbackKeywords),
                feedbackText
        );
    }
}
