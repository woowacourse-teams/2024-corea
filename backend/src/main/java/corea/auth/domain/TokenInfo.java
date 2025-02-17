package corea.auth.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;

@RequiredArgsConstructor
@Getter
public class TokenInfo {

    private final String accessToken;
    private final ResponseCookie refreshToken;

    public String getRefreshToken() {
        return refreshToken.toString();
    }
}
