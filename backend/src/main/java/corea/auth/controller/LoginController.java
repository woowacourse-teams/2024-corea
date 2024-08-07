package corea.auth.controller;

import corea.auth.annotation.LoginMember;
import corea.auth.domain.AuthInfo;
import corea.auth.domain.GithubUserInfo;
import corea.auth.infrastructure.CookieProvider;
import corea.auth.repository.LoginInfoRepository;
import corea.auth.service.LoginService;
import corea.member.domain.Member;
import corea.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
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
    private final CookieProvider cookieProvider;
    private final LoginInfoRepository loginInfoRepository;

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody String code) {
        GithubUserInfo userInfo = loginService.getUserInfo(code);
        Member member = loginService.login(userInfo);

        String accessToken = loginService.createAccessToken(member);
        String refreshToken = loginService.publishRefreshToken(member);
        Cookie cookie = cookieProvider.createCookie(refreshToken);

        return ResponseEntity.ok()
                .header(AUTHORIZATION_HEADER, accessToken)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<Void> extendAuthorization(@RequestBody String token) {
        Long memberId = loginService.authorize(token);
        Member member = memberService.findById(memberId);

        String accessToken = loginService.createAccessToken(member);

        return ResponseEntity.ok()
                .header(AUTHORIZATION_HEADER, accessToken)
                .build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@LoginMember AuthInfo authInfo) {
        Member member = memberService.findById(authInfo.getId());
        Cookie cookie = cookieProvider.createEmptyCookie();

        loginService.logout(member);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }
}
