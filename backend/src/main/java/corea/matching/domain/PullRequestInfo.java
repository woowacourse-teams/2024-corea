package corea.matching.domain;

import corea.matching.infrastructure.dto.PullRequestResponse;

import java.util.Map;

public record PullRequestInfo(Map<String, PullRequestResponse> data) {

    public boolean containsGithubMemberId(String githubMemberId) {
        return data.containsKey(githubMemberId);
    }

    public String getPullrequestLinkWithGithubMemberId(String githubMemberId) {
        return data.get(githubMemberId)
                .pullRequestLink();
    }
}
