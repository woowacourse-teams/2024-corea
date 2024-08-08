package corea.feedback.dto;

import corea.feedback.domain.DevelopFeedback;
import corea.feedback.domain.SocialFeedback;
import corea.feedback.util.FeedbackKeywordConverter;

import java.util.List;

public record FeedbackResponse(long feedbackId, long roomId, long receiverId, String profile, String username,
                               List<String> feedbackKeywords, int evaluationPoint, String feedbackText) {

    public static FeedbackResponse from(DevelopFeedback developFeedback) {
        return new FeedbackResponse(
                developFeedback.getId(),
                developFeedback.getRoomId(),
                developFeedback.getReceiver().getId(),
                developFeedback.getReceiver().getProfileLink(),
                developFeedback.getReceiver().getUsername(),
                FeedbackKeywordConverter.convertToMessages(developFeedback.getKeywords()),
                developFeedback.getEvaluatePoint(),
                developFeedback.getFeedBackText()
        );
    }

    public static FeedbackResponse from(SocialFeedback socialFeedback) {
        return new FeedbackResponse(
                socialFeedback.getId(),
                socialFeedback.getRoomId(),
                socialFeedback.getReceiver().getId(),
                socialFeedback.getReceiver().getProfileLink(),
                socialFeedback.getReceiver().getUsername(),
                FeedbackKeywordConverter.convertToMessages(socialFeedback.getKeywords()),
                socialFeedback.getEvaluatePoint(),
                socialFeedback.getFeedBackText()
        );
    }
}
