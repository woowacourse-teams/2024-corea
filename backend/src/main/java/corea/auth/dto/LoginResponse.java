package corea.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "로그인/로그인 유지를 위한 정보 전달")
public record LoginResponse(@Schema(description = "리프레시 JWT 토큰", example = "O1234567COREAREFRESH")
                            String refreshToken,
                            GithubUserInfo userInfo,
                            String memberRole) {
}
