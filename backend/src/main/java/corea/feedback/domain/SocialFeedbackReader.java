package corea.feedback.domain;

import corea.feedback.dto.FeedbackOutput;
import corea.feedback.repository.SocialFeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SocialFeedbackReader {

    private final SocialFeedbackRepository socialFeedbackRepository;

    public Map<Long, List<FeedbackOutput>> collectDeliverSocialFeedback(long feedbackDeliverId) {
        return socialFeedbackRepository.findAllByDeliverId(feedbackDeliverId)
                .stream()
                .map(FeedbackOutput::fromDeliver)
                .collect(Collectors.groupingBy(FeedbackOutput::roomId));
    }

    public Map<Long, List<FeedbackOutput>> collectReceivedSocialFeedback(long feedbackReceiverId) {
        return socialFeedbackRepository.findAllByReceiverId(feedbackReceiverId)
                .stream()
                .map(FeedbackOutput::fromReceiver)
                .collect(Collectors.groupingBy(FeedbackOutput::roomId));
    }

    public boolean exist(long deliverId, long receiverId) {
        return socialFeedbackRepository.existsByDeliverIdAndReceiverId(deliverId, receiverId);
    }
}
