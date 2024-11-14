package corea.feedback.domain;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.feedback.dto.DevelopFeedbackUpdateInput;
import corea.feedback.repository.DevelopFeedbackRepository;
import corea.member.domain.Member;
import corea.room.domain.Room;
import corea.room.domain.RoomReader;
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
    private final RoomReader roomReader;

    public DevelopFeedback create(DevelopFeedback developFeedback, long roomId, long deliverId, long receiverId) {
        validateAlreadyExist(roomId, deliverId, receiverId);
        log.info("개발 피드백 작성 [방 ID={}, 작성자 ID={}, 수신자 ID={}]", roomId, deliverId, receiverId);

        updateFeedbackPointIfRoomClosed(roomId, developFeedback);
        return developFeedbackRepository.save(developFeedback);
    }

    private void validateAlreadyExist(long roomId, long deliverId, long receiverId) {
        if (developFeedbackRepository.existsByRoomIdAndDeliverIdAndReceiverId(roomId, deliverId, receiverId)) {
            throw new CoreaException(ExceptionType.ALREADY_COMPLETED_FEEDBACK);
        }
    }

    private void updateFeedbackPointIfRoomClosed(long roomId, DevelopFeedback developFeedback) {
        Room room = roomReader.find(roomId);

        if (room.isClosed()) {
            updateDevelopFeedbackPoint(developFeedback);
        }
    }

    private void updateDevelopFeedbackPoint(DevelopFeedback developFeedback) {
        Member receiver = developFeedback.getReceiver();
        receiver.updateAverageRating(developFeedback.getEvaluatePoint());
    }

    public void update(DevelopFeedback developFeedback, long deliverId, DevelopFeedbackUpdateInput input) {
        validateUpdateAuthority(developFeedback, deliverId);
        log.info("개발 피드백 업데이트 [피드백 ID={}, 작성자 ID={}, 요청값={}]", developFeedback.getId(), deliverId, input);

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
