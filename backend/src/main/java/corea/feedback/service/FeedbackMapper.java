package corea.feedback.service;

import corea.feedback.dto.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class FeedbackMapper {

    public DevelopFeedbackUpdateInput toFeedbackInput(DevelopFeedbackUpdateRequest request) {
        return new DevelopFeedbackUpdateInput(
                request.evaluationPoint(),
                request.feedbackKeywords(),
                request.feedbackText(),
                request.recommendationPoint()
        );
    }

    public SocialFeedbackUpdateInput toFeedbackInput(SocialFeedbackUpdateRequest request) {
        return new SocialFeedbackUpdateInput(
                request.evaluationPoint(),
                request.feedbackKeywords(),
                request.feedbackText()
        );
    }

    public Map<Long, List<FeedbackResponse>> toFeedbackResponseMap(Map<Long, List<FeedbackOutput>> outputMap) {
        return outputMap.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> toFeedbackResponseList(entry.getValue())
                ));
    }

    private List<FeedbackResponse> toFeedbackResponseList(List<FeedbackOutput> outputs) {
        return outputs.stream()
                .map(this::toFeedbackResponse)
                .toList();
    }

    private FeedbackResponse toFeedbackResponse(FeedbackOutput output) {
        return new FeedbackResponse(
                output.feedbackId(),
                output.roomId(),
                output.receiverId(),
                output.profile(),
                output.username(),
                output.feedbackKeywords(),
                output.evaluationPoint(),
                output.feedbackText()
        );
    }
}
