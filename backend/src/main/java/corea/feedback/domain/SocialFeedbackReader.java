package corea.feedback.domain;

import corea.feedback.dto.FeedbackResponse;
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

    public Map<Long, List<FeedbackResponse>> findDeliveredSocialFeedback(long feedbackDeliverId) {
        return socialFeedbackRepository.findByDeliverId(feedbackDeliverId)
                .stream()
                .map(FeedbackResponse::fromDeliver)
                .collect(Collectors.groupingBy(FeedbackResponse::roomId));
    }

    public Map<Long, List<FeedbackResponse>> findReceivedSocialFeedback(long feedbackReceiverId) {
        return socialFeedbackRepository.findByReceiverId(feedbackReceiverId)
                .stream()
                .map(FeedbackResponse::fromReceiver)
                .collect(Collectors.groupingBy(FeedbackResponse::roomId));
    }
}
