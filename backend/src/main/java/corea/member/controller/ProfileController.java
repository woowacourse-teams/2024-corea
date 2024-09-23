package corea.member.controller;

import corea.auth.annotation.LoginMember;
import corea.auth.domain.AuthInfo;
import corea.member.dto.ProfileResponse;
import corea.member.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProfileController implements ProfileControllerSpecification {

    private final ProfileService profileService;

    @Override
    @GetMapping("/user/profile")
    public ResponseEntity<ProfileResponse> getMyProfile(@LoginMember AuthInfo authInfo) {
        ProfileResponse response = profileService.findProfileInfoById(authInfo.getId());
        return ResponseEntity.ok(response);
    }

    @Override
    @GetMapping("/user/profile/by")
    public ResponseEntity<ProfileResponse> getWithUserName(@RequestParam String username) {
        ProfileResponse response = profileService.findProfileInfoByUsername(username);
        return ResponseEntity.ok(response);
    }
}
