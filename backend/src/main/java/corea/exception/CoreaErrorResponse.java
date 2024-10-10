package corea.exception;

public record CoreaErrorResponse(ExceptionType exceptionType, String message) {

    public static CoreaErrorResponse from(CoreaException e) {
        return new CoreaErrorResponse(e.getExceptionType(), e.getMessage());
    }
}
