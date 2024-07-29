package corea.participation.controller;

import corea.auth.domain.AuthInfo;
import corea.exception.ExceptionType;
import corea.exception.ExceptionTypeGroup;
import corea.global.annotation.ApiErrorResponses;
import org.springframework.http.ResponseEntity;

public interface ParticipationControllerSpecification {

    @ApiErrorResponses(value = {ExceptionType.MEMBER_NOT_FOUND, ExceptionType.ROOM_NOT_FOUND, ExceptionType.ALREADY_APPLY},
            groups = ExceptionTypeGroup.INTERNAL_SERVER_ERROR)
    ResponseEntity<Void> participate(long id, AuthInfo authInfo);
}

