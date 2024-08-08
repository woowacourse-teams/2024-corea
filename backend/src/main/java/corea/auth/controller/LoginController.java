package corea.auth.controller;

import corea.auth.annotation.LoginMember;
import corea.auth.domain.AuthInfo;
import corea.auth.dto.GithubUserInfo;
import corea.auth.dto.LoginRequest;
import corea.auth.dto.LoginResponse;
import corea.auth.dto.TokenRefreshRequest;
import corea.auth.service.LoginService;
import corea.member.domain.Member;
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

    private final LoginService loginService;
    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        GithubUserInfo userInfo = loginService.getUserInfo(loginRequest.code());
        Member member = loginService.login(userInfo);

        String accessToken = loginService.createAccessToken(member);
        String refreshToken = loginService.publishRefreshToken(member);

        return ResponseEntity.ok()
                .header(AUTHORIZATION_HEADER, accessToken)
                .body(new LoginResponse(refreshToken, userInfo));
    }

    @PostMapping("/refresh")
    public ResponseEntity<Void> extendAuthorization(@RequestBody TokenRefreshRequest tokenRefreshRequest) {
        Long memberId = loginService.authorize(tokenRefreshRequest.refreshToken());
        Member member = memberService.findById(memberId);

        String accessToken = loginService.createAccessToken(member);

        return ResponseEntity.ok()
                .header(AUTHORIZATION_HEADER, accessToken)
                .build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@LoginMember AuthInfo authInfo) {
        loginService.logout(authInfo.getId());

        return ResponseEntity.ok()
                .build();
    }
}
