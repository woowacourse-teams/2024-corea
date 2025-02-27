package corea.auth.controller;

import corea.auth.domain.AuthInfo;
import corea.auth.dto.LoginRequest;
import corea.auth.dto.LoginResponse;
import corea.auth.dto.TokenRefreshRequest;
import corea.exception.ExceptionType;
import corea.exception.ExceptionTypeGroup;
import corea.global.annotation.ApiErrorResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Authorization", description = "인증 정보 관련 API")
public interface LoginControllerSpecification {

    @Operation(summary = "로그인합니다.",
            description = "사용자 정보를 받아 로그인합니다. 아직 가입하지 않은 유저일 경우 회원가입과 함께 로그인됩니다.")
    @ApiErrorResponses(value = {ExceptionType.MEMBER_NOT_FOUND, ExceptionType.GITHUB_AUTHORIZATION_ERROR},
            groups = ExceptionTypeGroup.INTERNAL_SERVER_ERROR)
    ResponseEntity<LoginResponse> login(LoginRequest loginRequest);

    @Operation(summary = "로그인을 유지합니다.",
            description = "로그인 되어 있는 상태에서 액세스 토큰의 유효기간이 만료되었을 경우 리프레시 토큰을 이용해 새로운 액세스 토큰을 부여받습니다. <br>" +
                    "리프레시 토큰은 서버와 클라이언트 양쪽에서 저장하며 API 요청 시 두 값을 비교한 후 동일한 경우에만 액세스 토큰을 부여합니다.")
    @ApiErrorResponses(value = {ExceptionType.TOKEN_EXPIRED, ExceptionType.INVALID_TOKEN, ExceptionType.COOKIE_NOT_EXIST},
            groups = ExceptionTypeGroup.INTERNAL_SERVER_ERROR)
    ResponseEntity<Void> extendAuthorization(TokenRefreshRequest tokenRefreshRequest);

    @Operation(summary = "로그아웃합니다.",
            description = "로그인 되어 있는 상태에서 로그아웃 후 모든 인증 정보를 폐기합니다. <br>" +
                    "요청 시 `Authorization Header`에 `Bearer JWT token`을 포함시켜야 합니다. " +
                    "이 토큰을 기반으로 `AuthInfo` 객체가 생성되며 사용자의 정보가 자동으로 주입됩니다. <br>" +
                    "JWT 토큰에서 추출된 사용자 정보는 피드백 작성에 필요한 인증된 사용자 정보를 제공합니다. " +
                    "<br><br>**참고:** 이 API를 사용하기 위해서는 유효한 JWT 토큰이 필요하며, " +
                    "토큰이 없거나 유효하지 않은 경우 인증 오류가 발생합니다.")
    @ApiErrorResponses(value = {ExceptionType.TOKEN_EXPIRED, ExceptionType.INVALID_TOKEN},
            groups = ExceptionTypeGroup.INTERNAL_SERVER_ERROR)
    ResponseEntity<Void> logout(AuthInfo authInfo);
}

