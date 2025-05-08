package corea.exception;

import corea.auth.infrastructure.CookieProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import static corea.exception.ExceptionType.TOKEN_EXPIRED;
import static corea.global.config.Constants.REFRESH_COOKIE;
import static org.springframework.http.HttpHeaders.SET_COOKIE;

@Slf4j
@ControllerAdvice
public class ExceptionResponseHandler {

    private static final String SERVER_ERROR_MESSAGE = "서버에 문제가 발생했습니다.";

    private final CookieProvider cookieProvider;

    public ExceptionResponseHandler(CookieProvider cookieProvider) {
        this.cookieProvider = cookieProvider;
    }

    @ExceptionHandler(CoreaException.class)
    public ResponseEntity<CoreaErrorResponse> handleCoreaException(CoreaException e) {
        log.debug("Corea Custom exception [statusCode = {}, errorMessage = {}, cause = {}]", e.getHttpStatus(), e.getMessage(), e.getStackTrace());
        ResponseEntity.BodyBuilder status = ResponseEntity.status(e.getHttpStatus());
        if (e.isExceptionType(TOKEN_EXPIRED)) {
            ResponseCookie expiredRefreshCookie = cookieProvider.createExpiredCookie(REFRESH_COOKIE);
            status.header(SET_COOKIE, expiredRefreshCookie.toString());
        }
        return status.body(CoreaErrorResponse.from(e));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResultException(NoResourceFoundException e) {
        return ResponseEntity.notFound()
                .build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("Server exception [errorMessage = {}, cause = {},error ={}]", e.getMessage(), e.getCause(), e.getStackTrace());
        return ResponseEntity.internalServerError()
                .body(new ErrorResponse(SERVER_ERROR_MESSAGE));
    }
}
