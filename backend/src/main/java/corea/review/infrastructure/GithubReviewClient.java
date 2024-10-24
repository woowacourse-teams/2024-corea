package corea.review.infrastructure;

import corea.auth.infrastructure.GithubPersonalAccessTokenProvider;
import corea.review.dto.GithubPullRequestReview;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
@RequiredArgsConstructor
public abstract class GithubReviewClient {

    private static final String PAGE_PARAM = "?page=";
    private static final String PER_PAGE_PARAM = "&per_page=";
    private static final int MAX_PER_PAGE = 100;

    private final RestClient restClient;
    private final GithubPullRequestUrlExchanger githubPullRequestUrlExchanger;
    private final GithubPersonalAccessTokenProvider githubPersonalAccessTokenProvider;

    abstract protected String prLinkToGithubApiUrl(String prLink);

    public List<GithubPullRequestReview> getPullRequestReviews(String prLink) {
        String githubApiUrl = prLinkToGithubApiUrl(prLink);

        return Stream.iterate(1, page -> page + 1)
                .map(page -> getPullRequestReviewsForPage(page, githubApiUrl))
                .takeWhile(this::hasMoreReviews)
                .flatMap(Arrays::stream)
                .toList();
    }

    private GithubPullRequestReview[] getPullRequestReviewsForPage(int page, String githubApiUrl) {
        String url = buildPageUrl(page, githubApiUrl);

        return restClient.get()
                .uri(url)
                .header(HttpHeaders.AUTHORIZATION, githubPersonalAccessTokenProvider.getRandomPersonalAccessToken())
                .accept(APPLICATION_JSON)
                .retrieve()
                .body(GithubPullRequestReview[].class);
    }

    private String buildPageUrl(int page, String commentApiUrl) {
        return commentApiUrl + PAGE_PARAM + page + PER_PAGE_PARAM + MAX_PER_PAGE;
    }

    private boolean hasMoreReviews(GithubPullRequestReview[] comments) {
        return comments.length > 0;
    }

    protected GithubPullRequestUrlExchanger getGithubPullRequestUrlExchanger() {
        return githubPullRequestUrlExchanger;
    }
}
