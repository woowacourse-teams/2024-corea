package corea.feedback.controller;

import corea.auth.domain.AuthInfo;
import corea.exception.ExceptionType;
import corea.feedback.dto.UserFeedbackResponse;
import corea.global.annotation.ApiErrorResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Feedback", description = "피드백 관련 API")
public interface UserFeedbackControllerSpecification {

    @Operation(summary = "개발 + 커뮤니케이션 관련 피드백을 반환합니다.",
            description = "자신에게 다른 사람들이 남긴 개발 + 커뮤니케이션 관련 피드백을 반환합니다. <br>" +
                    "요청 시 `Authorization Header`에 `Bearer JWT token`을 포함시켜야 합니다. " +
                    "이 토큰을 기반으로 `AuthInfo` 객체가 생성되며 사용자의 정보가 자동으로 주입됩니다. <br>" +
                    "JWT 토큰에서 추출된 사용자 정보는 피드백 작성에 필요한 인증된 사용자 정보를 제공합니다. " +
                    "<br><br>**참고:** 이 API를 사용하기 위해서는 유효한 JWT 토큰이 필요하며, " +
                    "토큰이 없거나 유효하지 않은 경우 인증 오류가 발생합니다.",
            tags = {"UserFeedback API"})
    @ApiErrorResponses(value = {ExceptionType.ROOM_NOT_FOUND})
    ResponseEntity<UserFeedbackResponse> receivedFeedback(AuthInfo authInfo);

    @Operation(summary = "개발 + 커뮤니케이션 관련 피드백을 반환합니다.",
            description = "자신이 다른 사람들에게 남긴 개발 + 커뮤니케이션 관련 피드백을 반환합니다. <br>" +
                    "요청 시 `Authorization Header`에 `Bearer JWT token`을 포함시켜야 합니다. " +
                    "이 토큰을 기반으로 `AuthInfo` 객체가 생성되며 사용자의 정보가 자동으로 주입됩니다. <br>" +
                    "JWT 토큰에서 추출된 사용자 정보는 피드백 작성에 필요한 인증된 사용자 정보를 제공합니다. " +
                    "<br><br>**참고:** 이 API를 사용하기 위해서는 유효한 JWT 토큰이 필요하며, " +
                    "토큰이 없거나 유효하지 않은 경우 인증 오류가 발생합니다.",
            tags = {"UserFeedback API"})
    @ApiErrorResponses(value = {ExceptionType.ROOM_NOT_FOUND})
    ResponseEntity<UserFeedbackResponse> deliveredFeedback(AuthInfo authInfo);
}
