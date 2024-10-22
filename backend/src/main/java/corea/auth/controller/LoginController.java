package corea.auth.controller;

import corea.auth.annotation.LoginMember;
import corea.auth.domain.AuthInfo;
import corea.auth.domain.TokenInfo;
import corea.auth.dto.GithubUserInfo;
import corea.auth.dto.LoginRequest;
import corea.auth.dto.LoginResponse;
import corea.auth.dto.TokenRefreshRequest;
import corea.auth.service.GithubOAuthProvider;
import corea.auth.service.LoginService;
import corea.auth.service.LogoutService;
import corea.member.dto.MemberRoleResponse;
import corea.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static corea.global.config.Constants.AUTHORIZATION_HEADER;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class LoginController implements LoginControllerSpecification {

    private final GithubOAuthProvider githubOAuthProvider;
    private final LoginService loginService;
    private final LogoutService logoutService;
    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        GithubUserInfo userInfo = githubOAuthProvider.getUserInfo(loginRequest.code());
        TokenInfo tokenInfo = loginService.login(userInfo);
        MemberRoleResponse memberRoleResponse = memberService.getMemberRoleWithGithubUserId(userInfo.id());

        return ResponseEntity.ok()
                .header(AUTHORIZATION_HEADER, tokenInfo.accessToken())
                .body(new LoginResponse(tokenInfo.refreshToken(), userInfo, memberRoleResponse.role()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<Void> extendAuthorization(@RequestBody TokenRefreshRequest tokenRefreshRequest) {
        String accessToken = loginService.refresh(tokenRefreshRequest.refreshToken());
        return ResponseEntity.ok()
                .header(AUTHORIZATION_HEADER, accessToken)
                .build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@LoginMember AuthInfo authInfo) {
        logoutService.logoutByUser(authInfo.getId());
        return ResponseEntity.ok()
                .build();
    }
}
