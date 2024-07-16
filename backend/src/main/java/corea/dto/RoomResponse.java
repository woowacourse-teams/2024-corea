package corea.dto;

import corea.domain.Room;

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

    public static RoomResponse of(final Room room, final String author) {
        return new RoomResponse(
                room.getId(),
                room.getTitle(),
                room.getContent(),
                author,
                room.getRepositoryLink(),
                room.getThumbnailLink(),
                room.getMatchingSize(),
                List.of(room.getKeyword()),
                room.getCurrentParticipantsSize(),
                room.getLimitedParticipantsSize(),
                room.getSubmissionDeadline(),
                room.getReviewDeadline()
        );
    }
}
