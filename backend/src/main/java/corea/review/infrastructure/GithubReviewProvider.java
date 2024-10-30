package corea.review.infrastructure;

import corea.review.dto.GithubPullRequestReview;
import corea.review.dto.GithubPullRequestReviewInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class GithubReviewProvider {

    private final GithubReviewClient reviewClient;
    private final GithubCommentClient commentClient;

    public GithubPullRequestReviewInfo provideReviewInfo(String prLink) {
        //TODO: getPullRequestReviews, getPullRequestComments에서 prLink를 중복으로 검증하고 있음.
        List<GithubPullRequestReview> reviews = reviewClient.getPullRequestReviews(prLink);
        List<GithubPullRequestReview> comments = commentClient.getPullRequestComments(prLink);

        Map<String, GithubPullRequestReview> result = collectPullRequestReviews(reviews, comments);
        return new GithubPullRequestReviewInfo(result);
    }

    private Map<String, GithubPullRequestReview> collectPullRequestReviews(List<GithubPullRequestReview> reviews, List<GithubPullRequestReview> comments) {
        return Stream.concat(
                        reviews.stream(),
                        comments.stream()
                )
                .collect(Collectors.toMap(
                        GithubPullRequestReview::getGithubUserId,
                        Function.identity(),
                        (x, y) -> x
                ));
    }
}
