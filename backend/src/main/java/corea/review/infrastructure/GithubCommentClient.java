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
public class GithubCommentClient {

    private static final Random RANDOM = new Random();

    private final RestClient restClient;
    private final GithubPullRequestUrlExchanger githubPullRequestUrlExchanger;
    private final List<String> personalAccessTokens;

    public GithubCommentClient(RestClient restClient, GithubPullRequestUrlExchanger githubPullRequestUrlExchanger, GithubProperties githubProperties) {
        this.restClient = restClient;
        this.githubPullRequestUrlExchanger = githubPullRequestUrlExchanger;
        this.personalAccessTokens = githubProperties.pullRequest()
                .tokens();
    }

    public List<GithubPullRequestReview> getPullRequestComments(String prLink) {
        String commentApiUrl = githubPullRequestUrlExchanger.prLinkToCommentApiUrl(prLink);

        return Stream.iterate(1, page -> page + 1)
                .map(page -> getPullRequestCommentsForPage(page, commentApiUrl))
                .takeWhile(this::hasMoreComments)
                .flatMap(Arrays::stream)
                .toList();
    }

    private GithubPullRequestReview[] getPullRequestCommentsForPage(int page, String commentApiUrl) {
        String url = buildPageUrl(page, commentApiUrl);

        return restClient.get()
                .uri(url)
                .header(HttpHeaders.AUTHORIZATION, getRandomPersonalAccessToken())
                .accept(APPLICATION_JSON)
                .retrieve()
                .body(GithubPullRequestReview[].class);
    }

    private String buildPageUrl(int page, String commentApiUrl) {
        return commentApiUrl + "?page=" + page + "&per_page=100";
    }

    private boolean hasMoreComments(GithubPullRequestReview[] comments) {
        return comments.length > 0;
    }

    private String getRandomPersonalAccessToken() {
        if (personalAccessTokens.isEmpty()) {
            return "";
        }
        return "Bearer " + personalAccessTokens.get(RANDOM.nextInt(personalAccessTokens.size()));
    }
}
