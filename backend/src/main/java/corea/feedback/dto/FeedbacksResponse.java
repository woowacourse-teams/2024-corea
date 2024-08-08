package corea.feedback.dto;

import corea.room.domain.Room;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "개발 피드백 + 커뮤니케이션 피드백들의 작성 응답")
public record FeedbacksResponse(@Schema(description = "방 아이디", example = "1")
                                long roomId,

                                @Schema(description = "방 이름", example = "즐거운 코딩 테스트")
                                String title,

                                @Schema(description = "방 키워드", example = "[\"TDD\", \"클린코드\"]")
                                List<String> roomKeywords,

                                @Schema(description = "방 종료 여부", example = "true")
                                boolean isClosed,

                                @Schema(description = "개발 관련 피드백 리스트")
                                List<FeedbackResponse> developFeedback,

                                @Schema(description = "커뮤니케이션 관련 피드백 리스트")
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
