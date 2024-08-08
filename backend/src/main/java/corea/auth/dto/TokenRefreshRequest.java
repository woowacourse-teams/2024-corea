package corea.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "새로운 액세스 토큰 요청")
public record TokenRefreshRequest(@Schema(description = "리프레시 JWT 토큰", example = "O1234567COREAREFRESH")
                                  String refreshToken) {
}
