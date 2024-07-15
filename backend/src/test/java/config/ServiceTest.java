package config;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Sql(value = {"/clear.sql"},executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public @interface ServiceTest {
}
