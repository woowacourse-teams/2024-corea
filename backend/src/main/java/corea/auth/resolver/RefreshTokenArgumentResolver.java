package corea.auth.resolver;

import corea.auth.annotation.RefreshToken;
import corea.auth.dto.TokenRefreshRequest;
import corea.auth.infrastructure.CookieProvider;
import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static corea.global.config.Constants.REFRESH_COOKIE;

@Component
@RequiredArgsConstructor
public class RefreshTokenArgumentResolver implements HandlerMethodArgumentResolver {

    private final CookieProvider cookieProvider;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RefreshToken.class);
    }

    @Override
    public TokenRefreshRequest resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            throw new CoreaException(ExceptionType.COOKIE_NOT_EXIST);
        }
        return new TokenRefreshRequest(cookieProvider.getCookieValue(cookies, REFRESH_COOKIE)
                .orElseThrow(() -> new CoreaException(ExceptionType.COOKIE_NOT_EXIST)));
    }
}
