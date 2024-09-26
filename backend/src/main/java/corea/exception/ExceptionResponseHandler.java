package corea.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@ControllerAdvice
public class ExceptionResponseHandler {
    private static final String SERVER_ERROR_MESSAGE = "서버에 문제가 발생했습니다.";

    @ExceptionHandler(CoreaException.class)
    public ResponseEntity<ErrorResponse> handleCoreaException(final CoreaException e) {
        log.debug("Corea Custom exception [statusCode = {}, errorMessage = {}, cause = {}]", e.getHttpStatus(), e.getMessage(), e.getStackTrace());
        return ResponseEntity.status(e.getHttpStatus())
                .body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResultException(final NoResourceFoundException e) {
        return ResponseEntity.notFound()
                .build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(final Exception e) {
        log.error("Server exception [errorMessage = {}, cause = {},error ={}]", e.getMessage(), e.getCause(), e.getStackTrace());
        return ResponseEntity.internalServerError()
                .body(new ErrorResponse(SERVER_ERROR_MESSAGE));
    }
}
