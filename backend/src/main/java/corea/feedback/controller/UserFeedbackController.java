package corea.feedback.controller;

import corea.auth.annotation.LoginMember;
import corea.auth.domain.AuthInfo;
import corea.feedback.dto.UserFeedbackResponse;
import corea.feedback.service.UserFeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/feedbacks")
@RequiredArgsConstructor
public class UserFeedbackController implements UserFeedbackControllerSpecification {

    private final UserFeedbackService userFeedbackService;

    @GetMapping("/received")
    public ResponseEntity<UserFeedbackResponse> receivedFeedback(@LoginMember AuthInfo authInfo) {
        return ResponseEntity.ok()
                .body(userFeedbackService.getReceivedFeedback(authInfo.getId()));
    }

    @GetMapping("/delivered")
    public ResponseEntity<UserFeedbackResponse> deliveredFeedback(@LoginMember AuthInfo authInfo) {
        return ResponseEntity.ok()
                .body(userFeedbackService.getDeliveredFeedback(authInfo.getId()));
    }
}
