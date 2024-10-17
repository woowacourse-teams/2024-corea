package corea.room.controller;

import corea.auth.domain.AuthInfo;
import corea.exception.ExceptionType;
import corea.global.annotation.ApiErrorResponses;
import corea.room.dto.RoomResponse;
import corea.room.dto.RoomResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;

public interface RoomDetailsInquiryControllerSpecification {

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

    @Operation(summary = "참여 중인 방 정보를 반환합니다..",
            description = "해당 멤버가 참여 중인 방들의 정보를 리뷰 마감일이 임박한 순으로 정렬해 반환합니다. <br>" +
                    "요청 시 `Authorization Header`에 `Bearer JWT token`을 포함시켜야 합니다. " +
                    "이 토큰을 기반으로 `AuthInfo` 객체가 생성되며 사용자의 정보가 자동으로 주입됩니다. <br>" +
                    "JWT 토큰에서 추출된 사용자 정보는 피드백 작성에 필요한 인증된 사용자 정보를 제공합니다. " +
                    "<br><br>**참고:** 이 API를 사용하기 위해서는 유효한 JWT 토큰이 필요하며, " +
                    "토큰이 없거나 유효하지 않은 경우 인증 오류가 발생합니다.")
    ResponseEntity<RoomResponses> participatedRooms(AuthInfo authInfo,
                                                    @Parameter(description = "종료된 방 포함 여부", example = "false")
                                                    boolean includeClosed);
}
