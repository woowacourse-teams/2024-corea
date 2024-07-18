package corea.room.dto;

import corea.room.domain.Room;

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

    //TODO 해당 객체를 사용한다면 반영
    public Room toEntity() {
        return null;
    }
}
