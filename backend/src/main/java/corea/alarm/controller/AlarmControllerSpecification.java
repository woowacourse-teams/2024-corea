package corea.alarm.controller;

import corea.alarm.dto.AlarmCheckRequest;
import corea.alarm.dto.AlarmCountResponse;
import corea.alarm.dto.AlarmResponses;
import corea.auth.domain.AuthInfo;
import corea.exception.ExceptionType;
import corea.global.annotation.ApiErrorResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Alarm", description = "알림 관련 API")
public interface AlarmControllerSpecification {

    @Operation(summary = "읽지 않은 알림 개수를 반환합니다.",
            description = "자신의 마이페이지에 디스플레이 되는 프로필 정보를 작성합니다. <br>" +
                    "요청 시 `Authorization Header`에 `Bearer JWT token`을 포함시켜야 합니다. " +
                    "이 토큰을 기반으로 `AuthInfo` 객체가 생성되며 사용자의 정보가 자동으로 주입됩니다. <br>" +
                    "JWT 토큰에서 추출된 사용자 정보는 피드백 작성에 필요한 인증된 사용자 정보를 제공합니다. " +
                    "<br><br>**참고:** 이 API를 사용하기 위해서는 유효한 JWT 토큰이 필요하며, " +
                    "토큰이 없거나 유효하지 않은 경우 인증 오류가 발생합니다.")
    @ApiErrorResponses(value = ExceptionType.MEMBER_NOT_FOUND)
    ResponseEntity<AlarmCountResponse> getAlarmCount(AuthInfo authInfo);

    @Operation(summary = "알림 목록을 최신순으로 반환합니다.",
            description = "자신의 마이페이지에 디스플레이 되는 프로필 정보를 작성합니다. <br>" +
                    "요청 시 `Authorization Header`에 `Bearer JWT token`을 포함시켜야 합니다. " +
                    "이 토큰을 기반으로 `AuthInfo` 객체가 생성되며 사용자의 정보가 자동으로 주입됩니다. <br>" +
                    "JWT 토큰에서 추출된 사용자 정보는 피드백 작성에 필요한 인증된 사용자 정보를 제공합니다. " +
                    "<br><br>**참고:** 이 API를 사용하기 위해서는 유효한 JWT 토큰이 필요하며, " +
                    "토큰이 없거나 유효하지 않은 경우 인증 오류가 발생합니다.")
    @ApiErrorResponses(value = ExceptionType.MEMBER_NOT_FOUND)
    ResponseEntity<AlarmResponses> getAlarms(AuthInfo authInfo);

    @Operation(summary = "알림을 체크합니다.",
            description = "자신의 마이페이지에 디스플레이 되는 프로필 정보를 작성합니다. <br>" +
                    "요청 시 `Authorization Header`에 `Bearer JWT token`을 포함시켜야 합니다. " +
                    "이 토큰을 기반으로 `AuthInfo` 객체가 생성되며 사용자의 정보가 자동으로 주입됩니다. <br>" +
                    "JWT 토큰에서 추출된 사용자 정보는 피드백 작성에 필요한 인증된 사용자 정보를 제공합니다. " +
                    "<br><br>**참고:** 이 API를 사용하기 위해서는 유효한 JWT 토큰이 필요하며, " +
                    "토큰이 없거나 유효하지 않은 경우 인증 오류가 발생합니다.")
    @ApiErrorResponses(value = {ExceptionType.MEMBER_NOT_FOUND, ExceptionType.NOT_RECEIVED_ALARM})
    ResponseEntity<Void> checkAlarm(AuthInfo authInfo, AlarmCheckRequest request);
}
