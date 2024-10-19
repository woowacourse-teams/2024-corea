package corea.feedback.dto;

import corea.feedback.domain.DevelopFeedback;
import corea.feedback.util.FeedbackKeywordConverter;
import corea.member.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Schema(description = "개발 능력 관련 피드백 작성 요청")
public record DevelopFeedbackCreateRequest(@Schema(description = "리뷰이 아이디", example = "2")
                                           @NotNull
                                           long receiverId,

                                           @Schema(description = "평가 점수", example = "4")
                                           @NotNull
                                           int evaluationPoint,

                                           @Schema(description = "선택한 피드백 키워드", example = "[\"코드를 이해하기 쉬웠어요\", \"컨벤션이 잘 지켜졌어요\"]")
                                           @NotNull
                                           List<String> feedbackKeywords,

                                           @Schema(description = "부가 작성 가능한 피드백 텍스트", example = "처음 자바를 접해봤다고 했는데 생각보다 매우 잘 구성되어 있는 코드였습니다. ...")
                                           String feedbackText,

                                           @Schema(description = "랭킹에 필요한 추천 점수", example = "2")
                                           int recommendationPoint) {

    public DevelopFeedback toEntity(long roomId, Member deliver, Member receiver) {
        return new DevelopFeedback(
                roomId,
                deliver,
                receiver,
                evaluationPoint,
                FeedbackKeywordConverter.convertToKeywords(feedbackKeywords),
                feedbackText,
                recommendationPoint
        );
    }
}
