package corea.feedback.util;

import corea.feedback.dto.DevelopFeedbackUpdateInput;
import corea.feedback.dto.DevelopFeedbackUpdateRequest;
import corea.feedback.dto.FeedbackOutput;
import corea.feedback.dto.FeedbackResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FeedbackMapper {

    public static DevelopFeedbackUpdateInput toFeedbackInput(DevelopFeedbackUpdateRequest request) {
        return new DevelopFeedbackUpdateInput(
                request.evaluationPoint(),
                request.feedbackKeywords(),
                request.feedbackText(),
                request.recommendationPoint()
        );
    }

    public static Map<Long, List<FeedbackResponse>> toFeedbackResponseMap(Map<Long, List<FeedbackOutput>> outputMap) {
        return outputMap.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> toFeedbackResponseList(entry.getValue())
                ));
    }

    private static List<FeedbackResponse> toFeedbackResponseList(List<FeedbackOutput> outputs) {
        return outputs.stream()
                .map(FeedbackMapper::toFeedbackResponse)
                .toList();
    }

    private static FeedbackResponse toFeedbackResponse(FeedbackOutput output) {
        return new FeedbackResponse(
                output.feedbackId(),
                output.roomId(),
                output.receiverId(),
                output.profile(),
                output.username(),
                output.isWrited(),
                output.feedbackKeywords(),
                output.evaluationPoint(),
                output.feedbackText()
        );
    }
}
