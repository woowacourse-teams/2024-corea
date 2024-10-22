package corea.review.infrastructure;

import corea.exception.CoreaException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GithubPullRequestUrlExchangerTest {

    @Test
    @DisplayName("올바른 Pull Request URL 을 상응하는 Github API 요청 주소로 변환한다.")
    void test() {
        String test = "https://github.com/woowacourse-teams/2024-corea/pull/1";
        String expected = "https://api.github.com/repos/woowacourse-teams/2024-corea/pulls/1/reviews";

        assertThat(GithubPullRequestUrlExchanger.pullRequestUrlToReview(test)).isEqualTo(expected);
    }

    @Test
    @DisplayName("https:// 로 시작하지 않는 주소에 대해 예외를 발생한다.")
    void validate1() {
        String test = "http://github.com/woowacourse-teams/2024-corea/pull/1";

        assertThatThrownBy(() -> GithubPullRequestUrlExchanger.pullRequestUrlToReview(test))
                .isInstanceOf(CoreaException.class);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("빈 주소에 대해 예외를 발생한다.")
    void validate2(String test) {
        assertThatThrownBy(() -> GithubPullRequestUrlExchanger.pullRequestUrlToReview(test))
                .isInstanceOf(CoreaException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "https://githubs.com/woowacourse-teams/2024-corea/pull/1",
            "https://github.com/woowacourse-teams/2024-corea/pulls/1",
            "https://github.com/woowacourse-teams/2024-corea/pulls/1/3",
            "https://github.com/woowacourse-teams/2024-corea/pull"})
    @DisplayName("올바르지 않은 Pull Request 주소에 대해 예외를 발생한다.")
    void validate3(String test) {
        assertThatThrownBy(() -> GithubPullRequestUrlExchanger.pullRequestUrlToReview(test))
                .isInstanceOf(CoreaException.class);
    }
}
