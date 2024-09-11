package corea.matching.infrastructure;

import corea.auth.infrastructure.GithubProperties;
import corea.matching.infrastructure.dto.PullRequestData;
import corea.matching.infrastructure.dto.PullRequestResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Random;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@EnableConfigurationProperties(GithubProperties.class)
@Component
public class GithubPullRequestClient {

    private static final String GITHUB_URL = "https://github.com/";
    private static final String GITHUB_REPO_API_URL = "https://api.github.com/repos/";
    private static final String PULLS_LINK = "/pulls";
    private static final String PER_PAGE_QUERY = "&per_page=";
    private static final String PAGE = "&page=";
    private static final String STATE_OPEN = "?state=open";
    private static final String DIRECTION = "&direction=asc";
    private static final Random RANDOM = new Random();

    private static final Logger log = LogManager.getLogger(GithubPullRequestClient.class);

    private final RestClient restClient;
    private final List<String> personalAccessTokens;

    public GithubPullRequestClient(final RestClient restClient, final GithubProperties githubProperties) {
        this.restClient = restClient;
        this.personalAccessTokens = githubProperties.pullRequest()
                .tokens();
    }

    public PullRequestData getPullRequestListWithPageNumber(String repositoryLink, int perPageSize, int pageNumber) {
        String requestLink = constructApiLink(repositoryLink, perPageSize, pageNumber);
        log.debug("요청 링크:{}", requestLink);
        PullRequestResponse[] response = restClient.get()
                .uri(requestLink)
                .header(HttpHeaders.AUTHORIZATION, getRandomPersonalAccessToken())
                .accept(APPLICATION_JSON)
                .retrieve()
                .body(PullRequestResponse[].class);
        log.debug("개수:{}, 응답 데이터:{}", response.length, response);
        return new PullRequestData(response);
    }

    private String constructApiLink(String repositoryLink, int pageSize, int pageNumber) {
        return convertApiLink(repositoryLink) + PULLS_LINK + STATE_OPEN + PER_PAGE_QUERY + pageSize + PAGE + pageNumber + DIRECTION;
    }

    /**
     * 요청을 위해서 API 주소로 변환합니다.
     * <p>
     * github.com -> api.github.com/repos 로 변환합니다.
     *
     * @param repositoryLink
     * @return
     */
    private String convertApiLink(String repositoryLink) {
        return repositoryLink.replace(GITHUB_URL, GITHUB_REPO_API_URL);
    }

    private String getRandomPersonalAccessToken() {
        if (personalAccessTokens.isEmpty()) {
            return "";
        }
        return "Bearer " + personalAccessTokens.get(RANDOM.nextInt(personalAccessTokens.size()));
    }
}
