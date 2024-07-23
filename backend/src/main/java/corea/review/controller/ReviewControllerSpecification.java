package corea.review.controller;

import corea.auth.domain.AuthInfo;
import corea.review.dto.ReviewRequest;
import org.springframework.http.ResponseEntity;

public interface ReviewControllerSpecification {
    
    ResponseEntity<Void> complete(AuthInfo authInfo, ReviewRequest request);
}
