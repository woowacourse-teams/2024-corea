package corea.dto;

import corea.domain.Room;

import java.time.LocalDateTime;

public record RoomCreateRequest(
        String title,
        long memberId,
        String repositoryLink,
        String thumbnailLink,
        int matchingSize,
        String keyword,
        LocalDateTime submissionDeadline,
        LocalDateTime reviewDeadline
) {

    public Room toEntity() {
        return new Room(
                title,
                memberId,
                repositoryLink,
                thumbnailLink,
                matchingSize,
                keyword,
                submissionDeadline,
                reviewDeadline
        );
    }
}
