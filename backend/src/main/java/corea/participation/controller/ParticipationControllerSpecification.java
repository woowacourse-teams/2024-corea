package corea.participation.controller;

import corea.auth.domain.AuthInfo;
import corea.exception.ExceptionType;
import corea.exception.ExceptionTypeGroup;
import corea.global.annotation.ApiErrorResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Participate", description = "방 참여 관련 API")
public interface ParticipationControllerSpecification {

    @Operation(summary = "원하는 방에 참여합니다.",
            description = "원하는 방 상세 페이지에 위치한 참여 버튼을 누르면 참여할 수 있습니다. <br>" +
                    "요청 시 `Authorization Header`에 `Bearer JWT token`을 포함시켜야 합니다. " +
                    "이 토큰을 기반으로 `AuthInfo` 객체가 생성되며 사용자의 정보가 자동으로 주입됩니다. <br>" +
                    "JWT 토큰에서 추출된 사용자 정보는 피드백 작성에 필요한 인증된 사용자 정보를 제공합니다. " +
                    "<br><br>**참고:** 이 API를 사용하기 위해서는 유효한 JWT 토큰이 필요하며, " +
                    "토큰이 없거나 유효하지 않은 경우 인증 오류가 발생합니다.")
    @ApiErrorResponses(value = {ExceptionType.MEMBER_NOT_FOUND, ExceptionType.ROOM_NOT_FOUND, ExceptionType.ALREADY_APPLY},
            groups = ExceptionTypeGroup.INTERNAL_SERVER_ERROR)
    ResponseEntity<Void> participate(
            @Parameter(description = "방 아이디", example = "1")
            long id,
            AuthInfo authInfo);

    @Operation(summary = "참여한 방을 취소합니다.",
            description = "원하는 방 상세 페이지에 위치한 취소 버튼을 누르면 취소할 수 있습니다. <br>" +
                    "요청 시 `Authorization Header`에 `Bearer JWT token`을 포함시켜야 합니다. " +
                    "이 토큰을 기반으로 `AuthInfo` 객체가 생성되며 사용자의 정보가 자동으로 주입됩니다. <br>" +
                    "JWT 토큰에서 추출된 사용자 정보는 피드백 작성에 필요한 인증된 사용자 정보를 제공합니다. " +
                    "<br><br>**참고:** 이 API를 사용하기 위해서는 유효한 JWT 토큰이 필요하며, " +
                    "토큰이 없거나 유효하지 않은 경우 인증 오류가 발생합니다.")
    @ApiErrorResponses(value = {ExceptionType.MEMBER_NOT_FOUND, ExceptionType.ROOM_NOT_FOUND, ExceptionType.NOT_ALREADY_APPLY},
            groups = ExceptionTypeGroup.INTERNAL_SERVER_ERROR)
    ResponseEntity<Void> cancelParticipate(
            @Parameter(description = "방 아이디", example = "1")
            long id,
            AuthInfo authInfo);


}

