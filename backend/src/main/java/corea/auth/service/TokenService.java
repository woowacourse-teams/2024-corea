package corea.auth.service;

import corea.auth.infrastructure.TokenProperties;
import corea.auth.infrastructure.TokenProvider;
import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.member.domain.Member;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@EnableConfigurationProperties(TokenProperties.class)
@Service
public class TokenService {

    private final TokenProvider tokenProvider;
    private final long accessTokenExpiration;
    private final long refreshTokenExpiration;
    private final SecretKey secretKey;

    public TokenService(TokenProvider tokenProvider, TokenProperties tokenProperties) {
        this.tokenProvider = tokenProvider;
        this.accessTokenExpiration = tokenProperties.expiration().access();
        this.refreshTokenExpiration = tokenProperties.expiration().refresh();
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(tokenProperties.secretKey()));
    }

    public String createAccessToken(Member member) {
        return tokenProvider.createToken(member, accessTokenExpiration, secretKey);
    }

    public String createRefreshToken(Member member) {
        return tokenProvider.createToken(member, refreshTokenExpiration, secretKey);
    }

    public void validateToken(String token) {
        tokenProvider.validateToken(token, secretKey);
    }

    public long findMemberIdByToken(String token) {
        return tokenProvider.findMemberIdByToken(token, secretKey)
                .orElseThrow(() -> new CoreaException(ExceptionType.INVALID_TOKEN));
    }
}
