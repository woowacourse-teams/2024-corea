package corea.feedback.controller;

import corea.auth.annotation.LoginMember;
import corea.auth.domain.AuthInfo;
import corea.feedback.dto.SocialFeedbackCreateRequest;
import corea.feedback.dto.SocialFeedbackResponse;
import corea.feedback.dto.SocialFeedbackUpdateRequest;
import corea.feedback.service.SocialFeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rooms/{roomId}/social/feedbacks")
@RequiredArgsConstructor
public class SocialFeedbackController implements SocialFeedbackControllerSpecification {

    private final SocialFeedbackService socialFeedbackService;

    @PostMapping
    public ResponseEntity<Void> create(@PathVariable long roomId,
                                       @LoginMember AuthInfo authInfo,
                                       @RequestBody SocialFeedbackCreateRequest request) {
        socialFeedbackService.create(roomId, authInfo.getId(), request);
        return ResponseEntity.ok()
                .build();
    }

    @GetMapping
    public ResponseEntity<SocialFeedbackResponse> socialFeedback(@PathVariable long roomId,
                                                                 @RequestParam String username,
                                                                 @LoginMember AuthInfo authInfo) {
        SocialFeedbackResponse response = socialFeedbackService.findSocialFeedback(roomId, authInfo.getId(), username);
        return ResponseEntity.ok()
                .body(response);
    }

    @PutMapping("/{feedbackId}")
    public ResponseEntity<Void> update(@PathVariable long roomId,
                                       @PathVariable long feedbackId,
                                       @LoginMember AuthInfo authInfo,
                                       @RequestBody SocialFeedbackUpdateRequest request) {
        socialFeedbackService.update(feedbackId, authInfo.getId(), request);
        return ResponseEntity.ok()
                .build();
    }
}
