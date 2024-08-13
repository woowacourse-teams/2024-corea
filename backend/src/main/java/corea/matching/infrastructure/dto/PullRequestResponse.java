package corea.matching.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record PullRequestResponse(
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
