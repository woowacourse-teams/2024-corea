package corea.review.infrastructure;

import corea.review.dto.GithubPullRequestReview;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class GithubCommentClientTest {

    @Autowired
    private GithubCommentClient githubCommentClient;

    @Test
    @DisplayName("해당 PR 링크에 존재하는 커멘트들을 가져온다.")
    void getCommentLink() {
        String prLink = "https://github.com/youngsu5582/github-api-test/pull/1";

        List<GithubPullRequestReview> comments = githubCommentClient.getCommentLink(prLink);

        assertThat(comments).hasSize(2);
    }
}
