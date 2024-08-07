package corea.auth.infrastructure;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "security.github")
public record GithubProperties(BaseUrl baseUrl, OAuth oauth) {

    public record BaseUrl(String oauth, String user) {
    }

    public record OAuth(String clientId, String clientSecret) {
    }
}
