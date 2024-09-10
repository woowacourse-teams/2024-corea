package corea.matching.service;

import config.ServiceTest;
import corea.matching.domain.PullRequestInfo;
import corea.matching.infrastructure.GithubPullRequestClient;
import corea.matching.infrastructure.dto.GithubUserResponse;
import corea.matching.infrastructure.dto.PullRequestData;
import corea.matching.infrastructure.dto.PullRequestResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ServiceTest
class PullRequestProviderTest {

    @MockBean
    private GithubPullRequestClient githubPullRequestClient;

    @Autowired
    private PullRequestProvider pullRequestProvider;

    private String link = "https://api.github.com/repos/woowacourse-precourse/java-baseball-6/";
    private LocalDateTime githubTime = LocalDateTime.now(ZoneOffset.UTC);
    private LocalDateTime serverTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

    @BeforeEach
    void setUp() {
        when(githubPullRequestClient.getPullRequestListWithPageNumber(
                link,
                100,
                1
        )).thenReturn(
                new PullRequestData(new PullRequestResponse[]{
                        new PullRequestResponse(link + "8", new GithubUserResponse("6"), githubTime.minusHours(4)),
                        new PullRequestResponse(link + "7", new GithubUserResponse("5"), githubTime.minusHours(3))
                })
        );
        when(githubPullRequestClient.getPullRequestListWithPageNumber(
                link,
                100,
                2
        )).thenReturn(
                new PullRequestData(new PullRequestResponse[]{
                        new PullRequestResponse(link + "9", new GithubUserResponse("3"), githubTime.minusHours(2)),
                        new PullRequestResponse(link + "8", new GithubUserResponse("4"), githubTime.minusHours(1))
                })
        );
        when(githubPullRequestClient.getPullRequestListWithPageNumber(
                link,
                100,
                3
        )).thenReturn(new PullRequestData(new PullRequestResponse[]{}));

    }

    @Test
    @DisplayName("지정된 시간의 이전이면 값을 추가한다.")
    void get_until_deadline() {

        //이것보다 이전 시간의 값들을 가져온다. ( 2시간 전은 1시간 59분 전보다 이전 값이므로 가져온다. )
        PullRequestInfo pullRequestInfo = pullRequestProvider.getUntilDeadline(link, serverTime.minusHours(2)
                .plusMinutes(1));
        assertThat(pullRequestInfo.data()).hasSize(3);
    }

    @Test
    @DisplayName("지정한 시간보다 이후의 값들을 받아오기 시작하면, 멈춘다.")
    void stop_after_deadline() {
        //이것보다 이후의 값들은 가져오지 않는다. ( 2시간 전은 2시간 1분 전보다 이후 값이므로 가져오지 않는다. )
        PullRequestInfo pullRequestInfo = pullRequestProvider.getUntilDeadline(link, serverTime.minusHours(2)
                .minusMinutes(1));
        assertThat(pullRequestInfo.data()).hasSize(2);
    }

    @Test
    @DisplayName("마지막 페이지이면, 끝난다.")
    void stop_is_lastPage() {
        PullRequestInfo pullRequestInfo = pullRequestProvider.getUntilDeadline(link, serverTime.minusMinutes(59));
        assertThat(pullRequestInfo.data()).hasSize(4);
    }
}
