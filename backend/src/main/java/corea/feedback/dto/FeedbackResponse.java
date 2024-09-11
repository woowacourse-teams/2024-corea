package corea.feedback.dto;

import corea.feedback.domain.DevelopFeedback;
import corea.feedback.domain.SocialFeedback;
import corea.feedback.util.FeedbackKeywordConverter;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "개발 피드백 + 커뮤니케이션 피드백 작성 응답")
public record FeedbackResponse(@Schema(description = "피드백 아이디", example = "1")
                               long feedbackId,

                               @Schema(description = "방 아이디", example = "1")
                               long roomId,

                               @Schema(description = "리뷰이 아이디", example = "2")
                               long receiverId,

                               @Schema(description = "프로필 링크", example = "www.naver.com")
                               String profile,

                               @Schema(description = "유저 이름", example = "jcoding-play")
                               String username,

                               @Schema(description = "선택한 피드백 키워드", example = "[\"코드를 이해하기 쉬웠어요\", \"컨벤션이 잘 지켜졌어요\"]")
                               List<String> feedbackKeywords,

                               @Schema(description = "평가 점수", example = "4")
                               int evaluationPoint,

                               @Schema(description = "부가 작성 가능한 피드백 텍스트", example = "처음 자바를 접해봤다고 했는데 생각보다 매우 잘 구성되어 있는 코드였습니다. ...")
                               String feedbackText) {

    public static FeedbackResponse fromReceiver(DevelopFeedback developFeedback) {
        return new FeedbackResponse(
                developFeedback.getId(),
                developFeedback.getRoomId(),
                developFeedback.getDeliver().getId(),
                developFeedback.getDeliver().getThumbnailUrl(),
                developFeedback.getDeliver().getUsername(),
                FeedbackKeywordConverter.convertToMessages(developFeedback.getKeywords()),
                developFeedback.getEvaluatePoint(),
                developFeedback.getFeedBackText()
        );
    }

    public static FeedbackResponse fromDeliver(DevelopFeedback developFeedback) {
        return new FeedbackResponse(
                developFeedback.getId(),
                developFeedback.getRoomId(),
                developFeedback.getReceiver().getId(),
                developFeedback.getReceiver().getThumbnailUrl(),
                developFeedback.getReceiver().getUsername(),
                FeedbackKeywordConverter.convertToMessages(developFeedback.getKeywords()),
                developFeedback.getEvaluatePoint(),
                developFeedback.getFeedBackText()
        );
    }

    public static FeedbackResponse fromReceiver(SocialFeedback socialFeedback) {
        return new FeedbackResponse(
                socialFeedback.getId(),
                socialFeedback.getRoomId(),
                socialFeedback.getDeliver().getId(),
                socialFeedback.getDeliver().getThumbnailUrl(),
                socialFeedback.getDeliver().getUsername(),
                FeedbackKeywordConverter.convertToMessages(socialFeedback.getKeywords()),
                socialFeedback.getEvaluatePoint(),
                socialFeedback.getFeedBackText()
        );
    }

    public static FeedbackResponse fromDeliver(SocialFeedback socialFeedback) {
        return new FeedbackResponse(
                socialFeedback.getId(),
                socialFeedback.getRoomId(),
                socialFeedback.getReceiver().getId(),
                socialFeedback.getReceiver().getThumbnailUrl(),
                socialFeedback.getReceiver().getUsername(),
                FeedbackKeywordConverter.convertToMessages(socialFeedback.getKeywords()),
                socialFeedback.getEvaluatePoint(),
                socialFeedback.getFeedBackText()
        );
    }
}
