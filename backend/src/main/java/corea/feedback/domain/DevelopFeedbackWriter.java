package corea.feedback.domain;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
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
        log.info("개발 피드백 작성 - 방 ID={}, 작성자 ID={}, 수신자 ID={}", roomId, deliverId, receiverId);

        return developFeedbackRepository.save(developFeedback);
    }

    private void validateAlreadyExist(long roomId, long deliverId, long receiverId) {
        if (developFeedbackRepository.existsByRoomIdAndDeliverIdAndReceiverId(roomId, deliverId, receiverId)) {
            throw new CoreaException(ExceptionType.ALREADY_COMPLETED_FEEDBACK);
        }
    }
}
