package corea.fixture;

import corea.feedback.domain.FeedbackKeyword;
import corea.feedback.domain.ReviewerToRevieweeFeedback;
import corea.member.domain.Member;

import java.util.List;

public class ReviewerToRevieweeFeedbackFixture {

    public static ReviewerToRevieweeFeedback POSITIVE_FEEDBACK(long roomId, Member reviewer, Member reviewee) {
        return new ReviewerToRevieweeFeedback(
                roomId,
                reviewer,
                reviewee,
                4,
                List.of(FeedbackKeyword.HELPFUL, FeedbackKeyword.EASY_TO_UNDERSTAND_THE_CODE),
                "코드가 좋았어요",
                2
        );
    }
}
