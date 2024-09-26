package corea.global.jpa;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@Profile("prod")
public class DataSourceConfig {

    // Write replica 정보로 만든 DataSource
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.write")
    public DataSource writeDataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }

    // Read replica 정보로 만든 DataSource
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.read")
    public DataSource readDataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }

    // 읽기 모드인지 여부로 DataSource를 분기 처리
    @Bean
    @DependsOn({"writeDataSource", "readDataSource"})
    public DataSource routeDataSource(@Qualifier("writeDataSource") DataSource writeDataSource,
                                      @Qualifier("readDataSource") DataSource readDataSource) {
        DataSourceRouter dataSourceRouter = new DataSourceRouter();
        HashMap<Object, Object> dataSourceMap = new HashMap<>();

        dataSourceMap.put(DataSourceConstant.WRITE, writeDataSource);
        dataSourceMap.put(DataSourceConstant.READ, readDataSource);

        dataSourceRouter.setTargetDataSources(dataSourceMap);
        dataSourceRouter.setDefaultTargetDataSource(writeDataSource);
        return dataSourceRouter;
    }

    @DependsOn({"routeDataSource"})
    @Primary
    @Bean
    public DataSource dataSource(DataSource routeDataSource) {
        return new LazyConnectionDataSourceProxy(routeDataSource);
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
