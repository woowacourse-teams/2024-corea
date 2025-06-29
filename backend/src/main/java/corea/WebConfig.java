package corea;

import corea.auth.interceptor.AuthorizationInterceptor;
import corea.auth.resolver.AccessedMemberArgumentResolver;
import corea.auth.resolver.LoginMemberArgumentResolver;
import corea.auth.resolver.RefreshTokenArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

import static corea.global.config.Constants.AUTHORIZATION_HEADER;
import static org.springframework.http.HttpHeaders.SET_COOKIE;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final LoginMemberArgumentResolver loginMemberArgumentResolver;
    private final AccessedMemberArgumentResolver accessedMemberArgumentResolver;
    private final AuthorizationInterceptor authorizationInterceptor;
    private final RefreshTokenArgumentResolver refreshTokenArgumentResolver;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000", "https://d2n5lw9a9hwjzs.cloudfront.net/",
                        "https://d114zsd8hvayia.cloudfront.net/", "https://vvv.code-review-area.com/",
                        "https://code-review-area.com/", "https://www.code-review-area.com/",
                        "https://dev.code-review-area.com/")
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                .allowCredentials(true)
                .exposedHeaders(AUTHORIZATION_HEADER, SET_COOKIE)
                .maxAge(3000);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginMemberArgumentResolver);
        resolvers.add(accessedMemberArgumentResolver);
        resolvers.add(refreshTokenArgumentResolver);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor)
                .addPathPatterns("/**");
    }
}
