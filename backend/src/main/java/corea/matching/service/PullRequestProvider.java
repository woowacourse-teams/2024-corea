package corea.matching.service;

import corea.matching.domain.PullRequestInfo;
import corea.matching.infrastructure.GithubPullRequestClient;
import corea.matching.infrastructure.dto.PullRequestData;
import corea.matching.infrastructure.dto.PullRequestResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class PullRequestProvider {

    private static final Logger log = LogManager.getLogger(PullRequestProvider.class);
    private static final ZoneId SERVER_ZONE_ID = ZoneId.of("Asia/Seoul");
    private static final String UTC = "UTC";
    private static final int PAGE_SIZE = 100;

    private final GithubPullRequestClient githubPullRequestClient;

    public PullRequestInfo getUntilDeadline(String repositoryLink, LocalDateTime deadline) {
        log.debug("레포지토리 링크:{}, 마감 기한:{}", repositoryLink, deadline);
        LocalDateTime utcDeadline = convertUtc(deadline);
        return new PullRequestInfo(Stream.iterate(1, page -> page + 1)
                .map(page -> githubPullRequestClient.getPullRequestListWithPageNumber(repositoryLink, PAGE_SIZE, page))
                .takeWhile(data -> !(data.isLastPage() || data.isAfterPage(utcDeadline)))
                .flatMap(PullRequestData::responseToStream)
                .filter(pullRequestResponse -> pullRequestResponse.isBefore(utcDeadline))
                .collect(Collectors.toMap(PullRequestResponse::getUserId, Function.identity(), (k1, k2) -> k2)));
    }

    /**
     * LocalDateTime UTC 로 변환 함수
     * <p>
     * 깃허브 서버는 UTC 를 사용, 서버 및 DB 는 Seoul TimeZone 을 사용하므로
     * 의도한 대로 해당 시간까지 PR을 조회할 떄 동작하지 않습니다.
     * 이로 인해, UTC 로 변환합니다.
     *
     * @param localDateTime
     * @return
     * @author youngsu5582
     */
    private LocalDateTime convertUtc(LocalDateTime localDateTime) {
        return localDateTime.atZone(SERVER_ZONE_ID)
                .withZoneSameInstant(ZoneId.of(UTC))
                .toLocalDateTime();
    }
}
