package corea.matching.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record PullRequestResponse(
        @JsonProperty("html_url")
        String pullRequestLink,
        GithubUserResponse user,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
        LocalDateTime created_at
) {

    public String getUserId() {
        return this.user.id();
    }

    public boolean isBefore(LocalDateTime localDateTime) {
        return this.created_at.isBefore(localDateTime);
    }
}
