package corea.feedback.dto;

import corea.room.domain.Room;

import java.util.List;

public record FeedbacksResponse(
        long roomId,
        String title,
        List<String> roomKeywords,
        boolean isClosed,
        List<FeedbackResponse> developFeedback,
        List<FeedbackResponse> socialFeedback

) {
    public static FeedbacksResponse of(Room room, List<FeedbackResponse> developFeedback, List<FeedbackResponse> socialFeedback) {
        return new FeedbacksResponse(
                room.getId(),
                room.getTitle(),
                room.getKeyword(),
                room.isClosed(),
                developFeedback,
                socialFeedback
        );
    }
}
