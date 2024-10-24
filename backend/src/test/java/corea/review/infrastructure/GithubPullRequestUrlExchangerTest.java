package corea.review.infrastructure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled
class GithubPullRequestUrlExchangerTest {

    private GithubPullRequestUrlExchanger githubPullRequestUrlExchanger;

    @BeforeEach
    void setUp() {
        githubPullRequestUrlExchanger = new GithubPullRequestUrlExchanger();
    }

    @Test
    @DisplayName("올바른 Pull Request URL 을 상응하는 Github API review 요청 주소로 변환한다.")
    void prLinkToReviewApiUrl() {
        String test = "https://github.com/woowacourse-teams/2024-corea/pull/1";
        String expected = "https://api.github.com/repos/woowacourse-teams/2024-corea/pulls/1/reviews";

        assertThat(githubPullRequestUrlExchanger.prLinkToReviewApiUrl(test)).isEqualTo(expected);
    }

    @Test
    @DisplayName("올바른 Pull Request URL 을 상응하는 Github API comment 요청 주소로 변환한다.")
    void prLinkToCommentApiUrl() {
        String test = "https://github.com/woowacourse-teams/2024-corea/pull/1";
        String expected = "https://api.github.com/repos/woowacourse-teams/2024-corea/issues/1/comments";

        assertThat(githubPullRequestUrlExchanger.prLinkToCommentApiUrl(test)).isEqualTo(expected);
    }
}
