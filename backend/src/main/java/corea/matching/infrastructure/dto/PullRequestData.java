package corea.matching.infrastructure.dto;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Stream;

public record PullRequestData(boolean isLastPage, PullRequestResponse[] pullRequestResponses) {
    public boolean isPastPage(LocalDateTime localDateTime) {
        LocalDateTime createdAt = pullRequestResponses[0].created_at();
        return createdAt.isBefore(localDateTime);
    }

    public Stream<PullRequestResponse> responseToStream() {
        return Arrays.stream(pullRequestResponses);
    }
}
