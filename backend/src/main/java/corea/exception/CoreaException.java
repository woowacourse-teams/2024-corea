package corea.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

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
        super(exceptionType.getMessage(), cause);
        this.exceptionType = exceptionType;
    }

    public boolean isExceptionType(ExceptionType exceptionType) {
        return this.exceptionType.equals(exceptionType);
    }

    public HttpStatus getHttpStatus() {
        return exceptionType.getHttpStatus();
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
