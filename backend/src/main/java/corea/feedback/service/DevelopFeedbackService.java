package corea.feedback.service;

import corea.alarm.service.AlarmService;
import corea.feedback.domain.DevelopFeedback;
import corea.feedback.domain.DevelopFeedbackReader;
import corea.feedback.domain.DevelopFeedbackWriter;
import corea.feedback.dto.DevelopFeedbackCreateRequest;
import corea.feedback.dto.DevelopFeedbackResponse;
import corea.feedback.dto.DevelopFeedbackUpdateInput;
import corea.feedback.dto.DevelopFeedbackUpdateRequest;
import corea.matchresult.domain.MatchResult;
import corea.matchresult.domain.MatchResultWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DevelopFeedbackService {

    private final AlarmService alarmService;
    private final DevelopFeedbackReader developFeedbackReader;
    private final DevelopFeedbackWriter developFeedbackWriter;
    private final MatchResultWriter matchResultWriter;
    private final FeedbackMapper feedbackMapper;

    @Transactional
    public DevelopFeedbackResponse create(long roomId, long deliverId, DevelopFeedbackCreateRequest request) {
        long receiverId = request.receiverId();

        MatchResult matchResult = matchResultWriter.completeDevelopFeedback(roomId, deliverId, receiverId);

        DevelopFeedback feedback = request.toEntity(roomId, matchResult.getReviewer(), matchResult.getReviewee());
        DevelopFeedback createdFeedback = developFeedbackWriter.create(feedback, roomId, deliverId, receiverId);

        alarmService.createFeedbackAlarm(deliverId, receiverId, roomId);

        return DevelopFeedbackResponse.from(createdFeedback);
    }

    @Transactional
    public DevelopFeedbackResponse update(long feedbackId, long deliverId, DevelopFeedbackUpdateRequest request) {
        DevelopFeedback developFeedback = developFeedbackReader.findById(feedbackId);

        DevelopFeedbackUpdateInput input = feedbackMapper.toFeedbackInput(request);
        developFeedbackWriter.update(developFeedback, deliverId, input);

        return DevelopFeedbackResponse.from(developFeedback);
    }

    public DevelopFeedbackResponse findDevelopFeedback(long roomId, long deliverId, String username) {
        DevelopFeedback developFeedback = developFeedbackReader.findDevelopFeedback(roomId, deliverId, username);
        return DevelopFeedbackResponse.from(developFeedback);
    }
}
