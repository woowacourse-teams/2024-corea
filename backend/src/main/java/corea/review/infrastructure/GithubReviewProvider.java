package corea.review.infrastructure;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.review.dto.GithubPullRequestReview;
import corea.review.dto.GithubPullRequestReviewInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class GithubReviewProvider {

    private static final String HTTP_SECURE_PREFIX = "https://";
    private static final String URL_DELIMITER = "/";
    private static final String GITHUB_PREFIX = "github.com";
    private static final String GITHUB_PULL_REQUEST_DOMAIN = "pull";
    private static final int DOMAIN_PREFIX_INDEX = 0;
    private static final int GITHUB_PULL_REQUEST_URL_INDEX = 3;
    private static final int VALID_URL_SPLIT_COUNT = 5;

    private final GithubPullRequestReviewClient reviewClient;
    private final GithubPullRequestCommentClient commentClient;

    public GithubPullRequestReviewInfo provideReviewInfo(String prLink) {
        validatePrLink(prLink);
        List<GithubPullRequestReview> reviews = reviewClient.getPullRequestReviews(prLink);
        List<GithubPullRequestReview> comments = commentClient.getPullRequestReviews(prLink);

        Map<String, GithubPullRequestReview> result = collectPullRequestReviews(reviews, comments);
        return new GithubPullRequestReviewInfo(result);
    }

    private void validatePrLink(String prUrl) {
        if (prUrl == null || !prUrl.startsWith(HTTP_SECURE_PREFIX)) {
            throw new CoreaException(ExceptionType.INVALID_PULL_REQUEST_URL);
        }
        String prLink = prUrl.replaceFirst(HTTP_SECURE_PREFIX, "");
        List<String> splitPrLink = List.of(prLink.split(URL_DELIMITER));
        if (splitPrLink.size() != VALID_URL_SPLIT_COUNT
                || !splitPrLink.get(DOMAIN_PREFIX_INDEX).contains(GITHUB_PREFIX)
                || !splitPrLink.get(GITHUB_PULL_REQUEST_URL_INDEX).equals(GITHUB_PULL_REQUEST_DOMAIN)) {
            throw new CoreaException(ExceptionType.INVALID_PULL_REQUEST_URL);
        }
    }

    private Map<String, GithubPullRequestReview> collectPullRequestReviews(List<GithubPullRequestReview> reviews, List<GithubPullRequestReview> comments) {
        return Stream.concat(
                        reviews.stream(),
                        comments.stream()
                )
                .collect(Collectors.toMap(
                        GithubPullRequestReview::getGithubUserId,
                        Function.identity(),
                        (x, y) -> x
                ));
    }
}
