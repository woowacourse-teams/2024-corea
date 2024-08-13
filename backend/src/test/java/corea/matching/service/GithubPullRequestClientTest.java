package corea.matching.service;

import corea.matching.infrastructure.GithubPullRequestClient;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class
GithubPullRequestClientTest {

    @Autowired
    GithubPullRequestClient client;

    @Test
    @Disabled
    @DisplayName("레포지토리 내 PR 목록을 가져온다.")
    void getPullRequestListWithPageNumber() {
        String repositoryLink = "https://api.github.com/repos/woowacourse-precourse/java-baseball-6";
        final var result = client.getPullRequestListWithPageNumber(repositoryLink, 1, 1);
        result.responseToStream()
                .forEach(System.out::println);
        assertThat(result.pullRequestResponses()).hasSize(1);
        assertThat(result.isLastPage()).isFalse();
    }
}
