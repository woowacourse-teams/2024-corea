package corea.auth.service;

import corea.auth.dto.GithubUserInfo;
import corea.auth.infrastructure.GithubOAuthClient;
import corea.room.domain.PullRequestReviews;
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

    public PullRequestReviews getPullRequestReview(String prLink) {
        return new PullRequestReviews(githubOAuthClient.getReviewLink(prLink));
    }
}
