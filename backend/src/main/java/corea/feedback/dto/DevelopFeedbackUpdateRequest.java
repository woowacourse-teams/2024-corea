package corea.feedback.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "개발 능력 관련 피드백 업데이트 요청")
public record DevelopFeedbackUpdateRequest(@Schema(description = "업데이트할 평가 점수", example = "1")
                                           int evaluationPoint,

                                           @Schema(description = "업데이트할 피드백 키워드", example = "[\"코드를 이해하기 어려웠어요\", \"컨벤션이 안 지켜졌어요\"]")
                                           List<String> feedbackKeywords,

                                           @Schema(description = "업데이트할 피드백 텍스트", example = "처음엔 좋다 생각했는데, 다시 생각하니 별로였어요.")
                                           String feedbackText,

                                           @Schema(description = "업데이트할 랭킹에 필요한 추천 점수", example = "1")
                                           int recommendationPoint) {
}
