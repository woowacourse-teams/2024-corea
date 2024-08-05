package corea.auth.service;

import corea.auth.domain.LoginInfo;
import corea.auth.infrastructure.TokenProperties;
import corea.auth.infrastructure.TokenProvider;
import corea.auth.repository.LoginInfoRepository;
import corea.exception.CoreaException;
import corea.member.domain.Member;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static corea.exception.ExceptionType.INVALID_TOKEN;
import static corea.exception.ExceptionType.TOKEN_EXPIRED;

@EnableConfigurationProperties(TokenProperties.class)
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginService {

    private static final String TOKEN_TYPE = "Bearer ";
    private static final String COOKIE_NAME = "cookie";

    private final LoginInfoRepository loginInfoRepository;
    private final TokenProvider tokenProvider;
    private final TokenProperties tokenProperties;

    public String createAccessToken(Member member) {
        return TOKEN_TYPE.concat(tokenProvider.createToken(member, tokenProperties.expiration().access()));
    }

    @Transactional
    public Cookie createRefreshCookie(Member member) {
        String jwtToken = tokenProvider.createToken(member, tokenProperties.expiration().refresh());
        loginInfoRepository.save(new LoginInfo(member, jwtToken));
        Cookie cookie = new Cookie(COOKIE_NAME, jwtToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        return cookie;
    }

    @Transactional
    public void validateRefreshToken(String token) {
        validateExpiration(token);

        LoginInfo info = loginInfoRepository.findByRefreshToken(token)
                .orElseThrow(() -> new CoreaException(INVALID_TOKEN));

        Long tokenMemberId = tokenProvider.getPayload(token).get("id", Long.class);
        if (!info.getMember().getId().equals(tokenMemberId)) {
            throw new CoreaException(INVALID_TOKEN);
        }
    }

    private void validateExpiration(String token) {
        try {
            tokenProvider.validateToken(token);
        } catch (CoreaException e) {
            if (e.getExceptionType().equals(TOKEN_EXPIRED)) {
                loginInfoRepository.deleteByRefreshToken(token);
                throw new CoreaException(TOKEN_EXPIRED);
            }
            throw new CoreaException(INVALID_TOKEN);
        }
    }
}
