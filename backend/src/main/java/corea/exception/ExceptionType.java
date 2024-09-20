package corea.exception;

import org.springframework.http.HttpStatus;

public enum ExceptionType {

    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버에 문제가 발생했습니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 멤버를 찾을 수 없습니다."),
    ROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "방을 찾을 수 없습니다."),
    NOT_FOUND_ERROR(HttpStatus.NOT_FOUND, "해당하는 값이 없습니다."),
    AUTHORIZATION_ERROR(HttpStatus.UNAUTHORIZED, "인증에 실패했습니다."),
    ALREADY_APPLY(HttpStatus.BAD_REQUEST, "해당 방에 이미 참여했습니다."),
    NOT_ALREADY_APPLY(HttpStatus.BAD_REQUEST, "아직 참여하지 않은 방입니다."),
    ROOM_STATUS_INVALID(HttpStatus.BAD_REQUEST, "방이 마감되었습니다."),
    ROOM_PARTICIPANT_EXCEED(HttpStatus.BAD_REQUEST, "방 참여 인원 수가 최대입니다."),
    PARTICIPANT_SIZE_LACK(HttpStatus.BAD_REQUEST, "참여 인원 수가 부족합니다."),
    NOT_MATCHED_MEMBER(HttpStatus.BAD_REQUEST, "매칭된 인원들이 아닙니다."),
    ALREADY_COMPLETED_REVIEW(HttpStatus.BAD_REQUEST, "이미 리뷰를 완료했습니다."),
    ALREADY_COMPLETED_FEEDBACK(HttpStatus.BAD_REQUEST, "이미 작성한 피드백이 존재합니다."),
    INVALID_CALCULATION_FORMULA(HttpStatus.BAD_REQUEST, "잘못된 계산식입니다."),
    INVALID_VALUE(HttpStatus.BAD_REQUEST, "올바르지 않은 값입니다."),
    FEEDBACK_NOT_FOUND(HttpStatus.NOT_FOUND, "피드백을 찾을 수 없습니다."),
    INVALID_RECRUITMENT_DEADLINE(HttpStatus.BAD_REQUEST, "올바르지 않은 모집 마감 시간입니다."),
    INVALID_REVIEW_DEADLINE(HttpStatus.BAD_REQUEST, "올바르지 않은 리뷰 마감 시간입니다."),
    COOKIE_NOT_EXIST(HttpStatus.NOT_FOUND, "해당 쿠키를 찾을 수 없습니다."),
    GITHUB_AUTHORIZATION_ERROR(HttpStatus.SERVICE_UNAVAILABLE, "깃허브 인증 서버가 원활하게 작동하지 않습니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 올바르지 않습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;

    ExceptionType(final HttpStatus httpStatus, final String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }
}
