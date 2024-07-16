package corea.dto;

import corea.domain.Room;

import java.time.LocalDateTime;

public record RoomCreateRequest(
        String title,
        String content,
        long memberId,
        String repositoryLink,
        String thumbnailLink,
        int matchingSize,
        String keyword,
        long currentParticipantsSize,
        long limitedParticipantsSize,
        LocalDateTime submissionDeadline,
        LocalDateTime reviewDeadline
) {

    public Room toEntity() {
        return new Room(
                title,
                content,
                memberId,
                repositoryLink,
                thumbnailLink,
                matchingSize,
                keyword,
                currentParticipantsSize,
                limitedParticipantsSize,
                submissionDeadline,
                reviewDeadline
        );
    }
}
