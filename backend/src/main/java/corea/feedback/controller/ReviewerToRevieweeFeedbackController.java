package corea.feedback.controller;

import corea.auth.annotation.LoginMember;
import corea.auth.domain.AuthInfo;
import corea.feedback.dto.ReviewerToRevieweeRequest;
import corea.feedback.dto.ReviewerToRevieweeResponse;
import corea.feedback.service.ReviewerToRevieweeFeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rooms/{roomId}/reviewee/feedbacks")
@RequiredArgsConstructor
public class ReviewerToRevieweeFeedbackController implements ReviewerToRevieweeControllerSpecification {

    private final ReviewerToRevieweeFeedbackService reviewerToRevieweeFeedbackService;

    @Override
    @PostMapping
    public ResponseEntity<Void> create(@PathVariable long roomId, @LoginMember AuthInfo authInfo, @RequestBody ReviewerToRevieweeRequest request) {
        reviewerToRevieweeFeedbackService.create(roomId, authInfo.getId(), request);
        return ResponseEntity.ok()
                .build();
    }

    @Override
    @GetMapping
    public ResponseEntity<ReviewerToRevieweeResponse> reviewerToReviewee(
            @PathVariable long roomId, @RequestParam String username, @LoginMember AuthInfo authInfo) {
        ReviewerToRevieweeResponse response = reviewerToRevieweeFeedbackService.findReviewerToReviewee(roomId, authInfo.getId(), username);
        return ResponseEntity.ok()
                .body(response);
    }

    @Override
    @PutMapping("/{feedbackId}")
    public ResponseEntity<Void> update(
            @PathVariable long roomId, @PathVariable long feedbackId, @LoginMember AuthInfo authInfo, @RequestBody ReviewerToRevieweeRequest request) {
        reviewerToRevieweeFeedbackService.update(feedbackId, roomId, authInfo.getId(), request);
        return ResponseEntity.ok()
                .build();
    }
}
