package corea.review.controller;

import corea.auth.annotation.LoginMember;
import corea.auth.domain.AuthInfo;
import corea.review.dto.ReviewRequest;
import corea.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController implements ReviewControllerSpecification{
    private final ReviewService reviewService;

    @PostMapping("/complete")
    public ResponseEntity<Void> complete(@LoginMember AuthInfo authInfo, @RequestBody ReviewRequest request) {
        reviewService.review(request.roomId(), authInfo.getId(), request.revieweeId());
        return ResponseEntity.ok()
                .build();
    }
}
