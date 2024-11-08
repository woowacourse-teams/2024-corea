package corea.feedback.domain;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.feedback.dto.SocialFeedbackUpdateInput;
import corea.feedback.repository.SocialFeedbackRepository;
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
public class SocialFeedbackWriter {

    private final SocialFeedbackRepository socialFeedbackRepository;
    private final RoomReader roomReader;

    public SocialFeedback create(SocialFeedback socialFeedback, long roomId, long deliverId, long receiverId) {
        validateAlreadyExist(roomId, deliverId, receiverId);
        log.info("소셜 피드백 작성 [방 ID={}, 작성자 ID={}, 수신자 ID={}]", roomId, deliverId, receiverId);

        updateFeedbackPointIfRoomClosed(roomId, socialFeedback);
        return socialFeedbackRepository.save(socialFeedback);
    }

    private void validateAlreadyExist(long roomId, long deliverId, long receiverId) {
        if (socialFeedbackRepository.existsByRoomIdAndDeliverIdAndReceiverId(roomId, deliverId, receiverId)) {
            throw new CoreaException(ExceptionType.ALREADY_COMPLETED_FEEDBACK);
        }
    }

    private void updateFeedbackPointIfRoomClosed(long roomId, SocialFeedback socialFeedback) {
        Room room = roomReader.find(roomId);

        if (room.isClosed()) {
            updateSocialFeedbackPoint(socialFeedback);
        }
    }

    private void updateSocialFeedbackPoint(SocialFeedback socialFeedback) {
        Member receiver = socialFeedback.getReceiver();
        receiver.updateAverageRating(socialFeedback.getEvaluatePoint());
    }

    public void update(SocialFeedback socialFeedback, long deliverId, SocialFeedbackUpdateInput input) {
        validateUpdateAuthority(socialFeedback, deliverId);
        log.info("소셜 피드백 업데이트 [피드백 ID={}, 작성자 ID={}, 요청값={}]", socialFeedback.getId(), deliverId, input);

        socialFeedback.update(
                input.evaluationPoint(),
                input.feedbackKeywords(),
                input.feedbackText()
        );
    }

    private void validateUpdateAuthority(SocialFeedback socialFeedback, long deliverId) {
        if (socialFeedback.isNotMatchingDeliver(deliverId)) {
            throw new CoreaException(ExceptionType.FEEDBACK_UPDATE_AUTHORIZATION_ERROR);
        }
    }
}
