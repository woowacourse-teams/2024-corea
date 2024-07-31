package corea.feedback.dto;

import corea.feedback.domain.FeedbackKeyword;
import corea.feedback.domain.ReviewerToReviewee;
import corea.member.domain.Member;

import java.util.List;

public record ReviewerToRevieweeRequest(long revieweeId, int evaluationPoint,
                                        List<String> feedbackKeywords,
                                        String feedbackText, int recommendationPoint) {
    public ReviewerToReviewee toEntity(long roomId, Member reviewer, Member reviewee) {
        return new ReviewerToReviewee(
                roomId,
                reviewer,
                reviewee,
                evaluationPoint,
                FeedbackKeyword.of(feedbackKeywords),
                feedbackText,
                recommendationPoint
        );
    }

    public ReviewerToReviewee toEntity(long feedbackId, long roomId, Member reviewer, Member reviewee) {
        return new ReviewerToReviewee(
                feedbackId,
                roomId,
                reviewer,
                reviewee,
                evaluationPoint,
                FeedbackKeyword.of(feedbackKeywords),
                feedbackText,
                recommendationPoint
        );
    }
}
