package corea.fixture;

import corea.auth.dto.GithubUserInfo;
import corea.review.dto.GithubPullRequestReview;
import corea.review.dto.GithubPullRequestReviewInfo;

import java.util.Map;

public class GithubFixture {
    public static GithubPullRequestReviewInfo PULL_REQUEST_REVIEW_INFO(String githubUserId) {
        return new GithubPullRequestReviewInfo(
                Map.of(githubUserId, PULL_REQUEST_REVIEW(githubUserId))
        );
    }

    public static GithubPullRequestReview PULL_REQUEST_REVIEW(String githubUserId) {
        return new GithubPullRequestReview("pullRequestReviewId",
                new GithubUserInfo("youngsu5582", "name", "https://gongu.copyright.or.kr", "98307410"),
                "https://github.com/woowacourse-teams/2024-corea/pull/722#issuecomment-2456605981");
    }
}
