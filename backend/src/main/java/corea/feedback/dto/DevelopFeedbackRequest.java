package corea.feedback.dto;

import corea.feedback.domain.DevelopFeedback;
import corea.feedback.util.FeedbackKeywordConverter;
import corea.member.domain.Member;

import java.util.List;

public record DevelopFeedbackRequest(long receiverId, int evaluationPoint,
                                     List<String> feedbackKeywords,
                                     String feedbackText, int recommendationPoint) {
    public DevelopFeedback toEntity(long roomId, Member deliver, Member receiver) {
        return new DevelopFeedback(
                roomId,
                deliver,
                receiver,
                evaluationPoint,
                FeedbackKeywordConverter.convertToKeywords(feedbackKeywords),
                feedbackText,
                recommendationPoint
        );
    }

    public DevelopFeedback toEntity(long feedbackId, long roomId, Member deliver, Member receiver) {
        return new DevelopFeedback(
                feedbackId,
                roomId,
                deliver,
                receiver,
                evaluationPoint,
                FeedbackKeywordConverter.convertToKeywords(feedbackKeywords),
                feedbackText,
                recommendationPoint
        );
    }
}
