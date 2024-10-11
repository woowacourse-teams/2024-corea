package corea.matchresult.view;

import corea.exception.ExceptionType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MatchingFailedMessageClassifierTest {

    @Test
    @DisplayName("매칭 진행 중 발생한 예외를 통해 예외 반환 메세지를 얻을 수 있다.")
    void classifyFailureMessage() {
        String message = MatchingFailedMessageClassifier.classifyFailureMessage(ExceptionType.PARTICIPANT_SIZE_LACK_DUE_TO_PULL_REQUEST);

        assertThat(message).isEqualTo("참가자의 수는 최소 인원을 충족하였지만, 일부 참가자가 pull request를 제출하지 않아 매칭을 진행하기에 참가자가 부족합니다.");
    }

    @Test
    @DisplayName("지정되어 있는 예외가 아닐 경우 기본 메세지를 얻을 수 있다.")
    void getDefaultMatchingFailedMessage() {
        String message = MatchingFailedMessageClassifier.classifyFailureMessage(ExceptionType.ALREADY_COMPLETED_FEEDBACK);

        assertThat(message).isEqualTo("매칭 과정에서 오류가 발생하여 매칭을 완료할 수 없습니다. 문제가 지속될 경우 관리자에게 문의하세요.");
    }
}
