package corea.feedback.controller;

import corea.auth.annotation.LoginMember;
import corea.auth.domain.AuthInfo;
import corea.feedback.dto.ReviewerToRevieweeRequest;
import corea.feedback.dto.ReviewerToRevieweeResponse;
import corea.feedback.service.ReviewerToRevieweeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rooms/{roomId}/reviewee/feedbacks")
@RequiredArgsConstructor
public class ReviewerToRevieweeController implements ReviewerToRevieweeControllerSpecification {

    private final ReviewerToRevieweeService reviewerToRevieweeService;

    @Override
    @PostMapping
    public ResponseEntity<Void> create(@PathVariable long roomId, @LoginMember AuthInfo authInfo, @RequestBody ReviewerToRevieweeRequest request) {
        reviewerToRevieweeService.create(roomId, authInfo.getId(), request);
        return ResponseEntity.ok()
                .build();
    }

    @Override
    @GetMapping
    public ResponseEntity<ReviewerToRevieweeResponse> reviewerToReviewee(
            @PathVariable long roomId, @RequestParam String username, @LoginMember AuthInfo authInfo) {
        ReviewerToRevieweeResponse response = reviewerToRevieweeService.findReviewerToReviewee(roomId, authInfo.getId(), username);
        return ResponseEntity.ok()
                .body(response);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable long roomId, @PathVariable long id, @LoginMember AuthInfo authInfo, @RequestBody ReviewerToRevieweeRequest request) {
        reviewerToRevieweeService.update(id, roomId, authInfo.getId(), request);
        return ResponseEntity.ok()
                .build();
    }
}
