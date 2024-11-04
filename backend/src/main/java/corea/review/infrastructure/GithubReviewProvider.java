package corea.review.infrastructure;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.review.dto.GithubPullRequestReview;
import corea.review.dto.GithubPullRequestReviewInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static corea.global.util.FutureUtil.supplyAsync;

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

        return getGithubPullRequestReviewInfo(prLink);
    }

    private GithubPullRequestReviewInfo getGithubPullRequestReviewInfo(String prLink) {
        CompletableFuture<List<GithubPullRequestReview>> reviewFuture = supplyAsync(() -> reviewClient.getPullRequestReviews(prLink));
        CompletableFuture<List<GithubPullRequestReview>> commentFuture = supplyAsync(() -> commentClient.getPullRequestReviews(prLink));

        return reviewFuture
                .thenCombine(commentFuture, this::collectPullRequestReviews)
                .exceptionally(e -> {throw new CoreaException(ExceptionType.GITHUB_SERVER_ERROR);})
                .thenApply(GithubPullRequestReviewInfo::new)
                .join();
    }

    private Map<String, GithubPullRequestReview> collectPullRequestReviews(List<GithubPullRequestReview> reviews, List<GithubPullRequestReview> comments) {
        return collectByGithubUserId(Stream.concat(reviews.stream(), comments.stream()));
    }

    private Map<String, GithubPullRequestReview> collectByGithubUserId(Stream<GithubPullRequestReview> reviews) {
        return reviews.collect(Collectors.toMap(
                GithubPullRequestReview::getGithubUserId,
                Function.identity(),
                (x, y) -> x
        ));
    }

    private void validatePrLink(String prUrl) {
        if (isNotStartsWithHttps(prUrl) || isInvalidGithubPrUrl(extractPrLinkParts(prUrl))) {
            throw new CoreaException(ExceptionType.INVALID_PULL_REQUEST_URL);
        }
    }

    private boolean isNotStartsWithHttps(String prUrl) {
        return prUrl == null || !prUrl.startsWith(HTTP_SECURE_PREFIX);
    }

    private List<String> extractPrLinkParts(String prUrl) {
        String prLink = prUrl.replaceFirst(HTTP_SECURE_PREFIX, "");
        return List.of(prLink.split(URL_DELIMITER));
    }

    private boolean isInvalidGithubPrUrl(List<String> prLinkParts) {
        return prLinkParts.size() != VALID_URL_SPLIT_COUNT ||
                !prLinkParts.get(DOMAIN_PREFIX_INDEX)
                        .contains(GITHUB_PREFIX) ||
                !prLinkParts.get(GITHUB_PULL_REQUEST_URL_INDEX)
                        .equals(GITHUB_PULL_REQUEST_DOMAIN);
    }
}
