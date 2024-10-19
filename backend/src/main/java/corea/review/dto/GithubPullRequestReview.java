package corea.review.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import corea.auth.dto.GithubUserInfo;

public record GithubPullRequestReview(

        @JsonProperty("id")
        String id,

        @JsonProperty("user")
        GithubUserInfo user,

        @JsonProperty("html_url")
        String html_url
) {
    public String getUserId() {
        return user.id();
    }
}
