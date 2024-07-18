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

    public static RoomResponse of(final Room room) {
        return new RoomResponse(
                room.getId(), room.getTitle(), room.getContent(), room.getManager().getEmail(),
                room.getRepositoryLink(), room.getThumbnailLink(), room.getMatchingSize(), List.of(room.getKeyword()),
                room.getCurrentParticipantsSize(), room.getLimitedParticipantsSize(), room.getRecruitmentDeadline(), room.getReviewDeadline()
        );
    }
}
