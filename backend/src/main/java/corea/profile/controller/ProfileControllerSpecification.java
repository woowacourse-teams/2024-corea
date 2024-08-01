package corea.profile.controller;

import corea.auth.domain.AuthInfo;
import corea.exception.ExceptionType;
import corea.exception.ExceptionTypeGroup;
import corea.global.annotation.ApiErrorResponses;
import corea.profile.dto.ProfileResponse;
import org.springframework.http.ResponseEntity;

public interface ProfileControllerSpecification {

    @ApiErrorResponses(value = {ExceptionType.MEMBER_NOT_FOUND}, groups = ExceptionTypeGroup.INTERNAL_SERVER_ERROR)
    ResponseEntity<ProfileResponse> profile(AuthInfo authInfo);
}
