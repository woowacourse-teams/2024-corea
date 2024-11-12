package corea.matching.infrastructure;

import corea.auth.infrastructure.GithubPersonalAccessTokenProvider;
import corea.matching.infrastructure.dto.PullRequestResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Slf4j
@Component
@RequiredArgsConstructor
public class PrivateGithubPullRequestClient {

    private final RestClient restClient;
    private final GithubPersonalAccessTokenProvider githubPersonalAccessTokenProvider;

    public PullRequestResponse getPullRequest(String repositoryLink, String username) {
        String apiLink = convertApiLink(repositoryLink, username);
        log.debug("요청 링크:{}", repositoryLink);
        return restClient.get()
                .uri(apiLink)
                .header(HttpHeaders.AUTHORIZATION, githubPersonalAccessTokenProvider.getRandomPersonalAccessToken())
                .accept(APPLICATION_JSON)
                .exchange((clientRequest, clientResponse) -> {
                    if (clientResponse.getStatusCode().is4xxClientError()) {
                        return null;
                    }
                    PullRequestResponse[] data = clientResponse.bodyTo(PullRequestResponse[].class);
                    return data.length > 0 ? data[0] : null;
                });
    }

    private String convertApiLink(String repositoryLink, String username) {
        String[] parts = repositoryLink.substring(8)
                .split("/");
        String repoName = parts[2];
        return String.format("https://api.github.com/repos/%s/%s-%s/pulls", username, repoName, username);
    }
}
