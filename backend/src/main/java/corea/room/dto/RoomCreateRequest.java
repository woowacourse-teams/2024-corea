package corea.room.dto;

import corea.member.domain.Member;
import corea.room.domain.Classification;
import corea.room.domain.Room;
import corea.room.domain.RoomStatus;

import java.time.LocalDateTime;

public record RoomCreateRequest(
        String title,
        String content,
        Member manager,
        String repositoryLink,
        String thumbnailLink,
        int matchingSize,
        String keyword,
        int currentParticipantsSize,
        int limitedParticipantsSize,
        LocalDateTime recruitmentDeadline,
        LocalDateTime reviewDeadline,
        Classification classification,
        RoomStatus status
) {

    public Room toEntity() {
        return new Room(title, content, matchingSize,
                repositoryLink, thumbnailLink, keyword,
                currentParticipantsSize, limitedParticipantsSize, manager,
                recruitmentDeadline, reviewDeadline, classification,
                status);
    }
}
