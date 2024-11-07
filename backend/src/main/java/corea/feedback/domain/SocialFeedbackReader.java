package corea.feedback.domain;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.feedback.dto.FeedbackOutput;
import corea.feedback.repository.SocialFeedbackRepository;
import corea.matchresult.domain.MatchResult;
import corea.matchresult.repository.MatchResultRepository;
import corea.member.domain.Member;
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
    private final MatchResultRepository matchResultRepository;

    public Map<Long, List<FeedbackOutput>> collectDeliverSocialFeedback(long feedbackDeliverId) {
        return socialFeedbackRepository.findAllByDeliverId(feedbackDeliverId)
                .stream()
                .map(socialFeedback -> FeedbackOutput.fromDeliver(socialFeedback, getMatchResult(socialFeedback).getReviewLink()))
                .collect(Collectors.groupingBy(FeedbackOutput::roomId));
    }

    public Map<Long, List<FeedbackOutput>> collectReceivedSocialFeedback(long feedbackReceiverId) {
        return socialFeedbackRepository.findAllByReceiverId(feedbackReceiverId)
                .stream()
                .map(socialFeedback -> FeedbackOutput.fromReceiver(socialFeedback, getMatchResult(socialFeedback).getPrLink()))
                .collect(Collectors.groupingBy(FeedbackOutput::roomId));
    }

    private MatchResult getMatchResult(SocialFeedback socialFeedback) {
        long roomId = socialFeedback.getRoomId();
        Member reviewee = socialFeedback.getDeliver();
        Member reviewer = socialFeedback.getReceiver();

        return matchResultRepository.findByRoomIdAndReviewerAndReviewee(roomId, reviewer, reviewee)
                .orElseThrow(() -> new CoreaException(ExceptionType.NOT_MATCHED_MEMBER));
    }

    public SocialFeedback findById(long feedbackId) {
        return socialFeedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new CoreaException(ExceptionType.FEEDBACK_NOT_FOUND));
    }

    public SocialFeedback findSocialFeedback(long roomId, long deliverId, String username) {
        return socialFeedbackRepository.findByRoomIdAndDeliverIdAndReceiverUsername(roomId, deliverId, username)
                .orElseThrow(() -> new CoreaException(ExceptionType.FEEDBACK_NOT_FOUND));
    }

    public boolean existsByRoomIdAndDeliverAndReceiver(long rooomId, long deliverId, long receiverId) {
        return socialFeedbackRepository.existsByRoomIdAndDeliverIdAndReceiverId(rooomId, deliverId, receiverId);
    }
}
