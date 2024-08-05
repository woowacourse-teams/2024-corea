package corea.auth.controller;

import corea.exception.ExceptionType;
import corea.exception.ExceptionTypeGroup;
import corea.global.annotation.ApiErrorResponses;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface LoginControllerSpecification {

    @ApiErrorResponses(value = {ExceptionType.MEMBER_NOT_FOUND, ExceptionType.GITHUB_AUTHORIZATION_ERROR},
            groups = ExceptionTypeGroup.INTERNAL_SERVER_ERROR)
    ResponseEntity<Void> login(HttpServletRequest request);

    @ApiErrorResponses(value = {ExceptionType.TOKEN_EXPIRED, ExceptionType.INVALID_TOKEN},
            groups = ExceptionTypeGroup.INTERNAL_SERVER_ERROR)
    ResponseEntity<Void> extendAuthorization(HttpServletRequest request);

}

