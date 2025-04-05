package corea.auth.controller;

import corea.auth.annotation.LoginMember;
import corea.auth.annotation.RefreshToken;
import corea.auth.domain.AuthInfo;
import corea.auth.domain.TokenInfo;
import corea.auth.dto.GithubUserInfo;
import corea.auth.dto.LoginRequest;
import corea.auth.dto.LoginResponse;
import corea.auth.dto.TokenRefreshRequest;
import corea.auth.infrastructure.CookieProvider;
import corea.auth.service.GithubOAuthProvider;
import corea.auth.service.LoginService;
import corea.auth.service.LogoutService;
import corea.member.dto.MemberRoleResponse;
import corea.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static corea.global.config.Constants.*;
import static org.springframework.http.HttpHeaders.SET_COOKIE;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class LoginController implements LoginControllerSpecification {

    private final GithubOAuthProvider githubOAuthProvider;
    private final CookieProvider cookieProvider;
    private final LoginService loginService;
    private final LogoutService logoutService;
    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        GithubUserInfo userInfo = githubOAuthProvider.getUserInfo(loginRequest.code());
        TokenInfo tokenInfo = loginService.login(userInfo);
        ResponseCookie refreshCookie = cookieProvider.createCookie(REFRESH_COOKIE, tokenInfo.refreshToken(), COOKIE_EXPIRATION);
        MemberRoleResponse memberRoleResponse = memberService.getMemberRoleWithGithubUserId(userInfo.id());

        return ResponseEntity.ok()
                .header(AUTHORIZATION_HEADER, tokenInfo.accessToken())
                .header(SET_COOKIE, refreshCookie.toString())
                .body(new LoginResponse(userInfo, memberRoleResponse.role()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<Void> extendAuthorization(@RefreshToken TokenRefreshRequest tokenRefreshRequest) {
        String accessToken = loginService.refresh(tokenRefreshRequest.refreshToken());
        return ResponseEntity.ok()
                .header(AUTHORIZATION_HEADER, accessToken)
                .build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@LoginMember AuthInfo authInfo) {
        logoutService.logoutByUser(authInfo.getId());
        ResponseCookie expiredRefreshCookie = cookieProvider.createExpiredCookie(REFRESH_COOKIE);
        return ResponseEntity.ok()
                .header(SET_COOKIE, expiredRefreshCookie.toString())
                .build();
    }
}
