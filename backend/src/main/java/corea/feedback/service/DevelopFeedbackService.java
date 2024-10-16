package corea.feedback.service;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.feedback.domain.DevelopFeedback;
import corea.feedback.domain.DevelopFeedbackReader;
import corea.feedback.domain.DevelopFeedbackWriter;
import corea.feedback.dto.DevelopFeedbackRequest;
import corea.feedback.dto.DevelopFeedbackResponse;
import corea.feedback.dto.DevelopFeedbackUpdateInput;
import corea.feedback.repository.DevelopFeedbackRepository;
import corea.matchresult.domain.MatchResult;
import corea.matchresult.domain.MatchResultWriter;
import corea.matchresult.repository.MatchResultRepository;
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

    private final DevelopFeedbackReader developFeedbackReader;
    private final DevelopFeedbackWriter developFeedbackWriter;
    private final MatchResultWriter matchResultWriter;

    @Transactional
    public DevelopFeedbackResponse create(long roomId, long deliverId, DevelopFeedbackRequest request) {
        MatchResult matchResult = matchResultWriter.completeDevelopFeedback(roomId, deliverId, request.receiverId());

        DevelopFeedback feedback = request.toEntity(roomId, matchResult.getReviewer(), matchResult.getReviewee());
        DevelopFeedback createdFeedback = developFeedbackWriter.create(feedback, roomId, deliverId, request.receiverId());
        return DevelopFeedbackResponse.from(createdFeedback);
    }

    @Transactional
    public DevelopFeedbackResponse update(long feedbackId, long deliverId, DevelopFeedbackRequest request) {
        DevelopFeedback developFeedback = developFeedbackReader.findById(feedbackId);

        DevelopFeedbackUpdateInput input = DevelopFeedbackUpdateInput.from(request);
        developFeedbackWriter.update(developFeedback, deliverId, input);

        return DevelopFeedbackResponse.from(developFeedback);
    }

    public DevelopFeedbackResponse findDevelopFeedback(long roomId, long deliverId, String username) {
        DevelopFeedback feedback = developFeedbackRepository.findByRoomIdAndDeliverIdAndReceiverUsername(roomId, deliverId, username)
                .orElseThrow(() -> new CoreaException(ExceptionType.FEEDBACK_NOT_FOUND));

        return DevelopFeedbackResponse.from(feedback);
    }
}
