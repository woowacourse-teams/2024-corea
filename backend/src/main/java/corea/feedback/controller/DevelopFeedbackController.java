package corea.feedback.controller;

import corea.auth.annotation.LoginMember;
import corea.auth.domain.AuthInfo;
import corea.feedback.dto.DevelopFeedbackRequest;
import corea.feedback.dto.DevelopFeedbackResponse;
import corea.feedback.service.DevelopFeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rooms/{roomId}/develop/feedbacks")
@RequiredArgsConstructor
public class DevelopFeedbackController implements DevelopFeedbackControllerSpecification {

    private final DevelopFeedbackService developFeedbackService;

    @Override
    @PostMapping
    public ResponseEntity<Void> create(@PathVariable long roomId, @LoginMember AuthInfo authInfo, @RequestBody DevelopFeedbackRequest request) {
        developFeedbackService.create(roomId, authInfo.getId(), request);
        return ResponseEntity.ok()
                .build();
    }

    @Override
    @GetMapping
    public ResponseEntity<DevelopFeedbackResponse> developFeedback(
            @PathVariable long roomId, @RequestParam String username, @LoginMember AuthInfo authInfo) {
        DevelopFeedbackResponse response = developFeedbackService.findDevelopFeedback(roomId, authInfo.getId(), username);
        return ResponseEntity.ok()
                .body(response);
    }

    @Override
    @PutMapping("/{feedbackId}")
    public ResponseEntity<Void> update(
            @PathVariable long roomId, @PathVariable long feedbackId, @LoginMember AuthInfo authInfo, @RequestBody DevelopFeedbackRequest request) {
        developFeedbackService.update(feedbackId, roomId, authInfo.getId(), request);
        return ResponseEntity.ok()
                .build();
    }
}
