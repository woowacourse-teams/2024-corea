package corea.room.domain;

import corea.auth.dto.GithubPullRequestReview;
import corea.auth.dto.GithubUserInfo;
import corea.exception.CoreaException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PullRequestReviewsTest {

    private final GithubPullRequestReview[] githubPullRequestReviews = new GithubPullRequestReview[]{
            new GithubPullRequestReview("1",
                    new GithubUserInfo(
                            "youngsu5582",
                            "조희선",
                            "https://gongu.copyright.or.kr/gongu/wrt",
                            "corea@naver.com",
                            "98307410"
                    ),
                    "review_url"
            )
    };

    @Test
    @DisplayName("리뷰가 존재하면, 리뷰의 URL 을 가져온다.")
    void get_html_url_when_exist_review() {
        PullRequestReviews pullRequestReviews = new PullRequestReviews(githubPullRequestReviews);
        String reviewUrl = pullRequestReviews.getReviewUrl("98307410");
        Assertions.assertThat(reviewUrl)
                .isEqualTo("review_url");
    }

    @Test
    @DisplayName("리뷰가 존재하지 않는 ID 를 넣으면 예외를 발생한다.")
    void throw_exception_when_review_not_exist() {
        PullRequestReviews pullRequestReviews = new PullRequestReviews(githubPullRequestReviews);
        assertThatThrownBy(() -> pullRequestReviews.getReviewUrl("notExist"))
                .isInstanceOf(CoreaException.class);
    }

}
