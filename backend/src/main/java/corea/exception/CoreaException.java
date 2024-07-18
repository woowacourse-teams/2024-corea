package corea.exception;

import lombok.Getter;

@Getter
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
}
