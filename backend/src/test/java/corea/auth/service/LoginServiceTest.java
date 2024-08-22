package corea.auth.service;

import config.ServiceTest;
import corea.auth.domain.LoginInfo;
import corea.auth.infrastructure.TokenProvider;
import corea.auth.repository.LoginInfoRepository;
import corea.exception.CoreaException;
import corea.fixture.MemberFixture;
import corea.member.domain.Member;
import corea.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static corea.exception.ExceptionType.INVALID_TOKEN;
import static corea.exception.ExceptionType.TOKEN_EXPIRED;
import static org.assertj.core.api.Assertions.*;

@ServiceTest
class LoginServiceTest {

    @Autowired
    private LoginService loginService;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private LoginInfoRepository loginInfoRepository;

    private Member member;
    private String refreshToken;

    @BeforeEach
    void setUp() {
        member = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());
        refreshToken = loginService.publishRefreshToken(member);
    }

    @Test
    @DisplayName("RefreshToken에 문제가 없을 경우 예외가 발생하지 않는다.")
    void authorize() {
        assertThatCode(() -> loginService.authorize(refreshToken))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("RefreshToken의 유효기간이 만료되었을 경우 DB에서 해당 유저의 로그인 정보를 삭제한다.")
    void authorizeException_TokenExpired() {
        String expiredRefreshToken = tokenProvider.createToken(member, 10L);

        loginInfoRepository.deleteAll();
        loginInfoRepository.save(new LoginInfo(member, expiredRefreshToken));

        assertThatThrownBy(() -> loginService.authorize(expiredRefreshToken))
                .isInstanceOf(CoreaException.class)
                .satisfies(exception -> {
                    CoreaException coreaException = (CoreaException) exception;
                    assertThat(coreaException.getExceptionType()).isEqualTo(TOKEN_EXPIRED);
                });

        assertThat(loginInfoRepository.findByRefreshToken(expiredRefreshToken)).isEmpty();
    }

    @Test
    @DisplayName("존재하지 않는 RefreshToken으로 AccessToken을 요청하는 경우 예외가 발생한다.")
    void authorizeException() {
        String token = tokenProvider.createToken(MemberFixture.MEMBER_YOUNGSU(), 1600L);

        assertThatThrownBy(() -> loginService.authorize(token))
                .isInstanceOf(CoreaException.class)
                .satisfies(exception -> {
                    CoreaException coreaException = (CoreaException) exception;
                    assertThat(coreaException.getExceptionType()).isEqualTo(INVALID_TOKEN);
                });
    }
}
