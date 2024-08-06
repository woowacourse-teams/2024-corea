package corea.feedback.dto;

import corea.feedback.domain.DevelopFeedback;
import corea.feedback.util.FeedbackKeywordConverter;

import java.util.List;

public record DevelopFeedbackResponse(long feedbackId, long receiverId, int evaluationPoint,
                                      List<String> feedbackKeywords,
                                      String feedbackText, int recommendationPoint) {

    public static DevelopFeedbackResponse of(DevelopFeedback developFeedback) {
        return new DevelopFeedbackResponse(
                developFeedback.getId(),
                developFeedback.getReceiver().getId(),
                developFeedback.getEvaluatePoint(),
                FeedbackKeywordConverter.convertToMessages(developFeedback.getKeywords()),
                developFeedback.getFeedBackText(),
                developFeedback.getRecommendationPoint()
        );
    }
}
