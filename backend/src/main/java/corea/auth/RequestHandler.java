package corea.auth;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class RequestHandler {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    public String extract(HttpServletRequest request) {
        return request.getHeader(AUTHORIZATION_HEADER);
    }
}
