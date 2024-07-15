package corea.dto;

import corea.domain.Room;

import java.time.LocalDateTime;

public record RoomResponse(
        long id,
        String title,
        String memberEmail,
        String repositoryLink,
        String thumbnailLink,
        int matchingSize,
        String keyword,
        LocalDateTime submissionDeadline,
        LocalDateTime reviewDeadline
) {

    public static RoomResponse from(final Room room, final String memberEmail) {
        return new RoomResponse(
                room.getId(),
                room.getTitle(),
                memberEmail,
                room.getRepositoryLink(),
                room.getThumbnailLink(),
                room.getMatchingSize(),
                room.getKeyword(),
                room.getSubmissionDeadline(),
                room.getReviewDeadline()
        );
    }
}
