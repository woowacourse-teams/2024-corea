package corea.feedback.service;

import corea.feedback.domain.DevelopFeedbackReader;
import corea.feedback.domain.SocialFeedbackReader;
import corea.feedback.dto.FeedbackOutput;
import corea.feedback.dto.FeedbackResponse;
import corea.feedback.dto.FeedbacksResponse;
import corea.feedback.dto.UserFeedbackResponse;
import corea.room.domain.Room;
import corea.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.function.TriFunction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
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
    private final FeedbackMapper feedbackMapper;

    public UserFeedbackResponse getDeliveredFeedback(long feedbackDeliverId) {
        Map<Long, List<FeedbackOutput>> developFeedbackOutput = developFeedbackReader.collectDeliverDevelopFeedback(feedbackDeliverId);
        Map<Long, List<FeedbackOutput>> socialFeedbackOutput = socialFeedbackReader.collectDeliverSocialFeedback(feedbackDeliverId);

        return getUserFeedbackResponse(developFeedbackOutput, socialFeedbackOutput, Room::isNotOpened);
    }

    public UserFeedbackResponse getReceivedFeedback(long feedbackReceiverId) {
        Map<Long, List<FeedbackOutput>> developFeedbackOutput = developFeedbackReader.collectReceivedDevelopFeedback(feedbackReceiverId);
        Map<Long, List<FeedbackOutput>> socialFeedbackOutput = socialFeedbackReader.collectReceivedSocialFeedback(feedbackReceiverId);

        maskingIfNoFeedbackDeliver(feedbackReceiverId, developFeedbackOutput, socialFeedbackOutput);

        return getUserFeedbackResponse(developFeedbackOutput, socialFeedbackOutput, Room::isClosed);
    }

    private UserFeedbackResponse getUserFeedbackResponse(Map<Long, List<FeedbackOutput>> developFeedbackOutput, Map<Long, List<FeedbackOutput>> socialFeedbackOutput, Predicate<Room> roomStatusPredicate) {
        Map<Long, List<FeedbackResponse>> developFeedbacks = feedbackMapper.toFeedbackResponseMap(developFeedbackOutput);
        Map<Long, List<FeedbackResponse>> socialFeedbacks = feedbackMapper.toFeedbackResponseMap(socialFeedbackOutput);

        List<FeedbacksResponse> feedbacksResponses = getFeedbacksResponses(developFeedbacks, socialFeedbacks, roomStatusPredicate);
        return new UserFeedbackResponse(feedbacksResponses);
    }

    private void maskingIfNoFeedbackDeliver(long receiverId, Map<Long, List<FeedbackOutput>> developFeedbackOutput, Map<Long, List<FeedbackOutput>> socialFeedbackOutput) {
        developFeedbackOutput.forEach((key, value) -> developFeedbackOutput.put(key, maskingFeedback(receiverId, value, true)));
        socialFeedbackOutput.forEach((key, value) -> socialFeedbackOutput.put(key, maskingFeedback(receiverId, value, false)));
    }

    private List<FeedbackOutput> maskingFeedback(long receiverId, List<FeedbackOutput> feedbackOutputs, boolean needToCheckSocialFeedback) {

        TriFunction<Long, Long, Long, Boolean> feedbackExistencePredicate = needToCheckSocialFeedback ? socialFeedbackReader::existsByRoomIdAndDeliverAndReceiver : developFeedbackReader::existsByRoomIdAndDeliverAndReceiver;
        return feedbackOutputs.stream()
                .map(feedbackOutput -> {
                    if (needToMasking(receiverId, feedbackOutput, feedbackExistencePredicate)) {
                        return FeedbackOutput.masking(feedbackOutput);
                    }
                    return feedbackOutput;
                })
                .toList();
    }

    private boolean needToMasking(long receiverId, FeedbackOutput feedbackResponse, TriFunction<Long, Long, Long, Boolean> feedbackExistencePredicate) {
        long roomId = feedbackResponse.roomId();
        long deliverId = feedbackResponse.receiverId();
        return !feedbackExistencePredicate.apply(roomId, receiverId, deliverId);
    }

    private List<FeedbacksResponse> getFeedbacksResponses(Map<Long, List<FeedbackResponse>> developFeedbacks, Map<Long, List<FeedbackResponse>> socialFeedbacks, Predicate<Room> roomStatusPredicate) {
        List<Long> roomIds = extractDistinctKeyStreams(developFeedbacks, socialFeedbacks).toList();
        List<Room> rooms = roomRepository.findAllById(roomIds);

        return rooms.stream()
                .filter(roomStatusPredicate)
                .sorted(Comparator.comparing(Room::getId).reversed())
                .map(room -> FeedbacksResponse.of(room, emptyListIfNull(developFeedbacks.get(room.getId())), emptyListIfNull(socialFeedbacks.get(room.getId()))))
                .toList();
    }
}
