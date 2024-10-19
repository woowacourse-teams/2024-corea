package corea.review.infrastructure;

import corea.auth.infrastructure.GithubProperties;
import corea.auth.infrastructure.GithubPullRequestUrlExchanger;
import corea.review.dto.GithubPullRequestReview;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@EnableConfigurationProperties(GithubProperties.class)
@Component
@RequiredArgsConstructor
public class GithubReviewClient {

    private final RestClient restClient;

    public GithubPullRequestReview[] getReviewLink(String prLink) {
        String url = GithubPullRequestUrlExchanger.pullRequestUrlToReview(prLink);
        return restClient.get()
                .uri(url)
                .accept(APPLICATION_JSON)
                .retrieve()
                .body(GithubPullRequestReview[].class);
    }
}
