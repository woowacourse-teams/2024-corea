package corea.feedback.service;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.feedback.domain.SocialFeedback;
import corea.feedback.dto.SocialFeedbackRequest;
import corea.feedback.dto.SocialFeedbackResponse;
import corea.feedback.repository.SocialFeedbackRepository;
import corea.matching.domain.MatchResult;
import corea.matching.repository.MatchResultRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SocialFeedbackService {

    private static final Logger log = LogManager.getLogger(SocialFeedbackService.class);
    private final SocialFeedbackRepository socialFeedbackRepository;
    private final MatchResultRepository matchResultRepository;

    @Transactional
    public SocialFeedbackResponse create(long roomId, long deliverId, SocialFeedbackRequest request) {
        validateAlreadyExist(roomId, deliverId, request.receiverId());
        log.debug("소설 피드백 작성[작성자({}), 요청값({})",deliverId,request);
        SocialFeedback socialFeedback = socialFeedbackRepository.save(createEntity(roomId, deliverId, request));
        return SocialFeedbackResponse.of(socialFeedback);
    }

    @Transactional
    public SocialFeedbackResponse update(long feedbackId, long roomId, long deliverId, SocialFeedbackRequest request) {
        validateNotExist(feedbackId);

        log.debug("소설 피드백 업데이트[작성자({}), 피드백 ID({}), 요청값({})",deliverId,feedbackId,request);
        SocialFeedback socialFeedback = socialFeedbackRepository.save(createEntity(feedbackId, roomId, deliverId, request));
        return SocialFeedbackResponse.of(socialFeedback);
    }

    public SocialFeedbackResponse findSocialFeedback(long roomId, long deliverId, String username) {
        return SocialFeedbackResponse
                .of(socialFeedbackRepository.findByRoomIdAndDeliverIdAndReceiverUsername(roomId, deliverId, username)
                        .orElseThrow(() -> new CoreaException(ExceptionType.FEEDBACK_NOT_FOUND)));
    }

    private void validateNotExist(long feedbackId) {
        if (!socialFeedbackRepository.existsById(feedbackId)) {
            throw new CoreaException(ExceptionType.FEEDBACK_NOT_FOUND);
        }
    }

    private void validateAlreadyExist(long roomId, long deliverId, long receiverId) {
        if (socialFeedbackRepository.existsByRoomIdAndDeliverIdAndReceiverId(roomId, deliverId, receiverId)) {
            throw new CoreaException(ExceptionType.ALREADY_COMPLETED_FEEDBACK);
        }
    }

    private SocialFeedback createEntity(long feedbackId, long roomId, long deliverId, SocialFeedbackRequest request) {
        MatchResult matchResult = matchResultRepository.findByRoomIdAndReviewerIdAndRevieweeId(roomId, request.receiverId(), deliverId)
                .orElseThrow(() -> new CoreaException(ExceptionType.NOT_MATCHED_MEMBER));

        return request.toEntity(feedbackId, roomId, matchResult.getReviewee(), matchResult.getReviewer());
    }

    private SocialFeedback createEntity(long roomId, long deliverId, SocialFeedbackRequest request) {
        MatchResult matchResult = matchResultRepository.findByRoomIdAndReviewerIdAndRevieweeId(roomId, request.receiverId(), deliverId)
                .orElseThrow(() -> new CoreaException(ExceptionType.NOT_MATCHED_MEMBER));
        matchResult.revieweeCompleteFeedback();
        return request.toEntity(roomId, matchResult.getReviewee(), matchResult.getReviewer());
    }
}
