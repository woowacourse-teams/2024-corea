package corea.exception;

public class CoreaException extends Exception {
    private final ExceptionType exceptionType;

    public CoreaException(ExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    public CoreaException(ExceptionType exceptionType, Throwable cause) {
        super(cause);
        this.exceptionType = exceptionType;
    }

    public ExceptionType getExceptionType() {
        return exceptionType;
    }
}
