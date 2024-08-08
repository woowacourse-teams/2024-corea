package corea.feedback.controller;

import corea.auth.domain.AuthInfo;
import corea.exception.ExceptionType;
import corea.feedback.dto.DevelopFeedbackRequest;
import corea.feedback.dto.DevelopFeedbackResponse;
import corea.global.annotation.ApiErrorResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Feedback", description = "피드백 관련 API")
public interface DevelopFeedbackControllerSpecification {

    @Operation(summary = "개발 관련 피드백을 작성합니다.",
            description = "자신에게 배정된 리뷰이의 개발 능력 관련 피드백을 작성합니다. <br>" +
                    "요청 시 `Authorization Header`에 `Bearer JWT token`을 포함시켜야 합니다. " +
                    "이 토큰을 기반으로 `AuthInfo` 객체가 생성되며 사용자의 정보가 자동으로 주입됩니다. <br>" +
                    "JWT 토큰에서 추출된 사용자 정보는 피드백 작성에 필요한 인증된 사용자 정보를 제공합니다. " +
                    "<br><br>**참고:** 이 API를 사용하기 위해서는 유효한 JWT 토큰이 필요하며, " +
                    "토큰이 없거나 유효하지 않은 경우 인증 오류가 발생합니다.",
            tags = {"DevelopFeedback API"})
    @ApiErrorResponses(value = {ExceptionType.ALREADY_COMPLETED_FEEDBACK, ExceptionType.NOT_MATCHED_MEMBER})
    ResponseEntity<Void> create(
            @Parameter(description = "방 아이디", example = "1")
            long roomId,
            AuthInfo authInfo,
            DevelopFeedbackRequest request);

    @Operation(summary = "개발 관련 피드백 리스트를 반환합니다.",
            description = "자신에게 사람들이 남긴 개발 능력 관련 피드백을 읽어옵니다. <br>" +
                    "요청 시 `Authorization Header`에 `Bearer JWT token`을 포함시켜야 합니다. " +
                    "이 토큰을 기반으로 `AuthInfo` 객체가 생성되며 사용자의 정보가 자동으로 주입됩니다. <br>" +
                    "JWT 토큰에서 추출된 사용자 정보는 피드백 작성에 필요한 인증된 사용자 정보를 제공합니다. " +
                    "<br><br>**참고:** 이 API를 사용하기 위해서는 유효한 JWT 토큰이 필요하며, " +
                    "토큰이 없거나 유효하지 않은 경우 인증 오류가 발생합니다.",
            tags = {"DevelopFeedback API"})
    @ApiErrorResponses(value = {ExceptionType.FEEDBACK_NOT_FOUND})
    ResponseEntity<DevelopFeedbackResponse> developFeedback(
            @Parameter(description = "방 아이디", example = "1")
            long roomId,
            @Parameter(description = "유저 이름", example = "ashsty")
            String username,
            AuthInfo authInfo);

    @Operation(summary = "개발 관련 피드백을 업데이트합니다.",
            description = "자신에게 배정된 리뷰이의 개발 능력 관련 피드백을 수정합니다. <br>" +
                    "요청 시 `Authorization Header`에 `Bearer JWT token`을 포함시켜야 합니다. " +
                    "이 토큰을 기반으로 `AuthInfo` 객체가 생성되며 사용자의 정보가 자동으로 주입됩니다. <br>" +
                    "JWT 토큰에서 추출된 사용자 정보는 피드백 작성에 필요한 인증된 사용자 정보를 제공합니다. " +
                    "<br><br>**참고:** 이 API를 사용하기 위해서는 유효한 JWT 토큰이 필요하며, " +
                    "토큰이 없거나 유효하지 않은 경우 인증 오류가 발생합니다.",
            tags = {"DevelopFeedback API"})
    @ApiErrorResponses(value = {ExceptionType.FEEDBACK_NOT_FOUND, ExceptionType.NOT_MATCHED_MEMBER})
    ResponseEntity<Void> update(
            @Parameter(description = "방 아이디", example = "1")
            long roomId,
            @Parameter(description = "피드백 아이디", example = "2")
            long feedbackId,
            AuthInfo authInfo,
            DevelopFeedbackRequest request);
}
