package corea.review.infrastructure;

import corea.review.dto.GithubPullRequestReview;
import corea.review.dto.GithubPullRequestReviewInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GithubReviewProvider {

    private final GithubReviewClient reviewClient;

    public GithubPullRequestReviewInfo getReviewWithPrLink(String prLink) {
        final GithubPullRequestReview[] result = reviewClient.getReviewLink(prLink);
        return new GithubPullRequestReviewInfo(Arrays.stream(result)
                .collect(Collectors.toMap(
                        GithubPullRequestReview::getGithubUserId,
                        Function.identity(),
                        (x, y) -> x
                )));
    }
}
