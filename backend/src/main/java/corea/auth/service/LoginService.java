package corea.auth.service;

import corea.auth.domain.LoginInfo;
import corea.auth.domain.TokenInfo;
import corea.auth.dto.GithubUserInfo;
import corea.auth.repository.LoginInfoRepository;
import corea.exception.CoreaException;
import corea.member.domain.Member;
import corea.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static corea.exception.ExceptionType.INVALID_TOKEN;
import static corea.exception.ExceptionType.TOKEN_EXPIRED;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginService {

    private final LoginInfoRepository loginInfoRepository;
    private final MemberRepository memberRepository;
    private final TokenService tokenService;
    private final LogoutService logoutService;

    @Transactional
    public TokenInfo login(GithubUserInfo userInfo) {
        Member member = memberRepository.findByUsername(userInfo.login())
                .orElseGet(() -> register(userInfo));

        String accessToken = tokenService.createAccessToken(member);
        String refreshToken = extendAuthorization(member);
        return new TokenInfo(accessToken, refreshToken);
    }

    private Member register(GithubUserInfo userInfo) {
        return memberRepository.save(new Member(userInfo.login(), userInfo.avatarUrl(), userInfo.name(), userInfo.email(), true, userInfo.githubUserId()));
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
            if (e.getExceptionType().equals(TOKEN_EXPIRED)) {
                logoutService.logoutByExpiredRefreshToken(refreshToken);
            }
            throw e;
        }
    }
}
