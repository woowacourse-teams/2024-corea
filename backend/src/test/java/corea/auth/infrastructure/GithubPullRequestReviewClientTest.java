package corea.auth.infrastructure;

import corea.review.dto.GithubPullRequestReview;
import corea.review.infrastructure.GithubReviewClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class GithubPullRequestReviewClientTest {

    @Autowired
    private GithubReviewClient client;

    @Test
    @DisplayName("해당 PR 링크에 존재하는 리뷰들을 가져온다.")
    void getReviewLink() {
        String prLink1 = "https://github.com/woowacourse-teams/2024-corea/pull/10";
        String prLink2 = "https://github.com/woowacourse-teams/2024-corea/pull/96";
        String prLink3 = "https://github.com/woowacourse-teams/2024-corea/pull/114";
        String prLink4 = "https://github.com/woowacourse-teams/2024-corea/pull/8";

        GithubPullRequestReview[] reviews1 = client.getReviewLink(prLink1);
        GithubPullRequestReview[] reviews2 = client.getReviewLink(prLink2);
        GithubPullRequestReview[] reviews3 = client.getReviewLink(prLink3);
        GithubPullRequestReview[] reviews4 = client.getReviewLink(prLink4);

        assertThat(reviews1).hasSize(1);
        assertThat(reviews2).hasSize(8);
        assertThat(reviews3).hasSize(7);
        assertThat(reviews4).hasSize(1);
    }
}
