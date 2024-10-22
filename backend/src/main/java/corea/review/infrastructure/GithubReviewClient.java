package corea.review.infrastructure;

import corea.auth.infrastructure.GithubProperties;
import corea.review.dto.GithubPullRequestReview;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@EnableConfigurationProperties(GithubProperties.class)
@Component
@RequiredArgsConstructor
public class GithubReviewClient {

    private final RestClient restClient;
    private final GithubPullRequestUrlExchanger githubPullRequestUrlExchanger;

    public List<GithubPullRequestReview> getPullRequestReviews(String prLink) {
        String reviewUrl = githubPullRequestUrlExchanger.pullRequestUrlToReview(prLink);

        return Stream.iterate(1, page -> page + 1)
                .map(page -> getGithubPullRequestReviews(page, reviewUrl))
                .takeWhile(this::isNotLastPage)
                .flatMap(Arrays::stream)
                .toList();
    }

    private GithubPullRequestReview[] getGithubPullRequestReviews(Integer page, String reviewUrl) {
        String url = convertToPageUrl(page, reviewUrl);

        return restClient.get()
                .uri(url)
                .accept(APPLICATION_JSON)
                .retrieve()
                .body(GithubPullRequestReview[].class);
    }

    private String convertToPageUrl(Integer page, String reviewUrl) {
        return reviewUrl + "?page=" + page + "&per_page=100";
    }

    private boolean isNotLastPage(GithubPullRequestReview[] reviews) {
        return reviews.length > 0;
    }
}
