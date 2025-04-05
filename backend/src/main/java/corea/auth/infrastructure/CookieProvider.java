package corea.auth.infrastructure;

import jakarta.servlet.http.Cookie;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class CookieProvider {

    private static final String EXPIRED_COOKIE_VALUE = "";
    private static final long EXPIRED_COOKIE_AGE = 0;

    public ResponseCookie createCookie(String name, String value, long maxAge) {
        return ResponseCookie.from(name, value)
                .httpOnly(true)
                .secure(true)
                .sameSite("Lax")
                .path("/")
                .maxAge(maxAge)
                .build();
    }

    public ResponseCookie createExpiredCookie(String name) {
        return createCookie(name, EXPIRED_COOKIE_VALUE, EXPIRED_COOKIE_AGE);
    }

    public Optional<String> getCookieValue(Cookie[] cookies, String name) {
        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(name))
                .map(Cookie::getValue)
                .findFirst();
    }
}
