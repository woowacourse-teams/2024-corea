package corea.room.controller;

import corea.auth.domain.AuthInfo;
import corea.exception.ExceptionType;
import corea.global.annotation.ApiErrorResponses;
import corea.room.dto.RoomCreateRequest;
import corea.room.dto.RoomParticipantResponses;
import corea.room.dto.RoomResponse;
import corea.room.dto.RoomUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Room", description = "방 관련 API")
public interface RoomControllerSpecification {

    @Operation(summary = "새로운 방을 생성합니다.",
            description = "상호 리뷰 인원을 모을 수 있는 새로운 방을 생성합니다. <br>" +
                    "요청 시 `Authorization Header`에 `Bearer JWT token`을 포함시켜야 합니다. " +
                    "이 토큰을 기반으로 `AuthInfo` 객체가 생성되며 사용자의 정보가 자동으로 주입됩니다. <br>" +
                    "JWT 토큰에서 추출된 사용자 정보는 피드백 작성에 필요한 인증된 사용자 정보를 제공합니다. " +
                    "<br><br>**참고:** 이 API를 사용하기 위해서는 유효한 JWT 토큰이 필요하며, " +
                    "토큰이 없거나 유효하지 않은 경우 인증 오류가 발생합니다.")
    @ApiErrorResponses(value = ExceptionType.MEMBER_NOT_FOUND)
    ResponseEntity<RoomResponse> create(AuthInfo authInfo, RoomCreateRequest request);

    @Operation(summary = "새로운 방을 수정합니다.",
            description = "상호 리뷰 인원을 모을 수 있는 방을 수정합니다. <br>" +
                    "요청 시 `Authorization Header`에 `Bearer JWT token`을 포함시켜야 합니다. " +
                    "이 토큰을 기반으로 `AuthInfo` 객체가 생성되며 사용자의 정보가 자동으로 주입됩니다. <br>" +
                    "JWT 토큰에서 추출된 사용자 정보는 피드백 작성에 필요한 인증된 사용자 정보를 제공합니다. " +
                    "<br><br>**참고:** 이 API를 사용하기 위해서는 유효한 JWT 토큰이 필요하며, " +
                    "토큰이 없거나 유효하지 않은 경우 인증 오류가 발생합니다.")
    @ApiErrorResponses(value = ExceptionType.MEMBER_NOT_FOUND)
    ResponseEntity<RoomResponse> update(AuthInfo authInfo, RoomUpdateRequest request);

    @Operation(summary = "방 상세 정보를 반환합니다.",
            description = "상세 페이지에 디스플레이 되는 방 상세 정보를 반환합니다. <br>" +
                    "요청 시 `Authorization Header`에 `Bearer JWT token`을 포함시켜야 합니다. " +
                    "이 토큰을 기반으로 `AuthInfo` 객체가 생성되며 사용자의 정보가 자동으로 주입됩니다. <br>" +
                    "JWT 토큰에서 추출된 사용자 정보는 피드백 작성에 필요한 인증된 사용자 정보를 제공합니다. " +
                    "<br><br>**참고:** 이 API를 사용하기 위해서는 유효한 JWT 토큰이 필요하며, " +
                    "토큰이 없거나 유효하지 않은 경우 인증 오류가 발생합니다.")
    @ApiErrorResponses(value = {ExceptionType.MEMBER_NOT_FOUND, ExceptionType.ROOM_NOT_FOUND})
    ResponseEntity<RoomResponse> room(@Parameter(description = "방 아이디", example = "1")
                                      long id,
                                      AuthInfo authInfo);

    @Operation(summary = "방 참가자들의 정보를 반환합니다.",
            description = "해당 방에 참여하고 있는 사람들 중 자신을 제외한 참여자들의 PR 링크를 랜덤으로 6명까지 노출합니다. <br>" +
                    "요청 시 `Authorization Header`에 `Bearer JWT token`을 포함시켜야 합니다. " +
                    "이 토큰을 기반으로 `AuthInfo` 객체가 생성되며 사용자의 정보가 자동으로 주입됩니다. <br>" +
                    "JWT 토큰에서 추출된 사용자 정보는 피드백 작성에 필요한 인증된 사용자 정보를 제공합니다. " +
                    "<br><br>**참고:** 이 API를 사용하기 위해서는 유효한 JWT 토큰이 필요하며, " +
                    "토큰이 없거나 유효하지 않은 경우 인증 오류가 발생합니다.")
    @ApiErrorResponses(value = {ExceptionType.MEMBER_NOT_FOUND, ExceptionType.ROOM_NOT_FOUND})
    ResponseEntity<RoomParticipantResponses> participants(@Parameter(description = "방 아이디", example = "1")
                                                          long id,
                                                          AuthInfo authInfo);

    @Operation(summary = "방을 삭제합니다.",
            description = "이미 생성되어 있는 방을 삭제합니다. <br>" +
                    "요청 시 `Authorization Header`에 `Bearer JWT token`을 포함시켜야 합니다. " +
                    "이 토큰을 기반으로 `AuthInfo` 객체가 생성되며 사용자의 정보가 자동으로 주입됩니다. <br>" +
                    "JWT 토큰에서 추출된 사용자 정보는 방을 생성한 사용자와 일치하는지 파악합니다. " +
                    "<br><br>**참고:** 이 API를 사용하기 위해서는 유효한 JWT 토큰이 필요하며, " +
                    "토큰이 없거나 유효하지 않은 경우 인증 오류가 발생합니다.")
    @ApiErrorResponses(value = ExceptionType.ROOM_DELETION_AUTHORIZATION_ERROR)
    ResponseEntity<Void> delete(@Parameter(description = "방 아이디", example = "1")
                                long id,
                                AuthInfo authInfo);
}
