package corea.member.controller;

import corea.auth.annotation.LoginMember;
import corea.auth.domain.AuthInfo;
import corea.member.dto.ProfileResponse;
import corea.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController implements MemberControllerSpecification {

    private final MemberService memberService;

    @Override
    @GetMapping("/user/profile")
    public ResponseEntity<ProfileResponse> profile(@LoginMember AuthInfo authInfo) {
        ProfileResponse response = memberService.findProfileInfoById(authInfo.getId());
        return ResponseEntity.ok(response);
    }
}
