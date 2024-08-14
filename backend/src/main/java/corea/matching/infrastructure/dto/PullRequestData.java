package corea.matching.infrastructure.dto;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Stream;

public record PullRequestData(boolean isLastPage, PullRequestResponse[] pullRequestResponses) {
    public boolean isAfterPage(LocalDateTime localDateTime) {
        LocalDateTime createdAt = pullRequestResponses[0].created_at();
        return createdAt.isAfter(localDateTime);
    }

    public Stream<PullRequestResponse> responseToStream() {
        return Arrays.stream(pullRequestResponses);
    }
}
