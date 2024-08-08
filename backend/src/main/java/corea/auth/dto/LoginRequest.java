package corea.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "액세스 토큰 요청을 위한 인증 코드 전달")
public record LoginRequest(@Schema(description = "인증 코드", example = "4csdf1234asdf")
                           String code) {
}
