package corea.review.infrastructure;

import corea.auth.infrastructure.GithubPersonalAccessTokenProvider;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class GithubPullRequestCommentClient extends GithubReviewClient {

    public GithubPullRequestCommentClient(RestClient restClient, GithubPullRequestUrlExchanger githubPullRequestUrlExchanger, GithubPersonalAccessTokenProvider githubPersonalAccessTokenProvider) {
        super(restClient, githubPullRequestUrlExchanger, githubPersonalAccessTokenProvider);
    }

    @Override
    protected String prLinkToGithubApiUrl(String prLink) {
        GithubPullRequestUrlExchanger githubPullRequestUrlExchanger = getGithubPullRequestUrlExchanger();
        return githubPullRequestUrlExchanger.prLinkToCommentApiUrl(prLink);
    }
}
