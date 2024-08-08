package corea.auth.dto;

import corea.auth.domain.GithubUserInfo;

public record LoginResponse(String refreshToken, GithubUserInfo userInfo) {
}
