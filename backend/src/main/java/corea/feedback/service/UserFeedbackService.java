package corea.feedback.service;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.feedback.dto.FeedbackResponse;
import corea.feedback.dto.FeedbacksResponse;
import corea.feedback.dto.UserFeedbackResponse;
import corea.feedback.repository.DevelopFeedbackRepository;
import corea.feedback.repository.SocialFeedbackRepository;
import corea.room.domain.Room;
import corea.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static corea.global.util.MapHandler.extractDistinctKeyStreams;
import static corea.global.util.NullHandler.emptyListIfNull;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserFeedbackService {

    private final RoomRepository roomRepository;
    private final DevelopFeedbackRepository developFeedbackRepository;
    private final SocialFeedbackRepository socialFeedbackRepository;

    public UserFeedbackResponse getDeliveredFeedback(long id) {
        Map<Long, List<FeedbackResponse>> deliveredDevelopFeedback = getDeliveredDevelopFeedback(id);
        Map<Long, List<FeedbackResponse>> deliverSocialFeedback = getDeliveredSocialFeedback(id);
        return new UserFeedbackResponse(
                extractDistinctKeyStreams(deliveredDevelopFeedback, deliverSocialFeedback)
                        .map(key -> FeedbacksResponse.of(getRoom(key), emptyListIfNull(deliveredDevelopFeedback.get(key)), emptyListIfNull(deliverSocialFeedback.get(key))))
                        .toList());
    }

    private Map<Long, List<FeedbackResponse>> getDeliveredDevelopFeedback(long id) {
        return developFeedbackRepository.findByDeliverId(id)
                .stream()
                .map(FeedbackResponse::from)
                .collect(Collectors.groupingBy(FeedbackResponse::roomId));
    }

    private Map<Long, List<FeedbackResponse>> getDeliveredSocialFeedback(long id) {
        return socialFeedbackRepository.findByDeliverId(id)
                .stream()
                .map(FeedbackResponse::from)
                .collect(Collectors.groupingBy(FeedbackResponse::roomId));
    }

    public UserFeedbackResponse getReceivedFeedback(long id) {
        Map<Long, List<FeedbackResponse>> receivedDevelopFeedback = getReceivedDevelopFeedback(id);
        Map<Long, List<FeedbackResponse>> receivedSocialFeedback = getReceivedSocialFeedback(id);

        return new UserFeedbackResponse(
                extractDistinctKeyStreams(receivedDevelopFeedback, receivedSocialFeedback)
                        .map(key -> FeedbacksResponse.of(getRoom(key), emptyListIfNull(receivedDevelopFeedback.get(key)), emptyListIfNull(receivedSocialFeedback.get(key))))
                        .toList());
    }

    private Map<Long, List<FeedbackResponse>> getReceivedDevelopFeedback(long id) {
        return developFeedbackRepository.findByReceiverId(id)
                .stream()
                .map(FeedbackResponse::from)
                .collect(Collectors.groupingBy(FeedbackResponse::roomId));
    }

    private Map<Long, List<FeedbackResponse>> getReceivedSocialFeedback(long id) {
        return socialFeedbackRepository.findByReceiverId(id)
                .stream()
                .map(FeedbackResponse::from)
                .collect(Collectors.groupingBy(FeedbackResponse::roomId));
    }

    private Room getRoom(long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new CoreaException(ExceptionType.ROOM_NOT_FOUND));
    }
}
