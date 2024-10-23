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
public class GithubCommentClient {

    private final RestClient restClient;
    private final GithubPullRequestUrlExchanger githubPullRequestUrlExchanger;

    public List<GithubPullRequestReview> getPullRequestComments(String prLink) {
        String commentApiUrl = githubPullRequestUrlExchanger.pullRequestUrlToComment(prLink);

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
}
