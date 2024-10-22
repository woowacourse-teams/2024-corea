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

    public GithubPullRequestReviewInfo getReviewWithPrLink(String prLink) {
        List<GithubPullRequestReview> reviewLinks = reviewClient.getReviewLink(prLink);
        List<GithubPullRequestReview> commentLinks = commentClient.getCommentLink(prLink);

        Map<String, GithubPullRequestReview> result = getGithubPullRequestReviews(reviewLinks, commentLinks);
        return new GithubPullRequestReviewInfo(result);
    }

    private Map<String, GithubPullRequestReview> getGithubPullRequestReviews(List<GithubPullRequestReview> reviewLinks, List<GithubPullRequestReview> commentLinks) {
        return Stream.concat(
                        reviewLinks.stream(),
                        commentLinks.stream()
                )
                .collect(Collectors.toMap(
                        GithubPullRequestReview::getGithubUserId,
                        Function.identity(),
                        (x, y) -> x
                ));
    }
}
