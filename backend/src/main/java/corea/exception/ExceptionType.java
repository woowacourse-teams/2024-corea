package corea.exception;

import org.springframework.http.HttpStatus;

public enum ExceptionType {

    NOT_FOUND_ERROR(HttpStatus.NOT_FOUND,"해당하는 값이 없습니다."),
    AUTHORIZATION_ERROR(HttpStatus.UNAUTHORIZED,"인증에 실패했습니다."),
    ALREADY_APPLY(HttpStatus.BAD_REQUEST,"해당 방에 이미 참여했습니다"),
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"서버에 문제가 발생했습니다.");
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
