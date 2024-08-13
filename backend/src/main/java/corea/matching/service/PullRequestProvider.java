package corea.matching.service;

import corea.matching.domain.PullRequestInfo;
import corea.matching.infrastructure.GithubPullRequestClient;
import corea.matching.infrastructure.dto.PullRequestData;
import corea.matching.infrastructure.dto.PullRequestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class PullRequestProvider {

    private final GithubPullRequestClient githubPullRequestClient;
    private static final int PAGE_SIZE = 100;

    public PullRequestInfo getUntilDeadline(String repositoryLink, LocalDateTime deadline) {
        return new PullRequestInfo(Stream.iterate(1, page -> page + 1)
                .map(page -> githubPullRequestClient.getPullRequestListWithPageNumber(repositoryLink, PAGE_SIZE, page))
                .takeWhile(data -> !(data.isLastPage() || data.isPastPage(deadline)))
                .flatMap(PullRequestData::responseToStream)
                .filter(pullRequestResponse -> pullRequestResponse.isAfter(deadline))
                .collect(Collectors.toMap(PullRequestResponse::getUserId, Function.identity())));
    }
}
