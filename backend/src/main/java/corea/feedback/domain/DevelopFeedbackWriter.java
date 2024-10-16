package corea.feedback.domain;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.feedback.dto.DevelopFeedbackUpdateInput;
import corea.feedback.repository.DevelopFeedbackRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
public class DevelopFeedbackWriter {

    private final DevelopFeedbackRepository developFeedbackRepository;

    public DevelopFeedback create(DevelopFeedback developFeedback, long roomId, long deliverId, long receiverId) {
        validateAlreadyExist(roomId, deliverId, receiverId);
        log.info("개발 피드백 작성 [방 ID={}, 작성자 ID={}, 수신자 ID={}]", roomId, deliverId, receiverId);

        return developFeedbackRepository.save(developFeedback);
    }

    private void validateAlreadyExist(long roomId, long deliverId, long receiverId) {
        if (developFeedbackRepository.existsByRoomIdAndDeliverIdAndReceiverId(roomId, deliverId, receiverId)) {
            throw new CoreaException(ExceptionType.ALREADY_COMPLETED_FEEDBACK);
        }
    }

    public void update(DevelopFeedback developFeedback, long deliverId, DevelopFeedbackUpdateInput input) {
        System.out.println("deliverId = " + deliverId);
        System.out.println("input = " + input);

        log.info("개발 피드백 업데이트 [피드백 ID={}, 작성자 ID={}, 요청값={}]", developFeedback.getId(), developFeedback, input);
        validateUpdateAuthority(developFeedback, deliverId);

        developFeedback.update(
                input.evaluationPoint(),
                input.feedbackKeywords(),
                input.feedbackText(),
                input.recommendationPoint()
        );
    }

    private void validateUpdateAuthority(DevelopFeedback developFeedback, long deliverId) {
        if (developFeedback.isNotMatchingDeliver(deliverId)) {
            throw new CoreaException(ExceptionType.FEEDBACK_UPDATE_AUTHORIZATION_ERROR);
        }
    }
}
