package config;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.web.server.LocalServerPort;

public abstract class BaseControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setupPort() {
        RestAssured.port = port;
    }
}
