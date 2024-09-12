package corea.global.aspect.query;

import org.hibernate.resource.jdbc.spi.StatementInspector;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Arrays;
import java.util.Objects;

@Component
@Profile({"local","dev"})
public class QueryInspector implements StatementInspector {

    private final transient QueryInfo queryInfo;
    private static final String FLAG_PROFILE = "inspect";
    private final boolean flag;

    public QueryInspector(final QueryInfo apiQueryCounter, final Environment environment) {
        this.queryInfo = apiQueryCounter;
        this.flag = Arrays.stream(environment.getActiveProfiles())
                .anyMatch(profile -> profile.equals(FLAG_PROFILE));
    }

    @Override
    public String inspect(final String sql) {
        if (isInRequestScope() || flag) {
            queryInfo.increaseQueryCount(sql);
        }
        return sql;
    }

    private boolean isInRequestScope() {
        return Objects.nonNull(RequestContextHolder.getRequestAttributes());
    }
}
