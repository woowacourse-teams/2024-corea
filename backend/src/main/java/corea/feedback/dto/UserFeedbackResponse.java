package corea.feedback.dto;

import java.util.List;

public record UserFeedbackResponse(List<FeedbacksResponse> feedbacks) {
}
