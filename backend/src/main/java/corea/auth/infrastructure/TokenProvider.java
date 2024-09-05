package corea.auth.infrastructure;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.member.domain.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.OptionalLong;

@EnableConfigurationProperties(TokenProperties.class)
@Component
public class TokenProvider {

    private static final String ID = "id";

    public String createToken(Member member, long expiration,SecretKey key) {
        Map<String, Long> claims = createClaimsByMember(member);
        Date now = new Date();
        Date validity = new Date(now.getTime() + expiration);
        return Jwts.builder()
                .claims(claims)
                .expiration(validity)
                .issuedAt(now)
                .signWith(key)
                .compact();
    }

    public void validateToken(String token,SecretKey key) {
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

    public OptionalLong findMemberIdByToken(String token,SecretKey key) {
        Claims claims = getPayload(token,key);
        return OptionalLong.of(claims.get(ID, Long.class));
    }

    public Claims getPayload(String token,SecretKey key) {
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
}
