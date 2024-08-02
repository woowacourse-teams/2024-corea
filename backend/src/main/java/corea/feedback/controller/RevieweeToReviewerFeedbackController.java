package corea.feedback.controller;

import corea.auth.annotation.LoginMember;
import corea.auth.domain.AuthInfo;
import corea.feedback.dto.RevieweeToReviewerFeedbackRequest;
import corea.feedback.dto.RevieweeToReviewerResponse;
import corea.feedback.service.RevieweeToReviewerFeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rooms/{roomId}/reviewer/feedbacks")
@RequiredArgsConstructor
public class RevieweeToReviewerFeedbackController implements RevieweeToReviewerFeedbackControllerSpecification {

    private final RevieweeToReviewerFeedbackService revieweeToReviewerFeedbackService;

    @Override
    @PostMapping
    public ResponseEntity<Void> create(@PathVariable long roomId, @LoginMember AuthInfo authInfo, @RequestBody RevieweeToReviewerFeedbackRequest request) {
        revieweeToReviewerFeedbackService.create(roomId, authInfo.getId(), request);
        return ResponseEntity.ok()
                .build();
    }

    @Override
    @GetMapping
    public ResponseEntity<RevieweeToReviewerResponse> revieweeToReviewer(@PathVariable long roomId, @RequestParam String username, @LoginMember AuthInfo authInfo) {
        RevieweeToReviewerResponse response = revieweeToReviewerFeedbackService.findRevieweeToReviewerFeedback(roomId, authInfo.getId(), username);
        return ResponseEntity.ok()
                .body(response);
    }

    @Override
    @PutMapping("/{feedbackId}")
    public ResponseEntity<Void> update(@PathVariable long roomId, @PathVariable long feedbackId, @LoginMember AuthInfo authInfo, @RequestBody RevieweeToReviewerFeedbackRequest request) {
        revieweeToReviewerFeedbackService.update(feedbackId, roomId, authInfo.getId(), request);
        return ResponseEntity.ok()
                .build();
    }
}
