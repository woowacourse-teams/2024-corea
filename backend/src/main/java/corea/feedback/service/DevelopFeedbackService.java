package corea.feedback.service;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.feedback.domain.DevelopFeedback;
import corea.feedback.dto.DevelopFeedbackRequest;
import corea.feedback.dto.DevelopFeedbackResponse;
import corea.feedback.repository.DevelopFeedbackRepository;
import corea.matching.domain.MatchResult;
import corea.matching.repository.MatchResultRepository;
import corea.member.domain.ProfileCountType;
import corea.member.domain.Member;
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

        DevelopFeedback developFeedback = developFeedbackRepository.save(createEntity(roomId, deliverId, request));
        increaseFeedbackCount(developFeedback.getReceiver());

        return DevelopFeedbackResponse.of(developFeedback);
    }

    @Transactional
    public DevelopFeedbackResponse update(long feedbackId, long roomId, long deliverId, DevelopFeedbackRequest request) {
        validateNotExist(feedbackId);
        log.debug("개발 피드백 업데이트[작성자({}), 피드백 ID({}), 요청값({})", deliverId, feedbackId, request);

        DevelopFeedback developFeedback = developFeedbackRepository.save(createEntity(feedbackId, roomId, deliverId, request));
        return DevelopFeedbackResponse.of(developFeedback);
    }

    public DevelopFeedbackResponse findDevelopFeedback(long roomId, long deliverId, String username) {
        return DevelopFeedbackResponse
                .of(developFeedbackRepository.findByRoomIdAndDeliverIdAndReceiverUsername(roomId, deliverId, username)
                        .orElseThrow(() -> new CoreaException(ExceptionType.FEEDBACK_NOT_FOUND)));
    }

    private void validateNotExist(long feedbackId) {
        if (!developFeedbackRepository.existsById(feedbackId)) {
            throw new CoreaException(ExceptionType.FEEDBACK_NOT_FOUND);
        }
    }

    private void validateAlreadyExist(long roomId, long deliverId, long receiverId) {
        if (developFeedbackRepository.existsByRoomIdAndDeliverIdAndReceiverId(roomId, deliverId, receiverId)) {
            throw new CoreaException(ExceptionType.ALREADY_COMPLETED_FEEDBACK);
        }
    }

    private DevelopFeedback createEntity(long roomId, long deliverId, DevelopFeedbackRequest request) {
        MatchResult matchResult = matchResultRepository.findByRoomIdAndReviewerIdAndRevieweeId(roomId, deliverId, request.receiverId())
                .orElseThrow(() -> new CoreaException(ExceptionType.NOT_MATCHED_MEMBER));
        matchResult.reviewerCompleteFeedback();
        return request.toEntity(roomId, matchResult.getReviewer(), matchResult.getReviewee());
    }

    private DevelopFeedback createEntity(long feedbackId, long roomId, long deliverId, DevelopFeedbackRequest request) {
        MatchResult matchResult = matchResultRepository.findByRoomIdAndReviewerIdAndRevieweeId(roomId, deliverId, request.receiverId())
                .orElseThrow(() -> new CoreaException(ExceptionType.NOT_MATCHED_MEMBER));

        return request.toEntity(feedbackId, roomId, matchResult.getReviewer(), matchResult.getReviewee());
    }

    private void increaseFeedbackCount(Member receiver) {
        receiver.increaseCount(ProfileCountType.FEEDBACK);
    }
}
