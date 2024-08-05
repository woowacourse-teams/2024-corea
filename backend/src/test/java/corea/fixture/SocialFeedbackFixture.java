package corea.fixture;

import corea.feedback.domain.FeedbackKeyword;
import corea.feedback.domain.SocialFeedback;
import corea.member.domain.Member;

import java.util.List;

public class SocialFeedbackFixture {

    public static SocialFeedback POSITIVE_FEEDBACK(long roomId, Member deliver, Member receiver) {
        return new SocialFeedback(
                null,
                roomId,
                deliver,
                receiver,
                4,
                List.of(FeedbackKeyword.HELPFUL, FeedbackKeyword.EASY_TO_UNDERSTAND_THE_CODE),
                "코드가 좋았어요"
        );
    }
}
