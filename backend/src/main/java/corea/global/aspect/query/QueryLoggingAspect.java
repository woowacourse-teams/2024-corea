package corea.global.aspect.query;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Aspect
@RequiredArgsConstructor
@Component
@Profile({"local","dev"})
public class QueryLoggingAspect {

    private static final Logger log = LogManager.getLogger(QueryLoggingAspect.class);
    private final QueryInfo queryInfo;

    @Pointcut("execution(* corea..*Controller.*(..))")
    public void controllerMethods() {}

    @Around("controllerMethods()")
    public Object logSqlStatements(ProceedingJoinPoint joinPoint) throws Throwable {
        queryInfo.clear();
        Object result = joinPoint.proceed();

        String className = joinPoint.getSignature()
                .getDeclaringTypeName();
        String methodName = joinPoint.getSignature()
                .getName();
        String logs = queryInfo.toFormatString();

        if (queryInfo.isExceedQuery()) {
            log.warn("{}.{} exceeded query limit(count:{}): \n{}", className, methodName, queryInfo.getCount(), logs);
        } else {
            log.debug("{}.{} executed with queries: \n{}", className, methodName, logs);
        }
        return result;
    }
}
