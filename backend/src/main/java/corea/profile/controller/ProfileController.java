package corea.profile.controller;

import corea.auth.annotation.LoginMember;
import corea.auth.domain.AuthInfo;
import corea.profile.dto.ProfileResponse;
import corea.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/user/profile")
    public ResponseEntity<ProfileResponse> profile(@LoginMember AuthInfo authInfo) {
        ProfileResponse response = profileService.findOne(authInfo.getId());
        return ResponseEntity.ok(response);
    }
}
