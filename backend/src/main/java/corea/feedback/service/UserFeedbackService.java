package corea.feedback.service;

import corea.feedback.domain.DevelopFeedbackReader;
import corea.feedback.domain.SocialFeedbackReader;
import corea.feedback.dto.FeedbackOutput;
import corea.feedback.dto.FeedbackResponse;
import corea.feedback.dto.FeedbacksResponse;
import corea.feedback.dto.UserFeedbackResponse;
import corea.feedback.util.FeedbackMapper;
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

    // TODO: RoomReader로 분리 -> 현재 조이썬 작업중이라 충돌 방지로 안함
    private final RoomRepository roomRepository;
    private final DevelopFeedbackReader developFeedbackReader;
    private final SocialFeedbackReader socialFeedbackReader;

    public UserFeedbackResponse getDeliveredFeedback(long feedbackDeliverId) {
        Map<Long, List<FeedbackOutput>> developFeedbackOutput = developFeedbackReader.collectDeliverDevelopFeedback(feedbackDeliverId);
        Map<Long, List<FeedbackOutput>> socialFeedbackOutput = socialFeedbackReader.collectDeliverSocialFeedback(feedbackDeliverId);

        return getUserFeedbackResponse(developFeedbackOutput, socialFeedbackOutput, room -> true);
    }

    public UserFeedbackResponse getReceivedFeedback(long feedbackReceiverId) {
        Map<Long, List<FeedbackOutput>> developFeedbackOutput = developFeedbackReader.collectReceivedDevelopFeedback(feedbackReceiverId);
        Map<Long, List<FeedbackOutput>> socialFeedbackOutput = socialFeedbackReader.collectReceivedSocialFeedback(feedbackReceiverId);

        return getUserFeedbackResponse(developFeedbackOutput, socialFeedbackOutput, Room::isClosed);
    }

    private UserFeedbackResponse getUserFeedbackResponse(Map<Long, List<FeedbackOutput>> developFeedbackOutput, Map<Long, List<FeedbackOutput>> socialFeedbackOutput, Predicate<Room> predicate) {
        Map<Long, List<FeedbackResponse>> developFeedbacks = FeedbackMapper.toFeedbackResponseMap(developFeedbackOutput);
        Map<Long, List<FeedbackResponse>> socialFeedbacks = FeedbackMapper.toFeedbackResponseMap(socialFeedbackOutput);

        List<FeedbacksResponse> feedbacksResponses = getFeedbacksResponses(developFeedbacks, socialFeedbacks, predicate);
        return new UserFeedbackResponse(feedbacksResponses);
    }

    private List<FeedbacksResponse> getFeedbacksResponses(Map<Long, List<FeedbackResponse>> developFeedbacks, Map<Long, List<FeedbackResponse>> socialFeedbacks, Predicate<Room> predicate) {
        List<Long> roomIds = extractDistinctKeyStreams(developFeedbacks, socialFeedbacks).toList();
        List<Room> rooms = roomRepository.findAllById(roomIds);

        return rooms.stream()
                .filter(predicate)
                .map(room -> FeedbacksResponse.of(room, emptyListIfNull(developFeedbacks.get(room.getId())), emptyListIfNull(socialFeedbacks.get(room.getId()))))
                .toList();
    }
}
