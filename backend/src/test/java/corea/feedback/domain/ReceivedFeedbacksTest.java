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
                List.of(FeedbackKeyword.KIND, FeedbackKeyword.HELPFUL, FeedbackKeyword.REVIEW_FAST),
                List.of(FeedbackKeyword.KIND, FeedbackKeyword.HELPFUL, FeedbackKeyword.REVIEW_FAST),
                List.of(FeedbackKeyword.KIND, FeedbackKeyword.HELPFUL),
                List.of(FeedbackKeyword.KIND)
        );
        List<List<FeedbackKeyword>> socialFeedbackKeywords = List.of(
                List.of(FeedbackKeyword.MAKE_CODE_FOR_THE_PURPOSE, FeedbackKeyword.EASY_TO_UNDERSTAND_THE_CODE),
                List.of(FeedbackKeyword.RESPONSE_FAST)
        );
        ReceivedFeedbacks receivedFeedbacks = new ReceivedFeedbacks(developFeedbackKeywords, socialFeedbackKeywords);

        List<String> result = receivedFeedbacks.findTopFeedbackKeywords(3);

        assertThat(result).containsExactly("친절했어요", "매우 도움이 되었어요", "리뷰 속도가 빨랐어요");
    }

    @Test
    @DisplayName("가장 많이 받은 긍정적인 피드백을 알 수 있다.")
    void findTopFeedbackKeywords2() {
        List<List<FeedbackKeyword>> developFeedbackKeywords = List.of(
                List.of(FeedbackKeyword.KIND)
        );
        List<List<FeedbackKeyword>> socialFeedbackKeywords = List.of(
                List.of(FeedbackKeyword.RESPONSE_SLOW, FeedbackKeyword.HARD_TO_UNDERSTAND_THE_CODE)
        );
        ReceivedFeedbacks receivedFeedbacks = new ReceivedFeedbacks(developFeedbackKeywords, socialFeedbackKeywords);

        List<String> result = receivedFeedbacks.findTopFeedbackKeywords(3);

        assertThat(result).containsExactly("친절했어요");
    }

    @Test
    @DisplayName("긍정적인 피드백이 없다면 아무런 피드백도 반환하지 않는다.")
    void findTopFeedbackKeywords3() {
        List<List<FeedbackKeyword>> developFeedbackKeywords = List.of(
                List.of(FeedbackKeyword.UNKIND, FeedbackKeyword.BAD_AT_EXPLAINING)
        );
        List<List<FeedbackKeyword>> socialFeedbackKeywords = List.of(
                List.of(FeedbackKeyword.NOT_MAKE_CODE_FOR_THE_PURPOSE, FeedbackKeyword.RESPONSE_SLOW)
        );
        ReceivedFeedbacks receivedFeedbacks = new ReceivedFeedbacks(developFeedbackKeywords, socialFeedbackKeywords);

        List<String> result = receivedFeedbacks.findTopFeedbackKeywords(3);

        assertThat(result).isEmpty();
    }
}
