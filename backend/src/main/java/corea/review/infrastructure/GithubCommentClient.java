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

    public List<GithubPullRequestReview> getCommentLink(String prLink) {
        String commentUrl = GithubPullRequestUrlExchanger.pullRequestUrlToComment(prLink);

        return Stream.iterate(1, page -> page + 1) // 페이지를 1부터 시작해서 증가
                .map(page -> getGithubPullRequestReviews(page, commentUrl))
                .takeWhile(this::isLastPage)
                .flatMap(Arrays::stream)
                .toList();
    }

    private GithubPullRequestReview[] getGithubPullRequestReviews(Integer page, String commentUrl) {
        String url = convertToPageUrl(page, commentUrl);

        return restClient.get()
                .uri(url)
                .accept(APPLICATION_JSON)
                .retrieve()
                .body(GithubPullRequestReview[].class);
    }

    private String convertToPageUrl(Integer page, String commentUrl) {
        return commentUrl + "?page=" + page + "&per_page=100";
    }

    private boolean isLastPage(GithubPullRequestReview[] reviews) {
        return reviews.length > 0;
    }
}
