package corea.feedback.service;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.feedback.domain.DevelopFeedback;
import corea.feedback.dto.DevelopFeedbackRequest;
import corea.feedback.dto.DevelopFeedbackResponse;
import corea.feedback.repository.DevelopFeedbackRepository;
import corea.matching.domain.MatchResult;
import corea.matching.repository.MatchResultRepository;
import corea.member.domain.Profile;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DevelopFeedbackService {

    private static final Logger log = LogManager.getLogger(DevelopFeedbackService.class);

    private final MatchResultRepository matchResultRepository;
    private final DevelopFeedbackRepository developFeedbackRepository;

    @Transactional
    public DevelopFeedbackResponse create(long roomId, long deliverId, DevelopFeedbackRequest request) {
        validateAlreadyExist(roomId, deliverId, request.receiverId());
        log.debug("개발 피드백 작성[작성자({}), 요청값({})", deliverId, request);

        MatchResult matchResult = matchResultRepository.findByRoomIdAndReviewerIdAndRevieweeId(roomId, deliverId, request.receiverId())
                .orElseThrow(() -> new CoreaException(ExceptionType.NOT_MATCHED_MEMBER));
        matchResult.reviewerCompleteFeedback();

        DevelopFeedback feedback = saveDevelopFeedback(roomId, request, matchResult);
        Profile profile = feedback.getReceiverProfile();
        profile.updateProfile(request.evaluationPoint());

        return DevelopFeedbackResponse.from(feedback);
    }

    private void validateAlreadyExist(long roomId, long deliverId, long receiverId) {
        if (developFeedbackRepository.existsByRoomIdAndDeliverIdAndReceiverId(roomId, deliverId, receiverId)) {
            throw new CoreaException(ExceptionType.ALREADY_COMPLETED_FEEDBACK);
        }
    }

    private DevelopFeedback saveDevelopFeedback(long roomId, DevelopFeedbackRequest request, MatchResult matchResult) {
        DevelopFeedback feedback = request.toEntity(roomId, matchResult.getReviewer(), matchResult.getReviewee());
        return developFeedbackRepository.save(feedback);
    }

    @Transactional
    public DevelopFeedbackResponse update(long feedbackId, long deliverId, DevelopFeedbackRequest request) {
        log.debug("개발 피드백 업데이트[작성자({}), 피드백 ID({}), 요청값({})", deliverId, feedbackId, request);

        DevelopFeedback feedback = developFeedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new CoreaException(ExceptionType.FEEDBACK_NOT_FOUND));
        updateFeedback(feedback, request);

        return DevelopFeedbackResponse.from(feedback);
    }

    private void updateFeedback(DevelopFeedback feedback, DevelopFeedbackRequest request) {
        int preEvaluatePoint = feedback.getEvaluatePoint();
        feedback.update(request);

        Profile profile = feedback.getReceiverProfile();
        profile.updateProfile(preEvaluatePoint, request.evaluationPoint());
    }

    public DevelopFeedbackResponse findDevelopFeedback(long roomId, long deliverId, String username) {
        DevelopFeedback feedback = developFeedbackRepository.findByRoomIdAndDeliverIdAndReceiverUsername(roomId, deliverId, username)
                .orElseThrow(() -> new CoreaException(ExceptionType.FEEDBACK_NOT_FOUND));

        return DevelopFeedbackResponse.from(feedback);
    }
}
