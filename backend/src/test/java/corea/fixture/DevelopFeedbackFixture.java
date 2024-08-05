package corea.fixture;

import corea.feedback.domain.FeedbackKeyword;
import corea.feedback.domain.DevelopFeedback;
import corea.member.domain.Member;

import java.util.List;

public class DevelopFeedbackFixture {

    public static DevelopFeedback POSITIVE_FEEDBACK(long roomId, Member deliver, Member receiver) {
        return new DevelopFeedback(
                roomId,
                deliver,
                receiver,
                4,
                List.of(FeedbackKeyword.HELPFUL, FeedbackKeyword.EASY_TO_UNDERSTAND_THE_CODE),
                "코드가 좋았어요",
                2
        );
    }
}
