package corea.feedback.domain;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.feedback.dto.FeedbackResponse;
import corea.feedback.repository.DevelopFeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DevelopFeedbackReader {

    private final DevelopFeedbackRepository developFeedbackRepository;

    public Map<Long, List<FeedbackResponse>> findDeliveredDevelopFeedback(long feedbackDeliverId) {
        return developFeedbackRepository.findByDeliverId(feedbackDeliverId)
                .stream()
                .map(FeedbackResponse::fromDeliver)
                .collect(Collectors.groupingBy(FeedbackResponse::roomId));
    }

    public Map<Long, List<FeedbackResponse>> findReceivedDevelopFeedback(long feedbackReceiverId) {
        return developFeedbackRepository.findByReceiverId(feedbackReceiverId)
                .stream()
                .map(FeedbackResponse::fromReceiver)
                .collect(Collectors.groupingBy(FeedbackResponse::roomId));
    }

    public DevelopFeedback findById(long feedbackId) {
        return developFeedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new CoreaException(ExceptionType.FEEDBACK_NOT_FOUND));
    }

    public DevelopFeedback findDevelopFeedback(long roomId, long deliverId, String username) {
        return developFeedbackRepository.findByRoomIdAndDeliverIdAndReceiverUsername(roomId, deliverId, username)
                .orElseThrow(() -> new CoreaException(ExceptionType.FEEDBACK_NOT_FOUND));
    }
}
