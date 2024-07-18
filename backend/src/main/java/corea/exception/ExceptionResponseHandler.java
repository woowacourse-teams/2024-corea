package corea.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionResponseHandler {

    @ExceptionHandler(CoreaException.class)
    public ResponseEntity<ErrorResponse> handleCoreaException(final CoreaException e) {
        log.debug("Reaction Game exception [statusCode = {}, errorMessage = {}, cause = {}]", e.getHttpStatus(), e.getMessage(),e.getCause());
        return ResponseEntity.status(e.getHttpStatus())
                .body(new ErrorResponse(e.getMessage()));
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(final Exception e) {
        log.debug("Server exception [errorMessage = {}, cause = {}]", e.getMessage(),e.getCause());
        return ResponseEntity.internalServerError()
                .body(new ErrorResponse(e.getMessage()));
    }
}
