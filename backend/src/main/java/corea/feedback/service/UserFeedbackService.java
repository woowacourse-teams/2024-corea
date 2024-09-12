package corea.feedback.service;

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

    public UserFeedbackResponse getDeliveredFeedback(long feedbackDeliverId) {
        Map<Long, List<FeedbackResponse>> deliveredDevelopFeedback = getDeliveredDevelopFeedback(feedbackDeliverId);
        Map<Long, List<FeedbackResponse>> deliverSocialFeedback = getDeliveredSocialFeedback(feedbackDeliverId);
        return getUserFeedbackResponse(deliveredDevelopFeedback, deliverSocialFeedback);
    }

    private Map<Long, List<FeedbackResponse>> getDeliveredDevelopFeedback(long feedbackDeliverId) {
        return developFeedbackRepository.findByDeliverId(feedbackDeliverId)
                .stream()
                .map(FeedbackResponse::fromDeliver)
                .collect(Collectors.groupingBy(FeedbackResponse::roomId));
    }

    private Map<Long, List<FeedbackResponse>> getDeliveredSocialFeedback(long feedbackDeliverId) {
        return socialFeedbackRepository.findByDeliverId(feedbackDeliverId)
                .stream()
                .map(FeedbackResponse::fromDeliver)
                .collect(Collectors.groupingBy(FeedbackResponse::roomId));
    }

    public UserFeedbackResponse getReceivedFeedback(long feedbackReceiverId) {
        Map<Long, List<FeedbackResponse>> receivedDevelopFeedback = getReceivedDevelopFeedback(feedbackReceiverId);
        Map<Long, List<FeedbackResponse>> receivedSocialFeedback = getReceivedSocialFeedback(feedbackReceiverId);
        return getUserFeedbackResponse(receivedDevelopFeedback, receivedSocialFeedback);
    }

    private Map<Long, List<FeedbackResponse>> getReceivedDevelopFeedback(long feedbackReceiverId) {
        return developFeedbackRepository.findByReceiverId(feedbackReceiverId)
                .stream()
                .map(FeedbackResponse::fromReceiver)
                .collect(Collectors.groupingBy(FeedbackResponse::roomId));
    }

    private Map<Long, List<FeedbackResponse>> getReceivedSocialFeedback(long feedbackReceiverId) {
        return socialFeedbackRepository.findByReceiverId(feedbackReceiverId)
                .stream()
                .map(FeedbackResponse::fromReceiver)
                .collect(Collectors.groupingBy(FeedbackResponse::roomId));
    }

    private UserFeedbackResponse getUserFeedbackResponse(Map<Long, List<FeedbackResponse>> developFeedback, Map<Long, List<FeedbackResponse>> socialFeedback) {
        List<Room> rooms = roomRepository.findAllById(
                extractDistinctKeyStreams(developFeedback, socialFeedback).toList());
        return new UserFeedbackResponse(rooms.stream()
                .filter(Room::isClosed)
                .map(room -> FeedbacksResponse.of(room, emptyListIfNull(developFeedback.get(room.getId())), emptyListIfNull(socialFeedback.get(room.getId()))))
                .toList());
    }
}
