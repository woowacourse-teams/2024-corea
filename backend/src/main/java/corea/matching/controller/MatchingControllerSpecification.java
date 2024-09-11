package corea.matching.controller;

import corea.auth.domain.AuthInfo;
import corea.exception.ExceptionType;
import corea.global.annotation.ApiErrorResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Matching", description = "매칭 관련 API")
public interface MatchingControllerSpecification {

    @Operation(summary = "매칭을 진행합니다.",
            description = "해당 방을 신청한 인원 중에서 리뷰어-리뷰이 페어를 랜덤으로 매칭합니다. <br>" +
                    "요청 시 `Authorization Header`에 `Bearer JWT token`을 포함시켜야 합니다. " +
                    "이 토큰을 기반으로 `AuthInfo` 객체가 생성되며 사용자의 정보가 자동으로 주입됩니다. <br>" +
                    "JWT 토큰에서 추출된 사용자 정보는 피드백 작성에 필요한 인증된 사용자 정보를 제공합니다. " +
                    "<br><br>**참고:** 이 API를 사용하기 위해서는 유효한 JWT 토큰이 필요하며, " +
                    "토큰이 없거나 유효하지 않은 경우 인증 오류가 발생합니다. <br>" +
                    "해당 방의 상태가 OPENED 가 아닌 경우에도 오류가 발생합니다.",
            tags = {"Matching API"})
    @ApiErrorResponses(value = {ExceptionType.MEMBER_NOT_FOUND, ExceptionType.ROOM_NOT_FOUND, ExceptionType.PARTICIPANT_SIZE_LACK, ExceptionType.ROOM_STATUS_INVALID})
    ResponseEntity<Void> matching(@Parameter(description = "방 아이디", example = "1")
                                  long id,
                                  AuthInfo authInfo);
}
