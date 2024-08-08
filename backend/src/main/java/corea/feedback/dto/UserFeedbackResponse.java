package corea.feedback.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "개발 피드백 + 커뮤니케이션 피드백 작성 응답")
public record UserFeedbackResponse(@Schema(description = "개발 피드백 + 커뮤니케이션 피드백들 작성 응답")
                                   List<FeedbacksResponse> feedbacks) {
}
