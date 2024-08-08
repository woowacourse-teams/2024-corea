package corea.feedback.dto;

import corea.feedback.domain.SocialFeedback;
import corea.feedback.util.FeedbackKeywordConverter;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "커뮤니케이션 능력 관련 피드백 작성 응답")
public record SocialFeedbackResponse(@Schema(description = "피드백 아이디", example = "1")
                                     long feedbackId,

                                     @Schema(description = "리뷰어 아이디", example = "2")
                                     long receiverId,

                                     @Schema(description = "평가 점수", example = "4")
                                     int evaluationPoint,

                                     @Schema(description = "선택한 피드백 키워드", example = "[\"이해가 잘 되게 설명을 잘해줘요(못해줘요)\", \"도움이 되었어요(아니에요)\"]")
                                     List<String> feedbackKeywords,

                                     @Schema(description = "부가 작성 가능한 피드백 텍스트", example = "말투가 너무 날카로운 것 같아요. ...")
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
