package corea.matching.infrastructure;

import corea.auth.infrastructure.GithubProperties;
import corea.matching.infrastructure.dto.PullRequestData;
import corea.matching.infrastructure.dto.PullRequestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@EnableConfigurationProperties(GithubProperties.class)
@Component
@RequiredArgsConstructor
public class GithubPullRequestClient {

    private static final String PULLS_LINK = "/pulls";
    private static final String PER_PAGE_QUERY = "per_page=";
    private static final String PAGE = "page=";
    private static final String DIRECTION = "direction=asc";
    private final RestClient restClient;

    public PullRequestData getPullRequestListWithPageNumber(String repositoryLink, int perPageSize, int pageNumber) {
        PullRequestResponse[] response = restClient.get()
                .uri(repositoryLink + PULLS_LINK + "?" + PER_PAGE_QUERY + perPageSize + "&" + PAGE + pageNumber + "&"+DIRECTION)
                .accept(APPLICATION_JSON)
                .retrieve()
                .body(PullRequestResponse[].class);

        return new PullRequestData(isLastPage(response), response);
    }

    private boolean isLastPage(PullRequestResponse[] responses) {
        return responses.length == 0;
    }
}
