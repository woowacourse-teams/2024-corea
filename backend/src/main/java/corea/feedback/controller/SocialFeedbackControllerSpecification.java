package corea.feedback.controller;

import corea.auth.domain.AuthInfo;
import corea.exception.ExceptionType;
import corea.feedback.dto.SocialFeedbackRequest;
import corea.feedback.dto.SocialFeedbackResponse;
import corea.global.annotation.ApiErrorResponses;
import org.springframework.http.ResponseEntity;

public interface SocialFeedbackControllerSpecification {

    @ApiErrorResponses(value = {ExceptionType.ALREADY_COMPLETED_FEEDBACK, ExceptionType.NOT_MATCHED_MEMBER})
    ResponseEntity<Void> create(long roomId, AuthInfo authInfo, SocialFeedbackRequest request);

    @ApiErrorResponses(value = {ExceptionType.FEEDBACK_NOT_FOUND})
    ResponseEntity<SocialFeedbackResponse> revieweeToReviewer(long roomId, String username, AuthInfo authInfo);

    @ApiErrorResponses(value = {ExceptionType.FEEDBACK_NOT_FOUND, ExceptionType.NOT_MATCHED_MEMBER})
    ResponseEntity<Void> update(long roomId, long feedbackId, AuthInfo authInfo, SocialFeedbackRequest request);
}
