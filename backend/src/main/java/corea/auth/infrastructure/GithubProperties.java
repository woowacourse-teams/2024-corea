package corea.auth.infrastructure;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "security.github")
public record GithubProperties(BaseUrl baseUrl, OAuth oauth, PullRequest pullRequest) {

    public record BaseUrl(String oauth, String user) {
    }

    public record OAuth(String clientId, String clientSecret) {
    }

    public record PullRequest(List<String> tokens) {
    }
}
