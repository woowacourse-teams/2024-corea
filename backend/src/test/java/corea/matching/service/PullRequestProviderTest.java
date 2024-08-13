package corea.matching.service;

import corea.matching.domain.PullRequestInfo;
import corea.matching.infrastructure.GithubPullRequestClient;
import corea.matching.infrastructure.dto.GithubUserResponse;
import corea.matching.infrastructure.dto.PullRequestData;
import corea.matching.infrastructure.dto.PullRequestResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
class PullRequestProviderTest {

    @MockBean
    private GithubPullRequestClient githubPullRequestClient;

    @Autowired
    private PullRequestProvider pullRequestProvider;

    private String link = "https://api.github.com/repos/woowacourse-precourse/java-baseball-6/";
    private LocalDateTime time = LocalDateTime.now();

    @BeforeEach
    void setUp() {
        when(githubPullRequestClient.getPullRequestListWithPageNumber(
                link,
                100,
                1
        )).thenReturn(
                new PullRequestData(false,
                        new PullRequestResponse[]{
                                new PullRequestResponse(link + "9", new GithubUserResponse("3"), time.minusHours(1)),
                                new PullRequestResponse(link + "8", new GithubUserResponse("4"), time.minusHours(2))
                        })
        );
        when(githubPullRequestClient.getPullRequestListWithPageNumber(
                link,
                100,
                2
        )).thenReturn(
                new PullRequestData(false,
                        new PullRequestResponse[]{
                                new PullRequestResponse(link + "8", new GithubUserResponse("6"), time.minusHours(3)),
                                new PullRequestResponse(link + "7", new GithubUserResponse("5"), time.minusHours(4))
                        })
        );
        when(githubPullRequestClient.getPullRequestListWithPageNumber(
                link,
                100,
                3
        )).thenReturn(new PullRequestData(true, new PullRequestResponse[]{}));

    }

    @Test
    @DisplayName("지정한 시간보다 이전의 값들을 받아오기 시작하면, 멈춘다.")
    void getUntilDeadline() {

        //이것보다 이전 시간의 값들을 가져오지 않게한다. ( 3시간 전은 2시간 59분 전보다 이전 값이므로 가져오지 못한다. )
        PullRequestInfo pullRequestInfo = pullRequestProvider.getUntilDeadline(link, time.minusHours(3)
                .plusMinutes(1));
        assertThat(pullRequestInfo.data()).hasSize(2);
    }

    @Test
    void getUntilDeadline3() {
        //이것보다 이후의 값들은 가져온다. ( 3시간 전은 3시간 1분 전보다 이후 값이므로 가져온다. )
        PullRequestInfo pullRequestInfo = pullRequestProvider.getUntilDeadline(link, time.minusHours(3)
                .minusMinutes(1));
        assertThat(pullRequestInfo.data()).hasSize(3);
    }

    @Test
    @DisplayName("마지막 페이지이면, 끝난다.")
    void getUntilDeadline1() {
        PullRequestInfo pullRequestInfo = pullRequestProvider.getUntilDeadline(link, time.minusHours(5));
        assertThat(pullRequestInfo.data()).hasSize(4);
    }
}
