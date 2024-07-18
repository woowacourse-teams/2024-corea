package corea.room.dto;

import corea.room.domain.Room;

import java.time.LocalDateTime;
import java.util.List;

public record RoomResponse(
        long id,
        String title,
        String content,
        String author,
        String repositoryLink,
        String thumbnailLink,
        int matchingSize,
        List<String> keywords,
        long currentParticipants,
        long limitedParticipants,
        LocalDateTime recruitmentDeadline,
        LocalDateTime reviewDeadline
) {

    //TODO 해당 객체를 사용한다면 반영
    public static RoomResponse of(final Room room, final String author) {
        return null;
    }
}
