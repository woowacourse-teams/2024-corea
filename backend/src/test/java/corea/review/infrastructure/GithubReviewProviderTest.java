package corea.review.infrastructure;

import corea.review.dto.GithubPullRequestReview;
import corea.review.dto.GithubPullRequestReviewInfo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GithubReviewProviderTest {

    @Autowired
    private GithubReviewProvider githubReviewProvider;

    @Test
    @DisplayName("리뷰한 사람의 리뷰 링크를 찾을 수 있다.")
    void getReviewWithPrLink() {
        GithubPullRequestReviewInfo result = githubReviewProvider.getReviewWithPrLink("https://github.com/youngsu5582/github-api-test/pull/5");

        // 입력된 pr에 무빈이 남긴 리뷰 -> 1개만 존재
        GithubPullRequestReview review = result.findWithGithubUserId("80106238").get();

        Assertions.assertThat(review.html_url()).isEqualTo("https://github.com/youngsu5582/github-api-test/pull/5#pullrequestreview-2327171078");
    }

    @Test
    @DisplayName("한 사람이 여러 리뷰를 남겼을 시, 첫번째 리뷰 링크를 찾는다.")
    void getReviewWithPrLink_duplicatedKey() {
        GithubPullRequestReviewInfo result = githubReviewProvider.getReviewWithPrLink("https://github.com/youngsu5582/github-api-test/pull/5");

        // 입력된 pr에 뽀로로가 남긴 리뷰 -> 2개 존재
        GithubPullRequestReview review = result.findWithGithubUserId("119468757").get();

        Assertions.assertThat(review.html_url()).isEqualTo("https://github.com/youngsu5582/github-api-test/pull/5#pullrequestreview-2327172283");
    }
}
