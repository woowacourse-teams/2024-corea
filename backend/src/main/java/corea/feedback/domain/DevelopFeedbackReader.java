package corea.feedback.domain;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.feedback.dto.FeedbackOutput;
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

    public Map<Long, List<FeedbackOutput>> collectDeliverDevelopFeedback(long feedbackDeliverId) {
        return developFeedbackRepository.findAllByDeliverId(feedbackDeliverId)
                .stream()
                .map(FeedbackOutput::fromDeliver)
                .collect(Collectors.groupingBy(FeedbackOutput::roomId));
    }

    public Map<Long, List<FeedbackOutput>> collectReceivedDevelopFeedback(long feedbackReceiverId) {
        return developFeedbackRepository.findAllByReceiverId(feedbackReceiverId)
                .stream()
                .map(FeedbackOutput::fromReceiver)
                .collect(Collectors.groupingBy(FeedbackOutput::roomId));
    }

    public DevelopFeedback findById(long feedbackId) {
        return developFeedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new CoreaException(ExceptionType.FEEDBACK_NOT_FOUND));
    }

    public DevelopFeedback findDevelopFeedback(long roomId, long deliverId, String username) {
        return developFeedbackRepository.findByRoomIdAndDeliverIdAndReceiverUsername(roomId, deliverId, username)
                .orElseThrow(() -> new CoreaException(ExceptionType.FEEDBACK_NOT_FOUND));
    }

    public boolean existsByDeliverAndReceiver(long deliverId, long receiverId) {
        return developFeedbackRepository.existsByDeliverIdAndReceiverId(deliverId, receiverId);
    }
}
