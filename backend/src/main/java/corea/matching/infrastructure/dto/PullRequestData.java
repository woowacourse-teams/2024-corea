package corea.matching.infrastructure.dto;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

public record PullRequestData(PullRequestResponse[] pullRequestResponses) {

    public boolean isAfterPage(LocalDateTime localDateTime) {
        LocalDateTime createdAt = pullRequestResponses[0].created_at();
        return createdAt.isAfter(localDateTime);
    }

    public boolean isLastPage() {
        return pullRequestResponses.length == 0;
    }

    public Stream<PullRequestResponse> responseToStream() {
        return Arrays.stream(pullRequestResponses);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof PullRequestData that)) return false;
        return Objects.deepEquals(pullRequestResponses, that.pullRequestResponses);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(pullRequestResponses);
    }

    @Override
    public String toString() {
        return "PullRequestData{" +
                "pullRequestResponses=" + Arrays.toString(pullRequestResponses) +
                '}';
    }
}
