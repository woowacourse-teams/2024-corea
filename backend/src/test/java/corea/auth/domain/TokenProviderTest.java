package corea.auth.domain;

import corea.auth.infrastructure.TokenProvider;
import corea.exception.CoreaException;
import corea.fixture.MemberFixture;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;

import static corea.exception.ExceptionType.INVALID_TOKEN;
import static corea.exception.ExceptionType.TOKEN_EXPIRED;
import static org.assertj.core.api.Assertions.*;

class TokenProviderTest {

    private TokenProvider tokenProvider = new TokenProvider();
    private SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode("secretKESampleasd12adkljzczckasdzxnasoqwenzcxxncasdupqwelkzcj1xzklasd"));

    @Test
    @DisplayName("jwt 토큰에 문제가 없을 경우 예외를 발생하지 않는다.")
    void validateToken() {
        String token = tokenProvider.createToken(MemberFixture.MEMBER_YOUNGSU(), 1600L, secretKey);

        assertThatCode(() -> tokenProvider.validateToken(token, secretKey))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("jwt 토큰이 만료된 경우 예외를 발생한다.")
    void validateTokenException1() {
        String token = tokenProvider.createToken(MemberFixture.MEMBER_YOUNGSU(), -1L, secretKey);

        assertThatThrownBy(() -> tokenProvider.validateToken(token, secretKey))
                .isInstanceOf(CoreaException.class)
                .satisfies(exception -> {
                    CoreaException coreaException = (CoreaException) exception;
                    assertThat(coreaException.getExceptionType()).isEqualTo(TOKEN_EXPIRED);
                });
    }

    @Test
    @DisplayName("jwt 토큰이 잘못된 경우 예외를 발생한다.")
    void validateTokenException2() {
        String token = "test.test.test";

        assertThatThrownBy(() -> tokenProvider.validateToken(token, secretKey))
                .isInstanceOf(CoreaException.class)
                .satisfies(exception -> {
                    CoreaException coreaException = (CoreaException) exception;
                    assertThat(coreaException.getExceptionType()).isEqualTo(INVALID_TOKEN);
                });
    }

    @Test
    void getPayload() {
        String token = tokenProvider.createToken(MemberFixture.MEMBER_YOUNGSU(), 1600L, secretKey);

        Long id = tokenProvider.getPayload(token, secretKey)
                .get("id", Long.class);

        assertThat(id).isEqualTo(MemberFixture.MEMBER_YOUNGSU()
                .getId());
    }
}
