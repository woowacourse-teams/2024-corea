package corea.exception;

import org.springframework.http.HttpStatus;

public enum ExceptionType {

    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"서버에 문제가 발생했습니다."),
    MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 멤버를 찾을 수 없습니다."),
    ROOM_NOT_FOUND(HttpStatus.BAD_REQUEST, "방을 찾을 수 없습니다."),
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
