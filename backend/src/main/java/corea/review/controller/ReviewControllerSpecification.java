package corea.review.controller;

import corea.auth.domain.AuthInfo;
import corea.exception.ExceptionType;
import corea.global.annotation.ApiErrorResponses;
import corea.review.dto.ReviewRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Review", description = "리뷰 관련 API")
public interface ReviewControllerSpecification {

    @Operation(summary = "리뷰를 완료합니다.",
            description = "자신에게 배정된 리뷰이의 리뷰를 완료하고 나면 버튼을 눌러 완료 처리할 수 있습니다. <br>" +
                    "요청 시 `Authorization Header`에 `Bearer JWT token`을 포함시켜야 합니다. " +
                    "이 토큰을 기반으로 `AuthInfo` 객체가 생성되며 사용자의 정보가 자동으로 주입됩니다. <br>" +
                    "JWT 토큰에서 추출된 사용자 정보는 피드백 작성에 필요한 인증된 사용자 정보를 제공합니다. " +
                    "<br><br>**참고:** 이 API를 사용하기 위해서는 유효한 JWT 토큰이 필요하며, " +
                    "토큰이 없거나 유효하지 않은 경우 인증 오류가 발생합니다.")
    @ApiErrorResponses(value = {ExceptionType.NOT_MATCHED_MEMBER,ExceptionType.ROOM_STATUS_IS_NOT_PROGRESS})
    ResponseEntity<Void> complete(AuthInfo authInfo,
                                  ReviewRequest request);
}
