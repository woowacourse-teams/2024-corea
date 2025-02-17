package corea.auth.service;

import config.ServiceTest;
import corea.auth.domain.LoginInfo;
import corea.auth.domain.TokenInfo;
import corea.auth.dto.GithubUserInfo;
import corea.auth.infrastructure.TokenProperties;
import corea.auth.infrastructure.TokenProvider;
import corea.auth.repository.LoginInfoRepository;
import corea.exception.CoreaException;
import corea.fixture.MemberFixture;
import corea.member.domain.Member;
import corea.member.repository.MemberRepository;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.crypto.SecretKey;

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

    @Autowired
    private TokenProperties tokenProperties;

    @Autowired
    private TokenService tokenService;

    private Member member;
    private SecretKey secretKey;

    @BeforeEach
    void setup() {
        secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(tokenProperties.secretKey()));
    }


    @BeforeEach
    void setUp() {
        member = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());
    }

    @Test
    @DisplayName("RefreshToken에 문제가 없을 경우 예외가 발생하지 않는다.")
    void refresh() {
        String refreshToken = tokenService.createRefreshToken(member);
        loginInfoRepository.save(new LoginInfo(member, refreshToken));

        assertThatCode(() -> loginService.refresh(refreshToken))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("RefreshToken가 이미 저장되어 있는 경우 새로운 RefreshToken을 발행한다.")
    void extendAuthorization() {
        String refreshToken = tokenProvider.createToken(member, 1500L, secretKey);
        loginInfoRepository.save(new LoginInfo(member, refreshToken));

        TokenInfo tokenInfo = loginService.login(new GithubUserInfo(
                "youngsu5582",
                "조희선",
                "https://gongu.copyright.or.kr/",
                "98307410"
        ));
        assertThat(tokenInfo.getRefreshToken()).isNotEqualTo(refreshToken);
    }

    @Test
    @DisplayName("RefreshToken의 유효기간이 만료되었을 경우 DB에서 해당 유저의 로그인 정보를 삭제한다.")
    void authorizeException_TokenExpired() throws InterruptedException {
        String expiredRefreshToken = tokenProvider.createToken(member, 5L, secretKey);

        loginInfoRepository.save(new LoginInfo(member, expiredRefreshToken));

        Thread.sleep(5);
        assertThatThrownBy(() -> loginService.refresh(expiredRefreshToken))
                .isInstanceOf(CoreaException.class)
                .satisfies(exception -> {
                    CoreaException coreaException = (CoreaException) exception;
                    assertThat(coreaException.getExceptionType()).isEqualTo(TOKEN_EXPIRED);
                });

        assertThat(loginInfoRepository.findByRefreshToken(expiredRefreshToken)).isEmpty();
    }

    @Test
    @DisplayName("존재하지 않는 RefreshToken으로 AccessToken을 요청하는 경우 예외가 발생한다.")
    void refreshException() {
        String token = tokenService.createRefreshToken(MemberFixture.MEMBER_YOUNGSU());

        assertThatThrownBy(() -> loginService.refresh(token))
                .isInstanceOf(CoreaException.class)
                .satisfies(exception -> {
                    CoreaException coreaException = (CoreaException) exception;
                    assertThat(coreaException.getExceptionType()).isEqualTo(INVALID_TOKEN);
                });
    }
}
