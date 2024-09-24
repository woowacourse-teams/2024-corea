package corea.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GithubPullRequestReview(

        @JsonProperty("id")
        String id,
        @JsonProperty("user")
        GithubUserInfo user,
        @JsonProperty("html_url")
        String html_url
){
}
