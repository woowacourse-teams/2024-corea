package corea.fixture;

import corea.feedback.domain.DevelopFeedback;
import corea.feedback.domain.FeedbackKeyword;
import corea.member.domain.Member;

import java.util.List;

public class DevelopFeedbackFixture {

    public static DevelopFeedback POSITIVE_FEEDBACK(long roomId, Member deliver, Member receiver) {
        return new DevelopFeedback(
                roomId,
                deliver,
                receiver,
                4,
                List.of(FeedbackKeyword.POSITIVE_DEVELOP_FEEDBACK_1, FeedbackKeyword.POSITIVE_DEVELOP_FEEDBACK_2),
                "코드가 좋았어요",
                2
        );
    }
}
