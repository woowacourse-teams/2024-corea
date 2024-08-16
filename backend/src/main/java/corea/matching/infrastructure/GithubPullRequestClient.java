package corea.matching.infrastructure;

import corea.auth.infrastructure.GithubProperties;
import corea.matching.infrastructure.dto.PullRequestData;
import corea.matching.infrastructure.dto.PullRequestResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@EnableConfigurationProperties(GithubProperties.class)
@Component
@RequiredArgsConstructor
public class GithubPullRequestClient {

    private static final String GITHUB_URL = "https://github.com/";
    private static final String GITHUB_REPO_API_URL = "https://api.github.com/repos/";
    private static final String PULLS_LINK = "/pulls";
    private static final String PER_PAGE_QUERY = "&per_page=";
    private static final String PAGE = "&page=";
    private static final String STATE_OPEN = "?state=open";
    private static final String DIRECTION = "&direction=asc";

    private static final Logger log = LogManager.getLogger(GithubPullRequestClient.class);
    private final RestClient restClient;

    public PullRequestData getPullRequestListWithPageNumber(String repositoryLink, int perPageSize, int pageNumber) {
        String requestLink = constructApiLink(repositoryLink, perPageSize, pageNumber);
        log.debug("요청 링크:{}", requestLink);
        PullRequestResponse[] response = restClient.get()
                .uri(requestLink)
                .accept(APPLICATION_JSON)
                .retrieve()
                .body(PullRequestResponse[].class);
        log.debug("개수:{}, 응답 데이터:{}", response.length, response);
        return new PullRequestData(isLastPage(response), response);
    }

    private boolean isLastPage(PullRequestResponse[] responses) {
        return responses.length == 0;
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
}
