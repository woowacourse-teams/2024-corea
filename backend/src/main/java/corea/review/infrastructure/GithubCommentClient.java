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
        String commentUrl = githubPullRequestUrlExchanger.pullRequestUrlToComment(prLink);

        return Stream.iterate(1, page -> page + 1) // 페이지를 1부터 시작해서 증가
                .map(page -> getPullRequestCommentsForPage(page, commentUrl))
                .takeWhile(this::hasMoreComments)
                .flatMap(Arrays::stream)
                .toList();
    }

    private GithubPullRequestReview[] getPullRequestCommentsForPage(Integer page, String commentUrl) {
        String url = buildPageUrl(page, commentUrl);

        return restClient.get()
                .uri(url)
                .accept(APPLICATION_JSON)
                .retrieve()
                .body(GithubPullRequestReview[].class);
    }

    private String buildPageUrl(Integer page, String commentUrl) {
        return commentUrl + "?page=" + page + "&per_page=100";
    }

    private boolean hasMoreComments(GithubPullRequestReview[] comments) {
        return comments.length > 0;
    }
}
