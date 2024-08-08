package corea.global.config;

import corea.global.customizer.ErrorResponseCustomizer;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.OAuthScope;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "CoReA OpenAPI 문서",
                description = "기존 명세와 코드 기반으로 API 목록을 문서화한 페이지입니다. <br><br> 최종 수정일 : 2024-08-08",
                version = "v2"
        )
)
@SecurityScheme(
        name = "OAuth2.0",
        type = SecuritySchemeType.OAUTH2,
        flows = @OAuthFlows(
                authorizationCode = @OAuthFlow(
                        authorizationUrl = "https://github.com/login/oauth/authorize",
                        tokenUrl = "https://github.com/login/oauth/access_token",
                        scopes = {
                                @OAuthScope(name = "Read User Email", description = "유저가 깃허브에 등록한 이메일 정보를 읽어옵니다."),
                        }
                )
        )
)
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI();
    }

    @Bean
    public GroupedOpenApi groupedOpenApi() {
        return GroupedOpenApi.builder()
                .group("CoReA API")
                .addOperationCustomizer(new ErrorResponseCustomizer())
                .build();
    }
}
