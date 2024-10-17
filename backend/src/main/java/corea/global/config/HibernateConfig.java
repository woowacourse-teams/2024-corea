package corea.global.config;

import corea.global.aspect.query.QueryInspector;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static org.hibernate.cfg.JdbcSettings.STATEMENT_INSPECTOR;

@Configuration
@RequiredArgsConstructor
@Profile({"local", "dev"})
public class HibernateConfig {

    private final QueryInspector queryInspector;

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertyConfig() {
        return hibernateProperties ->
                hibernateProperties.put(STATEMENT_INSPECTOR, queryInspector);
    }
}
