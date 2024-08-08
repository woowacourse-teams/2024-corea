package corea.global.config;

import corea.global.customizer.ErrorResponseCustomizer;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.*;
import io.swagger.v3.oas.models.OpenAPI;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "CoReA OpenAPI 문서",
                description = "작성한 명세를 기반으로 문서화 되었습니다.",
                version = "v1"
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
