package corea.auth.infrastructure;

import corea.global.config.Constants;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@EnableConfigurationProperties(GithubProperties.class)
@Component
public class GithubPersonalAccessTokenProvider {

    private static final Random RANDOM = new Random();

    private final List<String> personalAccessTokens;

    public GithubPersonalAccessTokenProvider(GithubProperties githubProperties) {
        this.personalAccessTokens = githubProperties.pullRequest()
                .tokens();
    }

    public String getRandomPersonalAccessToken() {
        if (personalAccessTokens.isEmpty()) {
            return "";
        }
        return Constants.TOKEN_TYPE + personalAccessTokens.get(getRandomIndex());
    }

    private int getRandomIndex() {
        return RANDOM.nextInt(personalAccessTokens.size());
    }
}
