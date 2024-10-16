package corea.feedback.service;

import corea.feedback.domain.DevelopFeedbackReader;
import corea.feedback.domain.SocialFeedbackReader;
import corea.feedback.dto.FeedbackResponse;
import corea.feedback.dto.FeedbacksResponse;
import corea.feedback.dto.UserFeedbackResponse;
import corea.room.domain.Room;
import corea.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import static corea.global.util.MapHandler.extractDistinctKeyStreams;
import static corea.global.util.NullHandler.emptyListIfNull;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserFeedbackService {

    private final RoomRepository roomRepository;
    private final DevelopFeedbackReader developFeedbackReader;
    private final SocialFeedbackReader socialFeedbackReader;

    public UserFeedbackResponse getDeliveredFeedback(long feedbackDeliverId) {
        Map<Long, List<FeedbackResponse>> developFeedbacks = developFeedbackReader.findDeliveredDevelopFeedback(feedbackDeliverId);
        Map<Long, List<FeedbackResponse>> socialFeedbacks = socialFeedbackReader.findDeliveredSocialFeedback(feedbackDeliverId);

        return getUserFeedbackResponse(developFeedbacks, socialFeedbacks, room -> true);
    }

    public UserFeedbackResponse getReceivedFeedback(long feedbackReceiverId) {
        Map<Long, List<FeedbackResponse>> developFeedback = developFeedbackReader.findReceivedDevelopFeedback(feedbackReceiverId);
        Map<Long, List<FeedbackResponse>> socialFeedback = socialFeedbackReader.findReceivedSocialFeedback(feedbackReceiverId);

        return getUserFeedbackResponse(developFeedback, socialFeedback, Room::isClosed);
    }

    private UserFeedbackResponse getUserFeedbackResponse(Map<Long, List<FeedbackResponse>> developFeedback, Map<Long, List<FeedbackResponse>> socialFeedback, Predicate<Room> predicate) {
        List<Long> roomIds = extractDistinctKeyStreams(developFeedback, socialFeedback).toList();
        List<Room> rooms = roomRepository.findAllById(roomIds);

        return new UserFeedbackResponse(rooms.stream()
                .filter(predicate)
                .map(room -> FeedbacksResponse.of(room, emptyListIfNull(developFeedback.get(room.getId())), emptyListIfNull(socialFeedback.get(room.getId()))))
                .toList());
    }
}
