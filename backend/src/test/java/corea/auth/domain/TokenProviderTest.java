package corea.auth.domain;

import corea.auth.infrastructure.TokenProvider;
import corea.exception.CoreaException;
import corea.fixture.MemberFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static corea.exception.ExceptionType.INVALID_TOKEN;
import static corea.exception.ExceptionType.TOKEN_EXPIRED;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class TokenProviderTest {

    @Autowired
    private TokenProvider tokenProvider;

    @Test
    @DisplayName("jwt 토큰에 문제가 없을 경우 예외를 발생하지 않는다.")
    void validateToken() {
        String token = tokenProvider.createToken(MemberFixture.MEMBER_JOYSON(), 1600L);

        assertThatCode(() -> tokenProvider.validateToken(token))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("jwt 토큰이 만료된 경우 예외를 발생한다.")
    void validateTokenException1() {
        String token = tokenProvider.createToken(MemberFixture.MEMBER_JOYSON(), -1L);

        assertThatThrownBy(() -> tokenProvider.validateToken(token))
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

        assertThatThrownBy(() -> tokenProvider.validateToken(token))
                .isInstanceOf(CoreaException.class)
                .satisfies(exception -> {
                    CoreaException coreaException = (CoreaException) exception;
                    assertThat(coreaException.getExceptionType()).isEqualTo(INVALID_TOKEN);
                });
    }

    @Test
    void getPayload() {
        String token = tokenProvider.createToken(MemberFixture.MEMBER_JOYSON(), 1600L);

        Long id = tokenProvider.getPayload(token).get("id", Long.class);

        assertThat(id).isEqualTo(MemberFixture.MEMBER_JOYSON().getId());
    }
}
