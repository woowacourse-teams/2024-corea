package corea.review.infrastructure;

import corea.auth.infrastructure.GithubProperties;
import corea.review.dto.GithubPullRequestReview;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@EnableConfigurationProperties(GithubProperties.class)
@Component
public class GithubReviewClient {

    private static final Random RANDOM = new Random();

    private final RestClient restClient;
    private final GithubPullRequestUrlExchanger githubPullRequestUrlExchanger;
    private final List<String> personalAccessTokens;

    public GithubReviewClient(RestClient restClient, GithubPullRequestUrlExchanger githubPullRequestUrlExchanger, GithubProperties githubProperties) {
        this.restClient = restClient;
        this.githubPullRequestUrlExchanger = githubPullRequestUrlExchanger;
        this.personalAccessTokens = githubProperties.pullRequest()
                .tokens();
    }

    public List<GithubPullRequestReview> getPullRequestReviews(String prLink) {
        String reviewApiUrl = githubPullRequestUrlExchanger.prLinkToReviewApiUrl(prLink);

        return Stream.iterate(1, page -> page + 1)
                .map(page -> getPullRequestReviewsForPage(page, reviewApiUrl))
                .takeWhile(this::hasMoreReviews)
                .flatMap(Arrays::stream)
                .toList();
    }

    private GithubPullRequestReview[] getPullRequestReviewsForPage(int page, String reviewApiUrl) {
        String url = buildPageUrl(page, reviewApiUrl);

        return restClient.get()
                .uri(url)
                .header(HttpHeaders.AUTHORIZATION, getRandomPersonalAccessToken())
                .accept(APPLICATION_JSON)
                .retrieve()
                .body(GithubPullRequestReview[].class);
    }

    private String buildPageUrl(int page, String reviewApiUrl) {
        return reviewApiUrl + "?page=" + page + "&per_page=100";
    }

    private boolean hasMoreReviews(GithubPullRequestReview[] reviews) {
        return reviews.length > 0;
    }

    private String getRandomPersonalAccessToken() {
        if (personalAccessTokens.isEmpty()) {
            return "";
        }
        return "Bearer " + personalAccessTokens.get(RANDOM.nextInt(personalAccessTokens.size()));
    }
}
