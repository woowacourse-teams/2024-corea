package corea.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@ControllerAdvice
public class ExceptionResponseHandler {

    @ExceptionHandler(CoreaException.class)
    public ResponseEntity<ErrorResponse> handleCoreaException(final CoreaException e) {
        log.debug("Corea Custom exception [statusCode = {}, errorMessage = {}, cause = {}]", e.getHttpStatus(), e.getMessage(), e.getCause());
        return ResponseEntity.status(e.getHttpStatus())
                .body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResultException(final NoResourceFoundException e) {
        log.warn("No Resource exception [errorMessage = {}, cause = {},error ={}]", e.getMessage(), e.getCause(), e);
        return ResponseEntity.notFound()
                .build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(final Exception e) {
        log.error("Server exception [errorMessage = {}, cause = {},error ={}]", e.getMessage(), e.getCause(), e);
        return ResponseEntity.internalServerError()
                .body(new ErrorResponse(e.getMessage()));
    }
}
