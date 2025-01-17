package corea.auth.domain;

import org.springframework.http.ResponseCookie;

public record TokenInfo(String accessToken, ResponseCookie refreshToken) {
}
