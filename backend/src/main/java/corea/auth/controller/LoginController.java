package corea.auth.controller;

import corea.auth.annotation.LoginMember;
import corea.auth.domain.AuthInfo;
import corea.auth.domain.GithubUserInfo;
import corea.auth.dto.LoginRequest;
import corea.auth.dto.RefreshTokenResponse;
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
    public ResponseEntity<RefreshTokenResponse> login(@RequestBody LoginRequest loginRequest) {
        GithubUserInfo userInfo = loginService.getUserInfo(loginRequest.code());
        Member member = loginService.login(userInfo);

        String accessToken = loginService.createAccessToken(member);
        String refreshToken = loginService.publishRefreshToken(member);

        return ResponseEntity.ok()
                .header(AUTHORIZATION_HEADER, accessToken)
                .body(new RefreshTokenResponse(refreshToken));
    }

    @PostMapping("/refresh")
    public ResponseEntity<Void> extendAuthorization(@RequestBody RefreshTokenResponse refreshTokenResponse) {
        Long memberId = loginService.authorize(refreshTokenResponse.refreshToken());
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
