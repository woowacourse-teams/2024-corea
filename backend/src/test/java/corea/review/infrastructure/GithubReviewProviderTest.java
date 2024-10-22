package corea.review.infrastructure;

import corea.review.dto.GithubPullRequestReview;
import corea.review.dto.GithubPullRequestReviewInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class GithubReviewProviderTest {

    @Autowired
    private GithubReviewProvider githubReviewProvider;

    @Test
    @DisplayName("한 사람이 하나의 리뷰를 남겼을 시, 리뷰한 사람의 리뷰 링크를 찾는다.")
    void getReviewWithPrLink1() {
        GithubPullRequestReviewInfo result = githubReviewProvider.getReviewWithPrLink("https://github.com/youngsu5582/github-api-test/pull/5");

        // 입력된 pr에 텐텐이 남긴 리뷰 -> 1개 존재
        GithubPullRequestReview review = result.findWithGithubUserId("63334368").get();

        assertThat(review.html_url()).isEqualTo("https://github.com/youngsu5582/github-api-test/pull/5#pullrequestreview-2362410991");
    }

    @Test
    @DisplayName("한 사람이 하나의 커멘트를 남겼을 시, 리뷰한 사람의 커멘트 링크를 찾는다.")
    void getReviewWithPrLink2() {
        GithubPullRequestReviewInfo result = githubReviewProvider.getReviewWithPrLink("https://github.com/youngsu5582/github-api-test/pull/10");

        // 입력된 pr에 뽀로로가 남긴 커멘트 -> 1개 존재
        GithubPullRequestReview review = result.findWithGithubUserId("119468757").get();

        assertThat(review.html_url()).isEqualTo("https://github.com/youngsu5582/github-api-test/pull/10#issuecomment-2429806517");
    }

    @Test
    @DisplayName("한 사람이 여러 리뷰를 남겼을 시, 첫번째 리뷰 링크를 찾는다.")
    void getReviewWithPrLink3() {
        GithubPullRequestReviewInfo result = githubReviewProvider.getReviewWithPrLink("https://github.com/youngsu5582/github-api-test/pull/5");

        // 입력된 pr에 무빈이 남긴 리뷰 -> 2개 존재
        GithubPullRequestReview review = result.findWithGithubUserId("80106238").get();

        assertThat(review.html_url()).isEqualTo("https://github.com/youngsu5582/github-api-test/pull/5#pullrequestreview-2327171078");
    }

    @Test
    @DisplayName("한 사람이 여러 커멘트를 남겼을 시, 첫번째 커멘트 링크를 찾는다.")
    void getReviewWithPrLink4() {
        GithubPullRequestReviewInfo result = githubReviewProvider.getReviewWithPrLink("https://github.com/youngsu5582/github-api-test/pull/3");

        // 입력된 pr에 뽀로로가 남긴 커멘트 -> 2개 존재
        GithubPullRequestReview review = result.findWithGithubUserId("119468757").get();

        assertThat(review.html_url()).isEqualTo("https://github.com/youngsu5582/github-api-test/pull/3#issuecomment-2429811119");
    }

    @Test
    @DisplayName("한 사람이 여러 리뷰와 커멘트를 남겼을 시, 첫번째 리뷰 링크를 찾는다.")
    void getReviewWithPrLink5() {
        GithubPullRequestReviewInfo result = githubReviewProvider.getReviewWithPrLink("https://github.com/youngsu5582/github-api-test/pull/1");

        // 입력된 pr에 뽀로로가 남긴 리뷰 -> 2개 존재
        // 입력된 pr에 뽀로로가 남긴 커멘트 -> 2개 존재
        GithubPullRequestReview review = result.findWithGithubUserId("119468757").get();

        assertThat(review.html_url()).isEqualTo("https://github.com/youngsu5582/github-api-test/pull/1#pullrequestreview-2383980194");
    }
}
