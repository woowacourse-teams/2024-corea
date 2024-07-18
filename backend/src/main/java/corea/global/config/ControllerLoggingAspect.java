package corea.global.config;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

@Aspect
@Component
@Profile("!prod")
public final class ControllerLoggingAspect {

    @Pointcut("""
            @annotation(org.springframework.web.bind.annotation.RequestMapping)
             || @annotation(org.springframework.web.bind.annotation.GetMapping)
             || @annotation(org.springframework.web.bind.annotation.PostMapping)
             || @annotation(org.springframework.web.bind.annotation.PatchMapping)
             || @annotation(org.springframework.web.bind.annotation.PutMapping)
             || @annotation(org.springframework.web.bind.annotation.DeleteMapping)
            """)
    void loggingPointcut() {
    }
    private Optional<HttpServletRequest> getRequest() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .filter(ServletRequestAttributes.class::isInstance)
                .map(ServletRequestAttributes.class::cast)
                .map(ServletRequestAttributes::getRequest);
    }

    @Around("loggingPointcut()")
    Object logAround(final ProceedingJoinPoint joinPoint) throws Throwable {
        final var log = getLog(joinPoint);
        loggingEnter(joinPoint, log);
        return loggingReturn(joinPoint, log);
    }

    private Object loggingReturn(
            final ProceedingJoinPoint joinPoint,
            final Logger log
    ) throws Throwable {
        final var startMillis = System.currentTimeMillis();
        final var result = joinPoint.proceed();
        final var elapsedMillis = System.currentTimeMillis() - startMillis;
        final Consumer<HttpServletRequest> httpServletRequestLogger = request -> log.debug(
                "return [time={}, url={}, httpMethod={}, ip={}, class={}, method={}, result={}, elapsedMillis={}]",
                LocalDateTime.now(),
                request.getRequestURL(),
                request.getMethod(),
                IpExtractor.extract(request),
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                result,
                elapsedMillis
        );
        final Runnable requestNotFoundLogger = () -> log.debug(
                "return [time={}, class={}, method={}, result={}, elapsedMillis={}]",
                LocalDateTime.now(),
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                result,
                elapsedMillis
        );
        getRequest().ifPresentOrElse(httpServletRequestLogger, requestNotFoundLogger);

        return result;
    }

    private void loggingEnter(final ProceedingJoinPoint joinPoint, final Logger log) {
        final Consumer<HttpServletRequest> httpServletRequestLogger = request -> log.debug(
                "enter [time={}, url={}, httpMethod={}, ip={}, class={}, method={}, hashcode={}, arguments={}]",
                LocalDateTime.now(),
                request.getRequestURL(),
                request.getMethod(),
                IpExtractor.extract(request),
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                Thread.currentThread().hashCode(),
                Arrays.toString(joinPoint.getArgs())
        );
        final Runnable requestNotFoundLogger = () -> log.warn(
                "enter [time={}, class={}, method={}, hashcode={}, arguments={}]",
                LocalDateTime.now(),
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                Thread.currentThread().hashCode(),
                Arrays.toString(joinPoint.getArgs())
        );
        getRequest().ifPresentOrElse(httpServletRequestLogger, requestNotFoundLogger);
    }

    private Logger getLog(final JoinPoint joinPoint) {
        return LoggerFactory.getLogger(joinPoint.getTarget()
                .getClass());
    }

    private enum IpExtractor {

        X_FORWARDED_FOR("X-Forwarded-For"),
        PROXY_CLIENT_IP("Proxy-Client-IP"),
        WL_PROXY_CLIENT_IP("WL-Proxy-Client-IP"),
        HTTP_CLIENT_IP("HTTP_CLIENT_IP"),
        HTTP_X_FORWARDED_FOR("HTTP_X_FORWARDED_FOR"),
        X_REAL_IP("X-Real-IP"),
        @SuppressWarnings("SpellCheckingInspection") X_REALIP("X-RealIP"),
        REMOTE_ADDR("REMOTE_ADDR"),
        REMOTE_ADDR_BUILD_IN(HttpServletRequest::getRemoteAddr),

        ;

        private static final String UNKNOWN = "unknown";

        private final Function<HttpServletRequest, String> mapper;

        IpExtractor(final String headerName) {
            this(request -> request.getHeader(headerName));
        }

        IpExtractor(final Function<HttpServletRequest, String> mapper) {
            this.mapper = mapper;
        }

        private static String extract(final HttpServletRequest request) {
            return Arrays.stream(values())
                    .map(it -> it.mapper.apply(request))
                    .filter(IpExtractor::isValidIp)
                    .findAny()
                    .orElse(UNKNOWN);
        }

        private static boolean isValidIp(final String ip) {
            return StringUtils.hasText(ip) && !UNKNOWN.equalsIgnoreCase(ip);
        }
    }
}
