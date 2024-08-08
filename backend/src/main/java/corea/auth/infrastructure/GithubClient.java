package corea.auth.infrastructure;

import corea.auth.dto.GithubAuthRequest;
import corea.auth.dto.GithubAuthResponse;
import corea.auth.dto.GithubUserInfo;
import corea.exception.CoreaException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import static corea.exception.ExceptionType.GITHUB_AUTHORIZATION_ERROR;
import static corea.global.config.Constants.AUTHORIZATION_HEADER;
import static corea.global.config.Constants.TOKEN_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@EnableConfigurationProperties(GithubProperties.class)
@Component
@RequiredArgsConstructor
public class GithubClient {

    private static final String ERROR = "\"error\"";

    private final RestClient restClient;
    private final GithubProperties githubProperties;

    public String getAccessToken(String code) {
        String result = getAccess(code);
        validate(result);
        return result;
    }

    private String getAccess(String code) {
        return restClient.post()
                .uri(githubProperties.baseUrl().oauth())
                .contentType(APPLICATION_JSON)
                .body(new GithubAuthRequest(
                        githubProperties.oauth().clientId(),
                        githubProperties.oauth().clientSecret(),
                        code))
                .accept(APPLICATION_JSON)
                .retrieve()
                .body(GithubAuthResponse.class)
                .accessToken();
    }

    private void validate(String result) {
        if (result == null || result.isBlank() || result.contains(ERROR)) {
            throw new CoreaException(GITHUB_AUTHORIZATION_ERROR);
        }
    }

    public GithubUserInfo getUserInfo(String accessToken) {
        return restClient.get()
                .uri(githubProperties.baseUrl().user())
                .accept(APPLICATION_JSON)
                .header(AUTHORIZATION_HEADER, TOKEN_TYPE.concat(accessToken))
                .retrieve()
                .body(GithubUserInfo.class);
    }
}
