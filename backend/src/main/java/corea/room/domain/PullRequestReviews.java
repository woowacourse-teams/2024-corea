package corea.room.domain;

import corea.auth.dto.GithubPullRequestReview;
import corea.exception.CoreaException;
import corea.exception.ExceptionType;

import java.util.Arrays;

public record PullRequestReviews(GithubPullRequestReview[] pullRequestReviews) {
    public String getReviewUrl(String githubUserId) {
        return Arrays.stream(pullRequestReviews)
                .filter(githubPullRequestReview -> githubPullRequestReview.isEqualGithubUserId(githubUserId))
                .findFirst()
                .map(GithubPullRequestReview::html_url)
                .orElseThrow(() -> new CoreaException(ExceptionType.NOT_EXIST_GITHUB_REVIEW));
    }
}
