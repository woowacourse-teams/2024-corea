package corea.auth.infrastructure;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.member.domain.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@EnableConfigurationProperties(TokenProperties.class)
@Component
@RequiredArgsConstructor
public class TokenProvider {

    private static final String ID = "id";

    private final TokenProperties tokenProperties;

    public String createToken(Member member, long expiration) {
        Map<String, Long> claims = createClaimsByMember(member);
        Date now = new Date();
        Date validity = new Date(now.getTime() + expiration);
        return Jwts.builder()
                .claims(claims)
                .expiration(validity)
                .issuedAt(now)
                .signWith(getSecretKey())
                .compact();
    }

    public void validateToken(String token) {
        SecretKey key = getSecretKey();
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
        } catch (ExpiredJwtException e) {
            throw new CoreaException(ExceptionType.TOKEN_EXPIRED);
        } catch (SignatureException | MalformedJwtException e) {
            throw new CoreaException(ExceptionType.INVALID_TOKEN);
        }
    }

    public Claims getPayload(String token) {
        SecretKey key = getSecretKey();
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private Map<String, Long> createClaimsByMember(Member member) {
        Map<String, Long> claims = new HashMap<>();
        claims.put(ID, member.getId());
        return claims;
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(tokenProperties.secretKey()));
    }

    public Long findMemberIdByToken(String token) {
        Claims claims = getPayload(token);
        return claims.get(ID, Long.class);
    }
}
