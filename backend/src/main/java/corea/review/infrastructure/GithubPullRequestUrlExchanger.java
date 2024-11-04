package corea.review.infrastructure;

import org.springframework.stereotype.Component;

@Component
public class GithubPullRequestUrlExchanger {

    private static final String GITHUB_URL = "https://github.com";
    private static final String GITHUB_API_URL = "https://api.github.com/repos";
    private static final String GITHUB_PULL_REQUEST_DOMAIN = "/pull/";
    private static final String GITHUB_PULL_REQUEST_REVIEW_DOMAIN = "/pulls/";
    private static final String GITHUB_PULL_REQUEST_COMMENT_DOMAIN = "/issues/";
    private static final String GITHUB_PULL_REQUEST_REVIEW_API_SUFFIX = "/reviews";
    private static final String GITHUB_PULL_REQUEST_COMMENT_API_SUFFIX = "/comments";

    public String prLinkToReviewApiUrl(String prLink) {
        String reviewApiUrl = convertToGithubApiUrl(prLink, GITHUB_PULL_REQUEST_REVIEW_DOMAIN);
        return reviewApiUrl + GITHUB_PULL_REQUEST_REVIEW_API_SUFFIX;
    }

    public String prLinkToCommentApiUrl(String prLink) {
        String commentApiUrl = convertToGithubApiUrl(prLink, GITHUB_PULL_REQUEST_COMMENT_DOMAIN);
        return commentApiUrl + GITHUB_PULL_REQUEST_COMMENT_API_SUFFIX;
    }

    private String convertToGithubApiUrl(String prLink, String pullRequestDomain) {
        String apiUrl = prLink.replaceFirst(GITHUB_URL, GITHUB_API_URL);
        return apiUrl.replace(GITHUB_PULL_REQUEST_DOMAIN, pullRequestDomain);
    }
}
