package corea.review.dto;

import java.util.Map;
import java.util.Optional;

public record GithubPullRequestReviewInfo(Map<String, GithubPullRequestReview> data) {

    public Optional<GithubPullRequestReview> findWithGithubUserId(String id) {
        return Optional.ofNullable(data.get(id));
    }
}
