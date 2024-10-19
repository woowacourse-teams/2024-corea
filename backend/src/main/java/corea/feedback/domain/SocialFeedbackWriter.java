package corea.feedback.domain;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.feedback.repository.SocialFeedbackRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
public class SocialFeedbackWriter {

    private final SocialFeedbackRepository socialFeedbackRepository;

    public SocialFeedback create(SocialFeedback socialFeedback, long roomId, long deliverId, long receiverId) {
        validateAlreadyExist(roomId, deliverId, receiverId);
        log.info("소셜 피드백 작성 [방 ID={}, 작성자 ID={}, 수신자 ID={}]", roomId, deliverId, receiverId);

        return socialFeedbackRepository.save(socialFeedback);
    }

    private void validateAlreadyExist(long roomId, long deliverId, long receiverId) {
        if (socialFeedbackRepository.existsByRoomIdAndDeliverIdAndReceiverId(roomId, deliverId, receiverId)) {
            throw new CoreaException(ExceptionType.ALREADY_COMPLETED_FEEDBACK);
        }
    }
}
