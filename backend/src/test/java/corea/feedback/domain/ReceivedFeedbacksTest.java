package corea.feedback.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ReceivedFeedbacksTest {

    @Test
    @DisplayName("가장 많이 받은 긍정적인 피드백을 알 수 있다.")
    void findTopFeedbackKeywords1() {
        List<List<FeedbackKeyword>> developFeedbackKeywords = List.of(
                List.of(FeedbackKeyword.POSITIVE_DEVELOP_FEEDBACK_1, FeedbackKeyword.POSITIVE_DEVELOP_FEEDBACK_2, FeedbackKeyword.POSITIVE_DEVELOP_FEEDBACK_3),
                List.of(FeedbackKeyword.POSITIVE_DEVELOP_FEEDBACK_1, FeedbackKeyword.POSITIVE_DEVELOP_FEEDBACK_2, FeedbackKeyword.POSITIVE_DEVELOP_FEEDBACK_3),
                List.of(FeedbackKeyword.POSITIVE_DEVELOP_FEEDBACK_1, FeedbackKeyword.POSITIVE_DEVELOP_FEEDBACK_2),
                List.of(FeedbackKeyword.POSITIVE_DEVELOP_FEEDBACK_1)
        );
        List<List<FeedbackKeyword>> socialFeedbackKeywords = List.of(
                List.of(FeedbackKeyword.POSITIVE_SOCIAL_FEEDBACK_1, FeedbackKeyword.POSITIVE_SOCIAL_FEEDBACK_2),
                List.of(FeedbackKeyword.POSITIVE_SOCIAL_FEEDBACK_3)
        );
        ReceivedFeedbacks receivedFeedbacks = new ReceivedFeedbacks(developFeedbackKeywords, socialFeedbackKeywords);

        List<String> result = receivedFeedbacks.findTopFeedbackKeywords(3);

        assertThat(result).containsExactly("코드를 이해하기 쉬웠어요", "코드의 가독성이 뛰어나요", "코드가 일관성 있게 작성되어 있어요");
    }

    @Test
    @DisplayName("가장 많이 받은 긍정적인 피드백을 알 수 있다.")
    void findTopFeedbackKeywords2() {
        List<List<FeedbackKeyword>> developFeedbackKeywords = List.of(
                List.of(FeedbackKeyword.NEGATIVE_DEVELOP_FEEDBACK_1)
        );
        List<List<FeedbackKeyword>> socialFeedbackKeywords = List.of(
                List.of(FeedbackKeyword.POSITIVE_SOCIAL_FEEDBACK_1)
        );
        ReceivedFeedbacks receivedFeedbacks = new ReceivedFeedbacks(developFeedbackKeywords, socialFeedbackKeywords);

        List<String> result = receivedFeedbacks.findTopFeedbackKeywords(3);

        assertThat(result).containsExactly("친절했어요");
    }

    @Test
    @DisplayName("긍정적인 피드백이 없다면 아무런 피드백도 반환하지 않는다.")
    void findTopFeedbackKeywords3() {
        List<List<FeedbackKeyword>> developFeedbackKeywords = List.of(
                List.of(FeedbackKeyword.NEGATIVE_DEVELOP_FEEDBACK_1, FeedbackKeyword.NEGATIVE_DEVELOP_FEEDBACK_2)
        );
        List<List<FeedbackKeyword>> socialFeedbackKeywords = List.of(
                List.of(FeedbackKeyword.NORMAL_SOCIAL_FEEDBACK_1, FeedbackKeyword.NEGATIVE_SOCIAL_FEEDBACK_2)
        );
        ReceivedFeedbacks receivedFeedbacks = new ReceivedFeedbacks(developFeedbackKeywords, socialFeedbackKeywords);

        List<String> result = receivedFeedbacks.findTopFeedbackKeywords(3);

        assertThat(result).isEmpty();
    }
}
