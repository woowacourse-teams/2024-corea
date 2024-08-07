package corea.auth.interceptor;

import corea.auth.RequestHandler;
import corea.auth.annotation.LoginMember;
import corea.auth.infrastructure.TokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

@Component
@RequiredArgsConstructor
public class AuthorizationInterceptor implements HandlerInterceptor {

    private static final Class<LoginMember> AUTH_ANNOTATION = LoginMember.class;

    private final TokenProvider tokenProvider;
    private final RequestHandler requestHandler;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (CorsUtils.isPreFlightRequest(request)) {
            return true;
        }

        boolean hasAnnotation = checkAnnotation(handler);
        if (hasAnnotation) {
            String accessToken = requestHandler.extract(request);
            tokenProvider.validateToken(accessToken);
        }
        return true;
    }

    private boolean checkAnnotation(Object handler) {
        if (handler instanceof ResourceHttpRequestHandler) {
            return false;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;

        return (null != handlerMethod.getMethodAnnotation(AUTH_ANNOTATION) ||
                null != handlerMethod.getBeanType().getAnnotation(AUTH_ANNOTATION));
    }
}
