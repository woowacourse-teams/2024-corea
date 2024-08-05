package corea.auth.infrastructure;

import corea.auth.domain.GithubUserInfo;
import corea.exception.CoreaException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import static corea.exception.ExceptionType.GITHUB_AUTHORIZATION_ERROR;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@EnableConfigurationProperties(GithubProperties.class)
@Component
@RequiredArgsConstructor
public class GithubClient {

    private final RestClient restClient;
    private final GithubProperties githubProperties;

    public String getAccessToken(String code) {
        String result = getAccess(code);
        validate(result);
        return result;
    }

    private String getAccess(String code) {
        return restClient.post()
                .uri(uriBuilder -> uriBuilder.path(githubProperties.baseUrl().oauth())
                        .queryParam("client_id", githubProperties.oauth().clientId())
                        .queryParam("client_secret", githubProperties.oauth().clientSecret())
                        .queryParam("code", code)
                        .build())
                .contentType(APPLICATION_JSON)
                .retrieve()
                .body(String.class);
    }

    private void validate(String result) {
        if (result == null || result.isBlank() || result.contains("\"error\"")) {
            throw new CoreaException(GITHUB_AUTHORIZATION_ERROR);
        }
    }

    public GithubUserInfo getUserInfo(String accessToken) {
        return restClient.get()
                .uri(githubProperties.baseUrl().user())
                .accept(APPLICATION_JSON)
                .header("Authorization", accessToken)
                .retrieve()
                .toEntity(GithubUserInfo.class)
                .getBody();
    }
}
