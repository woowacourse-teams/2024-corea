package corea.matching.dto;

import corea.matching.infrastructure.dto.GithubUserResponse;
import corea.matching.infrastructure.dto.PullRequestData;
import corea.matching.infrastructure.dto.PullRequestResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class PullRequestDataTest {

    @Test
    @DisplayName("페이지 처음 데이터의 시간보다 주어진 시간이 지났으면 지난 페이지가 된다.")
    void some() {
        GithubUserResponse githubUserResponse = new GithubUserResponse("3");
        LocalDateTime time = LocalDateTime.of(2024, 11, 24, 18, 31);
        PullRequestData pullRequestData = new PullRequestData(new PullRequestResponse[]{new PullRequestResponse("https://pullRequestLink.com", githubUserResponse, time)});

        boolean isAftered = pullRequestData.isAfterPage(LocalDateTime.of(2024, 11, 24, 18, 30));
        assertThat(isAftered).isTrue();
    }
}
