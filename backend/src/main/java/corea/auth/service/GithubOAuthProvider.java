package corea.auth.service;

import corea.auth.dto.GithubPullRequestReview;
import corea.auth.dto.GithubUserInfo;
import corea.auth.infrastructure.GithubOAuthClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GithubOAuthProvider {

    private final GithubOAuthClient githubOAuthClient;

    public GithubUserInfo getUserInfo(String code) {
        String accessToken = githubOAuthClient.getAccessToken(code);
        return githubOAuthClient.getUserInfo(accessToken);
    }

    public GithubPullRequestReview[] getPullRequestReview(String prLink) {
        return githubOAuthClient.getReviewLink(prLink);
    }
}
