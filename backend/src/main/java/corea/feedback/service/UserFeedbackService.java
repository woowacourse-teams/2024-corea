package corea.feedback.service;

import corea.exception.CoreaException;
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

import static corea.exception.ExceptionType.ROOM_NOT_FOUND;
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
        return getUserFeedbackResponse(deliveredDevelopFeedback, deliverSocialFeedback);
    }

    private Map<Long, List<FeedbackResponse>> getDeliveredDevelopFeedback(long id) {
        return developFeedbackRepository.findByDeliverId(id)
                .stream()
                .map(FeedbackResponse::fromDeliver)
                .collect(Collectors.groupingBy(FeedbackResponse::roomId));
    }

    private Map<Long, List<FeedbackResponse>> getDeliveredSocialFeedback(long id) {
        return socialFeedbackRepository.findByDeliverId(id)
                .stream()
                .map(FeedbackResponse::fromDeliver)
                .collect(Collectors.groupingBy(FeedbackResponse::roomId));
    }

    public UserFeedbackResponse getReceivedFeedback(long id) {
        Map<Long, List<FeedbackResponse>> receivedDevelopFeedback = getReceivedDevelopFeedback(id);
        Map<Long, List<FeedbackResponse>> receivedSocialFeedback = getReceivedSocialFeedback(id);
        return getUserFeedbackResponse(receivedDevelopFeedback, receivedSocialFeedback);
    }

    private Map<Long, List<FeedbackResponse>> getReceivedDevelopFeedback(long id) {
        return developFeedbackRepository.findByReceiverId(id)
                .stream()
                .map(FeedbackResponse::fromReceiver)
                .collect(Collectors.groupingBy(FeedbackResponse::roomId));
    }

    private Map<Long, List<FeedbackResponse>> getReceivedSocialFeedback(long id) {
        return socialFeedbackRepository.findByReceiverId(id)
                .stream()
                .map(FeedbackResponse::fromReceiver)
                .collect(Collectors.groupingBy(FeedbackResponse::roomId));
    }

    private UserFeedbackResponse getUserFeedbackResponse(Map<Long, List<FeedbackResponse>> developFeedback, Map<Long, List<FeedbackResponse>> socialFeedback) {
        List<Room> rooms = roomRepository.findAllById(
                extractDistinctKeyStreams(developFeedback, socialFeedback).toList());
        if (rooms.isEmpty()) {
            throw new CoreaException(ROOM_NOT_FOUND);
        }
        return new UserFeedbackResponse(rooms.stream()
                .filter(Room::isClosed)
                .map(room -> FeedbacksResponse.of(room, emptyListIfNull(developFeedback.get(room.getId())), emptyListIfNull(socialFeedback.get(room.getId()))))
                .toList());
    }
}
