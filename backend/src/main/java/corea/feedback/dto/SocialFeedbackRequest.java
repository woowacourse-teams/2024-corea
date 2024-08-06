package corea.feedback.dto;

import corea.feedback.domain.SocialFeedback;
import corea.feedback.util.FeedbackKeywordConverter;
import corea.member.domain.Member;

import java.util.List;

public record SocialFeedbackRequest(long receiverId, int evaluationPoint,
                                    List<String> feedbackKeywords,
                                    String feedbackText) {
    public SocialFeedback toEntity(long roomId, Member deliver, Member receiver) {
        return new SocialFeedback(
                null,
                roomId,
                deliver,
                receiver,
                evaluationPoint,
                FeedbackKeywordConverter.convertToKeywords(feedbackKeywords),
                feedbackText
        );
    }

    public SocialFeedback toEntity(long feedbackId, long roomId, Member deliver, Member receiver) {
        return new SocialFeedback(
                feedbackId,
                roomId,
                deliver,
                receiver,
                evaluationPoint,
                FeedbackKeywordConverter.convertToKeywords(feedbackKeywords),
                feedbackText
        );
    }
}
