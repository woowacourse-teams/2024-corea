package corea.review.dto;

import java.util.Map;
import java.util.Optional;

public record GithubPullRequestReviewInfo(Map<String, GithubPullRequestReview> data) {
    public Optional<GithubPullRequestReview> findWithGithubId(String id) {
        return Optional.ofNullable(data.get(id));
    }
}
