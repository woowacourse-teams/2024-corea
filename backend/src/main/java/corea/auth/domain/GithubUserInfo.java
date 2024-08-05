package corea.auth.domain;

public record GithubUserInfo(
        String login,
        String name,
        String avatar_url,
        String email) {
}
