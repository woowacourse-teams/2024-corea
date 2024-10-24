package corea.review.infrastructure;

import corea.review.dto.GithubPullRequestReview;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled
@SpringBootTest
class GithubPullRequestReviewClientTest {

    @Autowired
    private GithubPullRequestReviewClient client;

    @Test
    @DisplayName("해당 PR 링크에 존재하는 리뷰들을 가져온다.")
    void getPullRequestReviews() {
        String prLink1 = "https://github.com/woowacourse-teams/2024-corea/pull/10";
        String prLink2 = "https://github.com/woowacourse-teams/2024-corea/pull/96";
        String prLink3 = "https://github.com/woowacourse-teams/2024-corea/pull/114";
        String prLink4 = "https://github.com/woowacourse-teams/2024-corea/pull/8";

        List<GithubPullRequestReview> reviews1 = client.getPullRequestReviews(prLink1);
        List<GithubPullRequestReview> reviews2 = client.getPullRequestReviews(prLink2);
        List<GithubPullRequestReview> reviews3 = client.getPullRequestReviews(prLink3);
        List<GithubPullRequestReview> reviews4 = client.getPullRequestReviews(prLink4);

        assertThat(reviews1).hasSize(1);
        assertThat(reviews2).hasSize(8);
        assertThat(reviews3).hasSize(7);
        assertThat(reviews4).hasSize(1);
    }
}
