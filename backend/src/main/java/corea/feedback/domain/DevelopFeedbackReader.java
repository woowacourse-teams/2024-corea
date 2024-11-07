package corea.feedback.domain;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.feedback.dto.FeedbackOutput;
import corea.feedback.repository.DevelopFeedbackRepository;
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
public class DevelopFeedbackReader {

    private final DevelopFeedbackRepository developFeedbackRepository;
    private final MatchResultRepository matchResultRepository;

    public Map<Long, List<FeedbackOutput>> collectDeliverDevelopFeedback(long feedbackDeliverId) {
        return developFeedbackRepository.findAllByDeliverId(feedbackDeliverId)
                .stream()
                .map(developFeedback -> FeedbackOutput.fromDeliver(developFeedback, getMatchResult(developFeedback).getPrLink()))
                .collect(Collectors.groupingBy(FeedbackOutput::roomId));
    }

    public Map<Long, List<FeedbackOutput>> collectReceivedDevelopFeedback(long feedbackReceiverId) {
        return developFeedbackRepository.findAllByReceiverId(feedbackReceiverId)
                .stream()
                .map(developFeedback -> FeedbackOutput.fromReceiver(developFeedback, getMatchResult(developFeedback).getReviewLink()))
                .collect(Collectors.groupingBy(FeedbackOutput::roomId));
    }

    private MatchResult getMatchResult(DevelopFeedback developFeedback) {
        long roomId = developFeedback.getRoomId();
        Member reviewer = developFeedback.getDeliver();
        Member reviewee = developFeedback.getReceiver();

        return matchResultRepository.findByRoomIdAndReviewerAndReviewee(roomId, reviewer, reviewee)
                .orElseThrow(() -> new CoreaException(ExceptionType.NOT_MATCHED_MEMBER));
    }

    public DevelopFeedback findById(long feedbackId) {
        return developFeedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new CoreaException(ExceptionType.FEEDBACK_NOT_FOUND));
    }

    public DevelopFeedback findDevelopFeedback(long roomId, long deliverId, String username) {
        return developFeedbackRepository.findByRoomIdAndDeliverIdAndReceiverUsername(roomId, deliverId, username)
                .orElseThrow(() -> new CoreaException(ExceptionType.FEEDBACK_NOT_FOUND));
    }

    public boolean existsByRoomIdAndDeliverAndReceiver(long roomId, long deliverId, long receiverId) {
        return developFeedbackRepository.existsByRoomIdAndDeliverIdAndReceiverId(roomId, deliverId, receiverId);
    }
}
