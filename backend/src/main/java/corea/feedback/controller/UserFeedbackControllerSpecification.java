package corea.feedback.controller;

import corea.auth.domain.AuthInfo;
import corea.exception.ExceptionType;
import corea.feedback.dto.UserFeedbackResponse;
import corea.global.annotation.ApiErrorResponses;
import org.springframework.http.ResponseEntity;

public interface UserFeedbackControllerSpecification {

    @ApiErrorResponses(value = {ExceptionType.ROOM_NOT_FOUND})
    ResponseEntity<UserFeedbackResponse> receivedFeedback(AuthInfo authInfo);

    @ApiErrorResponses(value = {ExceptionType.ROOM_NOT_FOUND})
    ResponseEntity<UserFeedbackResponse> deliveredFeedback(AuthInfo authInfo);
}
