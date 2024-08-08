package corea.exception;

public record ErrorResponse(String message) {

    public static ErrorResponse from(ExceptionType type) {
        return new ErrorResponse(type.getMessage());
    }
}
