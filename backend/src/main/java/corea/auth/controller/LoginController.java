package corea.auth.controller;

import corea.auth.domain.GithubUserInfo;
import corea.auth.infrastructure.GithubClient;
import corea.auth.infrastructure.TokenProvider;
import corea.auth.service.LoginService;
import corea.member.domain.Member;
import corea.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class LoginController implements LoginControllerSpecification {

    private final GithubClient githubClient;
    private final LoginService loginService;
    private final MemberService memberService;
    private final TokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<Void> login(HttpServletRequest request) {
        String code = request.getParameter("code");
        String accessToken = githubClient.getAccessToken(code);

        GithubUserInfo userInfo = githubClient.getUserInfo(accessToken);
        Member member = memberService.login(userInfo);

        String token = loginService.createAccessToken(member);
        Cookie cookie = loginService.createRefreshCookie(member);

        return ResponseEntity.ok()
                .header("Authorization", token)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<Void> extendAuthorization(HttpServletRequest request) {
        String refreshToken = request.getParameter("token");
        loginService.validateRefreshToken(refreshToken);

        Long memberId = tokenProvider.getPayload(refreshToken).get("id", Long.class);
        Member member = memberService.findById(memberId);

        String token = loginService.createAccessToken(member);

        return ResponseEntity.ok()
                .header("Authorization", token)
                .build();
    }
}
