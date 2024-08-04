package corea.fixture;

import corea.feedback.domain.FeedbackKeyword;
import corea.feedback.domain.RevieweeToReviewerFeedback;
import corea.member.domain.Member;

import java.util.List;

public class RevieweeToReviewerFeedbackFixture {

    public static RevieweeToReviewerFeedback POSITIVE_FEEDBACK(long roomId, Member reviewer, Member reviewee) {
        return new RevieweeToReviewerFeedback(
                null,
                roomId,
                reviewer,
                reviewee,
                4,
                List.of(FeedbackKeyword.HELPFUL, FeedbackKeyword.EASY_TO_UNDERSTAND_THE_CODE),
                "코드가 좋았어요"
        );
    }
}
