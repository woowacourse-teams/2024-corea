package corea.auth.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GithubUserInfo(
        String login,
        String name,
        @JsonProperty("avatar_url")
        String avatarUrl,
        String email) {

    public GithubUserInfo {
        if (email == null) {
            email = "";
        }
    }
}
