package corea.auth;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import static corea.global.config.Constants.*;

@Component
public class RequestHandler {

    public String extract(HttpServletRequest request) {
        if (request.getHeader(AUTHORIZATION_HEADER) != null && request.getHeader(AUTHORIZATION_HEADER).startsWith(TOKEN_TYPE)) {
            return request.getHeader(AUTHORIZATION_HEADER).replace(TOKEN_TYPE, "");
        }
        return ANONYMOUS;
    }
}
