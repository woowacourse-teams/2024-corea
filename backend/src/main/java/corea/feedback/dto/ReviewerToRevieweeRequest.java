package corea.feedback.dto;

import corea.feedback.domain.ReviewerToReviewee;
import corea.feedback.domain.ReviewerToRevieweeFeedback;
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
                ReviewerToRevieweeFeedback.toEntityList(feedbackKeywords),
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
                ReviewerToRevieweeFeedback.toEntityList(feedbackKeywords),
                feedbackText,
                recommendationPoint
        );
    }
}
