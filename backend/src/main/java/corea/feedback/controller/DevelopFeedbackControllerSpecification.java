package corea.feedback.controller;

import corea.auth.domain.AuthInfo;
import corea.exception.ExceptionType;
import corea.feedback.dto.DevelopFeedbackRequest;
import corea.feedback.dto.DevelopFeedbackResponse;
import corea.global.annotation.ApiErrorResponses;
import org.springframework.http.ResponseEntity;

public interface DevelopFeedbackControllerSpecification {

    @ApiErrorResponses(value = {ExceptionType.ALREADY_COMPLETED_FEEDBACK, ExceptionType.NOT_MATCHED_MEMBER})
    ResponseEntity<Void> create(long roomId, AuthInfo authInfo, DevelopFeedbackRequest request);

    @ApiErrorResponses(value = {ExceptionType.FEEDBACK_NOT_FOUND})
    ResponseEntity<DevelopFeedbackResponse> reviewerToReviewee(long roomId, String username, AuthInfo authInfo);

    @ApiErrorResponses(value = {ExceptionType.FEEDBACK_NOT_FOUND, ExceptionType.NOT_MATCHED_MEMBER})
    ResponseEntity<Void> update(long roomId, long feedbackId, AuthInfo authInfo, DevelopFeedbackRequest request);
}