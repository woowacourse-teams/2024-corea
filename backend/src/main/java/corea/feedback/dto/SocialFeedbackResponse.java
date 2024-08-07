package corea.feedback.dto;

import corea.feedback.domain.SocialFeedback;
import corea.feedback.util.FeedbackKeywordConverter;

import java.util.List;

public record SocialFeedbackResponse(long feedbackId, long receiverId, int evaluationPoint,
                                     List<String> feedbackKeywords,
                                     String feedbackText) {

    public static SocialFeedbackResponse of(SocialFeedback socialFeedback) {
        return new SocialFeedbackResponse(
                socialFeedback.getId(),
                socialFeedback.getReceiver().getId(),
                socialFeedback.getEvaluatePoint(),
                FeedbackKeywordConverter.convertToMessages(socialFeedback.getKeywords()),
                socialFeedback.getFeedBackText()
        );
    }
}
