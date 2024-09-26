package corea.global.jpa;

import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

import javax.sql.DataSource;
import java.util.HashMap;

@Slf4j
@Configuration
@RequiredArgsConstructor
@Profile("prod")
@EnableJpaRepositories(basePackages = "corea")
public class DataSourceConfig {

    // Write replica 정보로 만든 DataSource
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.write")
    public DataSource writeDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    // Read replica 정보로 만든 DataSource
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.read")
    public DataSource readDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    // 읽기 모드인지 여부로 DataSource를 분기 처리
    @Bean
    @DependsOn({"writeDataSource", "readDataSource"})
    public DataSource routeDataSource(DataSource writeDataSource, DataSource readDataSource) {
        DataSourceRouter dataSourceRouter = new DataSourceRouter();
        HashMap<Object, Object> dataSourceMap = new HashMap<>();

        log.info("Write Database : {}",writeDataSource);
        log.info("Read Database : {}",readDataSource);

        dataSourceMap.put(DataSourceConstant.WRITE, writeDataSource);
        dataSourceMap.put(DataSourceConstant.READ, readDataSource);

        dataSourceRouter.setTargetDataSources(dataSourceMap);
        dataSourceRouter.setDefaultTargetDataSource(writeDataSource);
        return dataSourceRouter;
    }

    @Bean
    @Primary
    @DependsOn({"routeDataSource"})
    public DataSource dataSource(DataSource routeDataSource) {
        return new LazyConnectionDataSourceProxy(routeDataSource);
    }
}
