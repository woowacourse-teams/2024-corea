package corea.auth.infrastructure;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;

import java.util.List;
import java.util.stream.IntStream;

public class GithubPullRequestUrlExchanger {

    private static final String REVIEW_API_PREFIX = "api.github.com/repos";
    private static final String GITHUB_PREFIX = "github.com";
    private static final String HTTP_SECURE_PREFIX = "https://";
    private static final String URL_DELIMITER = "/";
    private static final String GITHUB_PULL_REQUEST_REVIEW_API_SUFFIX = "reviews";
    private static final String GITHUB_PULL_REQUEST_DOMAIN = "pull";
    private static final String GITHUB_PULL_REQUEST_API_DOMAIN = "pulls";
    private static final int DOMAIN_PREFIX_INDEX = 0;
    private static final int GITHUB_PULL_REQUEST_URL_INDEX = 3;
    private static final int VALID_URL_SPLIT_COUNT = 5;

    private GithubPullRequestUrlExchanger() {
    }

    public static String pullRequestUrlToReview(String prLink) {
        validatePrLink(prLink);
        return prLinkToReviewApiLink(prLink);
    }

    private static void validatePrLink(String prUrl) {
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

    private static String prLinkToReviewApiLink(String prLink) {
        StringBuilder builder = new StringBuilder();
        builder.append(HTTP_SECURE_PREFIX);
        builder.append(REVIEW_API_PREFIX);
        String[] splitPrLink = prLink.replaceFirst(HTTP_SECURE_PREFIX + GITHUB_PREFIX, "").split(URL_DELIMITER);
        List<String> apiUrlComponents = IntStream.range(0, splitPrLink.length)
                .mapToObj(i -> filterPullUrlToApiUrl(splitPrLink, i))
                .toList();
        builder.append(String.join(URL_DELIMITER, apiUrlComponents));
        builder.append(URL_DELIMITER + GITHUB_PULL_REQUEST_REVIEW_API_SUFFIX);
        return builder.toString();
    }

    private static String filterPullUrlToApiUrl(String[] splitPrLink, int index) {
        if (index != GITHUB_PULL_REQUEST_URL_INDEX) {
            return splitPrLink[index];
        }
        return GITHUB_PULL_REQUEST_API_DOMAIN;
    }
}
