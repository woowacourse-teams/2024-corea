package corea.exception;

import org.springframework.http.HttpStatus;

public class CoreaException extends RuntimeException {

    private final ExceptionType exceptionType;

    public CoreaException(ExceptionType exceptionType) {
        super(exceptionType.getMessage());
        this.exceptionType = exceptionType;
    }

    public CoreaException(ExceptionType exceptionType, String message) {
        super(message);
        this.exceptionType = exceptionType;
    }

    public CoreaException(ExceptionType exceptionType, Throwable cause) {
        super(cause);
        this.exceptionType = exceptionType;
    }

    public HttpStatus getHttpStatus() {
        return exceptionType.getHttpStatus();
    }

    @Override
    public String getMessage() {
        return exceptionType.getMessage();
    }
}
