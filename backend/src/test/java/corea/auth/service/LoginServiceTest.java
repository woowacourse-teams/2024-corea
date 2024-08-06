package corea.auth.service;

import config.ServiceTest;
import corea.auth.infrastructure.TokenProvider;
import corea.exception.CoreaException;
import corea.fixture.MemberFixture;
import corea.member.domain.Member;
import corea.member.repository.MemberRepository;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static corea.exception.ExceptionType.INVALID_TOKEN;
import static org.assertj.core.api.Assertions.*;

@ServiceTest
class LoginServiceTest {

    @Autowired
    private LoginService authService;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private MemberRepository memberRepository;

    private String refreshToken;

    @BeforeEach
    void setUp() {
        Member member = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());
        Cookie refreshCookie = authService.createRefreshCookie(member);

        refreshToken = refreshCookie.getValue();
    }

    @Test
    @DisplayName("RefreshToken에 문제가 없을 경우 예외가 발생하지 않는다.")
    void validateRefreshToken() {
        assertThatCode(() -> authService.validateRefreshToken(refreshToken))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("존재하지 않는 RefreshToken으로 AccessToken을 요청하는 경우 예외가 발생한다.")
    void validateRefreshTokenException1() {
        String token = tokenProvider.createToken(MemberFixture.MEMBER_YOUNGSU(), 1600L);

        assertThatThrownBy(() -> authService.validateRefreshToken(token))
                .isInstanceOf(CoreaException.class)
                .satisfies(exception -> {
                    CoreaException coreaException = (CoreaException) exception;
                    assertThat(coreaException.getExceptionType()).isEqualTo(INVALID_TOKEN);
                });
    }
}
