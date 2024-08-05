package corea.feedback.service;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.feedback.dto.FeedbackResponse;
import corea.feedback.dto.FeedbacksResponse;
import corea.feedback.dto.UserFeedbackResponse;
import corea.feedback.repository.SocialFeedbackRepository;
import corea.feedback.repository.DevelopFeedbackRepository;
import corea.room.domain.Room;
import corea.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static corea.global.util.MapHandler.extractDistinctKeyStreams;
import static corea.global.util.NullHandler.emptyIfNull;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserFeedbackService {

    private final RoomRepository roomRepository;
    private final DevelopFeedbackRepository developFeedbackRepository;
    private final SocialFeedbackRepository socialFeedbackRepository;

    public UserFeedbackResponse getDeliveredFeedback(long id) {
        Map<Long, List<FeedbackResponse>> reviewerToRevieweeMap = getDeliveredReviewerToRevieweeFeedback(id);
        Map<Long, List<FeedbackResponse>> revieweeToReviewerMap = getDeliveredRevieweeToReviewerFeedback(id);
        return new UserFeedbackResponse(
                extractDistinctKeyStreams(reviewerToRevieweeMap, revieweeToReviewerMap)
                        .map(key -> FeedbacksResponse.of(getRoom(key), emptyIfNull(reviewerToRevieweeMap.get(key)), emptyIfNull(revieweeToReviewerMap.get(key))))
                        .toList());
    }

    private Map<Long, List<FeedbackResponse>> getDeliveredReviewerToRevieweeFeedback(long id) {
        return developFeedbackRepository.findByDeliverId(id)
                .stream()
                .map(FeedbackResponse::from)
                .collect(Collectors.groupingBy(FeedbackResponse::roomId));
    }

    private Map<Long, List<FeedbackResponse>> getDeliveredRevieweeToReviewerFeedback(long id) {
        return socialFeedbackRepository.findByDeliverId(id)
                .stream()
                .map(FeedbackResponse::from)
                .collect(Collectors.groupingBy(FeedbackResponse::roomId));
    }

    public UserFeedbackResponse getReceivedFeedback(long id) {
        Map<Long, List<FeedbackResponse>> reviewerToRevieweeMap = getReceivedReviewerToRevieweeFeedback(id);
        Map<Long, List<FeedbackResponse>> revieweeToReviewerMap = getReceivedRevieweeToReviewerFeedback(id);

        return new UserFeedbackResponse(
                extractDistinctKeyStreams(reviewerToRevieweeMap, revieweeToReviewerMap)
                        .map(key -> FeedbacksResponse.of(getRoom(key), emptyIfNull(reviewerToRevieweeMap.get(key)), emptyIfNull(revieweeToReviewerMap.get(key))))
                        .toList());
    }

    private Map<Long, List<FeedbackResponse>> getReceivedReviewerToRevieweeFeedback(long id) {
        return developFeedbackRepository.findByReceiverId(id)
                .stream()
                .map(FeedbackResponse::from)
                .collect(Collectors.groupingBy(FeedbackResponse::roomId));
    }

    private Map<Long, List<FeedbackResponse>> getReceivedRevieweeToReviewerFeedback(long id) {
        return socialFeedbackRepository.findByReceiverId(id)
                .stream()
                .map(FeedbackResponse::from)
                .collect(Collectors.groupingBy(FeedbackResponse::roomId));
    }

    private Room getRoom(final long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new CoreaException(ExceptionType.ROOM_NOT_FOUND));
    }
}
