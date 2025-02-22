package corea.auth.service;

import corea.auth.domain.LoginInfo;
import corea.auth.domain.TokenInfo;
import corea.auth.dto.GithubUserInfo;
import corea.auth.infrastructure.CookieProvider;
import corea.auth.repository.LoginInfoRepository;
import corea.exception.CoreaException;
import corea.member.domain.Member;
import corea.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static corea.exception.ExceptionType.INVALID_TOKEN;
import static corea.exception.ExceptionType.TOKEN_EXPIRED;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginService {

    private final LoginInfoRepository loginInfoRepository;
    private final MemberRepository memberRepository;
    private final TokenService tokenService;
    private final LogoutService logoutService;
    private final CookieProvider cookieProvider;

    @Transactional
    public TokenInfo login(GithubUserInfo userInfo) {
        Member member = memberRepository.findByGithubUserId(userInfo.id())
                .orElseGet(() -> register(userInfo));

        String accessToken = tokenService.createAccessToken(member);
        String refreshToken = extendAuthorization(member);
        return new TokenInfo(accessToken, refreshToken);
    }

    private Member register(GithubUserInfo userInfo) {
        Member member = memberRepository.save(new Member(userInfo.login(), userInfo.avatarUrl(), userInfo.name(), userInfo.id()));
        logCreateMembers(member);
        return member;
    }

    private void logCreateMembers(Member member) {
        log.info("멤버를 생성했습니다. 멤버 id={}, 멤버 이름={},깃허브 id={}, 닉네임={}", member.getId(), member.getName(), member.getGithubUserId(), member.getUsername());
    }

    private String extendAuthorization(Member member) {
        String refreshToken = tokenService.createRefreshToken(member);
        loginInfoRepository.findByMemberId(member.getId())
                .ifPresentOrElse(
                        loginInfo -> loginInfoRepository.save(loginInfo.changeRefreshToken(refreshToken)),
                        () -> loginInfoRepository.save(new LoginInfo(member, refreshToken))
                );
        return refreshToken;
    }

    @Transactional
    public String refresh(String refreshToken) {
        try {
            tokenService.validateToken(refreshToken);
            LoginInfo info = loginInfoRepository.findByRefreshToken(refreshToken)
                    .orElseThrow(() -> new CoreaException(INVALID_TOKEN));
            return tokenService.createAccessToken(info.getMember());
        } catch (CoreaException e) {
            if (e.getExceptionType()
                    .equals(TOKEN_EXPIRED)) {
                logoutService.logoutByExpiredRefreshToken(refreshToken);
            }
            throw e;
        }
    }
}
