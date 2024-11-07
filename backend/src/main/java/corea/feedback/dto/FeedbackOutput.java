package corea.feedback.dto;

import corea.feedback.domain.DevelopFeedback;
import corea.feedback.domain.SocialFeedback;
import corea.feedback.util.FeedbackKeywordConverter;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Collections;
import java.util.List;

@Schema(description = "개발 피드백 + 커뮤니케이션 피드백 조회 응답")
public record FeedbackOutput(@Schema(description = "피드백 아이디", example = "1")
                             long feedbackId,

                             @Schema(description = "방 아이디", example = "1")
                             long roomId,

                             @Schema(description = "리뷰이 아이디", example = "2")
                             long receiverId,

                             @Schema(description = "프로필 링크", example = "www.naver.com")
                             String profile,

                             @Schema(description = "유저 이름", example = "jcoding-play")
                             String username,

                             @Schema(description = "내가 상대방의 피드백 작성을 완료하였는지 여부", example = "false")
                             boolean isWrited,

                             @Schema(description = "선택한 피드백 키워드", example = "[\"코드를 이해하기 쉬웠어요\", \"컨벤션이 잘 지켜졌어요\"]")
                             List<String> feedbackKeywords,

                             @Schema(description = "평가 점수", example = "4")
                             int evaluationPoint,

                             @Schema(description = "부가 작성 가능한 피드백 텍스트", example = "처음 자바를 접해봤다고 했는데 생각보다 매우 잘 구성되어 있는 코드였습니다. ...")
                             String feedbackText,

                             @Schema(description = "pr링크 및 comment링크 정보 제공", example = "https://github.com/youngsu5582/github-api-test/pull/1")
                             String link) {

    private static final boolean FEEDBACK_COMPLETE = true;
    private static final boolean FEEDBACK_INCOMPLETE = false;

    public static FeedbackOutput fromReceiver(DevelopFeedback developFeedback, String link) {
        return new FeedbackOutput(
                developFeedback.getId(),
                developFeedback.getRoomId(),
                developFeedback.getDeliver().getId(),
                developFeedback.getDeliver().getThumbnailUrl(),
                developFeedback.getDeliver().getUsername(),
                FEEDBACK_COMPLETE,
                FeedbackKeywordConverter.convertToMessages(developFeedback.getKeywords()),
                developFeedback.getEvaluatePoint(),
                developFeedback.getFeedBackText(),
                link
        );
    }

    public static FeedbackOutput fromDeliver(DevelopFeedback developFeedback, String link) {
        return new FeedbackOutput(
                developFeedback.getId(),
                developFeedback.getRoomId(),
                developFeedback.getReceiver().getId(),
                developFeedback.getReceiver().getThumbnailUrl(),
                developFeedback.getReceiver().getUsername(),
                FEEDBACK_COMPLETE,
                FeedbackKeywordConverter.convertToMessages(developFeedback.getKeywords()),
                developFeedback.getEvaluatePoint(),
                developFeedback.getFeedBackText(),
                link
        );
    }

    public static FeedbackOutput fromReceiver(SocialFeedback socialFeedback, String link) {
        return new FeedbackOutput(
                socialFeedback.getId(),
                socialFeedback.getRoomId(),
                socialFeedback.getDeliver().getId(),
                socialFeedback.getDeliver().getThumbnailUrl(),
                socialFeedback.getDeliver().getUsername(),
                FEEDBACK_COMPLETE,
                FeedbackKeywordConverter.convertToMessages(socialFeedback.getKeywords()),
                socialFeedback.getEvaluatePoint(),
                socialFeedback.getFeedBackText(),
                link
        );
    }

    public static FeedbackOutput fromDeliver(SocialFeedback socialFeedback, String link) {
        return new FeedbackOutput(
                socialFeedback.getId(),
                socialFeedback.getRoomId(),
                socialFeedback.getReceiver().getId(),
                socialFeedback.getReceiver().getThumbnailUrl(),
                socialFeedback.getReceiver().getUsername(),
                FEEDBACK_COMPLETE,
                FeedbackKeywordConverter.convertToMessages(socialFeedback.getKeywords()),
                socialFeedback.getEvaluatePoint(),
                socialFeedback.getFeedBackText(),
                link
        );
    }

    public static FeedbackOutput masking(FeedbackOutput feedbackOutput) {
        return new FeedbackOutput(
                feedbackOutput.feedbackId(),
                feedbackOutput.roomId,
                feedbackOutput.receiverId,
                feedbackOutput.profile,
                feedbackOutput.username,
                FEEDBACK_INCOMPLETE,
                Collections.emptyList(),
                0,
                "",
                feedbackOutput.link()
        );
    }
}
