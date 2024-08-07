package corea.auth.infrastructure;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "security.token.jwt")
public record TokenProperties(String secretKey, Expiration expiration) {

    public record Expiration(long access, long refresh) {
    }
}
