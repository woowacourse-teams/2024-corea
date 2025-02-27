package corea.matching.infrastructure;

import config.ServiceTest;
import corea.matching.infrastructure.dto.GithubUserResponse;
import corea.matching.infrastructure.dto.PullRequestResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@ServiceTest
@Disabled
class PrivateGithubPullRequestClientTest {

    @Autowired
    PrivateGithubPullRequestClient privateGithubPullRequestClient;

    @Test
    @DisplayName("개인 레포지토리의 PR을 가져온다.")
    void get_private_repository() {
        String repositoryLink = "https://github.com/woowacourse-precourse/java-christmas-6";
        String username = "youngsu5582";
        PullRequestResponse response = privateGithubPullRequestClient.getPullRequest(repositoryLink, username);
        assertThat(response.pullRequestLink()).isEqualTo("https://github.com/youngsu5582/java-christmas-6-youngsu5582/pull/1");
        assertThat(response.user()).isEqualTo(new GithubUserResponse("98307410"));
    }

    @Test
    @DisplayName("레포지토리가 없으면 null 을 반환한다.")
    void get_null_if_not_exist_repository() {
        String repositoryLink = "https://github.com/woowacourse-precourse/java-christmas-6";
        String username = "00kang";
        PullRequestResponse response = privateGithubPullRequestClient.getPullRequest(repositoryLink, username);
        assertThat(response).isNull();
    }
}
