package corea.auth.service;

import corea.auth.domain.GithubUserInfo;
import corea.auth.domain.LoginInfo;
import corea.auth.infrastructure.GithubClient;
import corea.auth.infrastructure.TokenProperties;
import corea.auth.infrastructure.TokenProvider;
import corea.auth.repository.LoginInfoRepository;
import corea.exception.CoreaException;
import corea.member.domain.Member;
import corea.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static corea.exception.ExceptionType.INVALID_TOKEN;
import static corea.exception.ExceptionType.TOKEN_EXPIRED;
import static corea.global.config.Constants.TOKEN_TYPE;

@EnableConfigurationProperties(TokenProperties.class)
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginService {

    private final LoginInfoRepository loginInfoRepository;
    private final TokenProvider tokenProvider;
    private final TokenProperties tokenProperties;
    private final MemberRepository memberRepository;
    private final GithubClient githubClient;

    public String createAccessToken(Member member) {
        return TOKEN_TYPE.concat(tokenProvider.createToken(member, tokenProperties.expiration().access()));
    }

    @Transactional
    public String publishRefreshToken(Member member) {
        String refreshToken = tokenProvider.createToken(member, tokenProperties.expiration().refresh());
        loginInfoRepository.save(new LoginInfo(member, refreshToken));
        return refreshToken;
    }

    @Transactional
    public Long authorize(String token) {
        validateRefreshToken(token);
        return tokenProvider.findMemberIdByToken(token);
    }

    private void validateRefreshToken(String token) {
        validateExpiration(token);

        LoginInfo info = loginInfoRepository.findByRefreshToken(token)
                .orElseThrow(() -> new CoreaException(INVALID_TOKEN));

        Long tokenMemberId = tokenProvider.findMemberIdByToken(token);
        if (!info.getMember().getId().equals(tokenMemberId)) {
            throw new CoreaException(INVALID_TOKEN);
        }
    }

    private void validateExpiration(String token) {
        try {
            tokenProvider.validateToken(token);
        } catch (CoreaException e) {
            if (e.getExceptionType().equals(TOKEN_EXPIRED)) {
                loginInfoRepository.deleteByRefreshToken(token);
                throw new CoreaException(TOKEN_EXPIRED);
            }
            throw new CoreaException(INVALID_TOKEN);
        }
    }

    @Transactional
    public Member login(GithubUserInfo userInfo) {
        return memberRepository.findByUsername(userInfo.login())
                .orElseGet(() -> register(new Member(userInfo.login(), userInfo.avatarUrl(), userInfo.name(), userInfo.email(), true)));
    }

    private Member register(Member member) {
        return memberRepository.save(member);
    }

    public GithubUserInfo getUserInfo(String code) {
        String accessToken = githubClient.getAccessToken(code);
        return githubClient.getUserInfo(accessToken);
    }

    public void logout(Member member) {
        loginInfoRepository.deleteByMember(member);
    }
}
