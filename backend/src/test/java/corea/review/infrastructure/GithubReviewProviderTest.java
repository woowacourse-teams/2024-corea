package corea.review.infrastructure;

import corea.exception.CoreaException;
import corea.review.dto.GithubPullRequestReview;
import corea.review.dto.GithubPullRequestReviewInfo;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Disabled
@SpringBootTest
class GithubReviewProviderTest {

    @Autowired
    private GithubReviewProvider githubReviewProvider;

    @Test
    @DisplayName("https:// 로 시작하지 않는 주소에 대해 예외를 발생한다.")
    void validate1() {
        String test = "http://github.com/woowacourse-teams/2024-corea/pull/1";

        assertThatThrownBy(() -> githubReviewProvider.provideReviewInfo(test))
                .isInstanceOf(CoreaException.class);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("빈 주소에 대해 예외를 발생한다.")
    void validate2(String test) {
        assertThatThrownBy(() -> githubReviewProvider.provideReviewInfo(test))
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
        assertThatThrownBy(() -> githubReviewProvider.provideReviewInfo(test))
                .isInstanceOf(CoreaException.class);
    }

    @Test
    @DisplayName("한 사람이 하나의 리뷰를 남겼을 시, 리뷰한 사람의 리뷰 링크를 찾는다.")
    void provideReviewInfo1() {
        GithubPullRequestReviewInfo result = githubReviewProvider.provideReviewInfo("https://github.com/youngsu5582/github-api-test/pull/5");

        // 입력된 pr에 텐텐이 남긴 리뷰 -> 1개 존재
        GithubPullRequestReview review = result.findWithGithubUserId("63334368").get();

        assertThat(review.html_url()).isEqualTo("https://github.com/youngsu5582/github-api-test/pull/5#pullrequestreview-2362410991");
    }

    @Test
    @DisplayName("한 사람이 하나의 커멘트를 남겼을 시, 리뷰한 사람의 커멘트 링크를 찾는다.")
    void provideReviewInfo2() {
        GithubPullRequestReviewInfo result = githubReviewProvider.provideReviewInfo("https://github.com/youngsu5582/github-api-test/pull/10");

        // 입력된 pr에 뽀로로가 남긴 커멘트 -> 1개 존재
        GithubPullRequestReview review = result.findWithGithubUserId("119468757").get();

        assertThat(review.html_url()).isEqualTo("https://github.com/youngsu5582/github-api-test/pull/10#issuecomment-2429806517");
    }

    @Test
    @DisplayName("한 사람이 여러 리뷰를 남겼을 시, 첫번째 리뷰 링크를 찾는다.")
    void provideReviewInfo3() {
        GithubPullRequestReviewInfo result = githubReviewProvider.provideReviewInfo("https://github.com/youngsu5582/github-api-test/pull/5");

        // 입력된 pr에 무빈이 남긴 리뷰 -> 2개 존재
        GithubPullRequestReview review = result.findWithGithubUserId("80106238").get();

        assertThat(review.html_url()).isEqualTo("https://github.com/youngsu5582/github-api-test/pull/5#pullrequestreview-2327171078");
    }

    @Test
    @DisplayName("한 사람이 여러 커멘트를 남겼을 시, 첫번째 커멘트 링크를 찾는다.")
    void provideReviewInfo4() {
        GithubPullRequestReviewInfo result = githubReviewProvider.provideReviewInfo("https://github.com/youngsu5582/github-api-test/pull/3");

        // 입력된 pr에 뽀로로가 남긴 커멘트 -> 2개 존재
        GithubPullRequestReview review = result.findWithGithubUserId("119468757").get();

        assertThat(review.html_url()).isEqualTo("https://github.com/youngsu5582/github-api-test/pull/3#issuecomment-2429811119");
    }

    @Test
    @DisplayName("한 사람이 여러 리뷰와 커멘트를 남겼을 시, 첫번째 리뷰 링크를 찾는다.")
    void provideReviewInfo5() {
        GithubPullRequestReviewInfo result = githubReviewProvider.provideReviewInfo("https://github.com/youngsu5582/github-api-test/pull/1");

        // 입력된 pr에 뽀로로가 남긴 리뷰 -> 2개 존재
        // 입력된 pr에 뽀로로가 남긴 커멘트 -> 2개 존재
        GithubPullRequestReview review = result.findWithGithubUserId("119468757").get();

        assertThat(review.html_url()).isEqualTo("https://github.com/youngsu5582/github-api-test/pull/1#pullrequestreview-2383980194");
    }
}
