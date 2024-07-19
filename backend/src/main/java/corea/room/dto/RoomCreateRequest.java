package corea.room.dto;

import corea.member.domain.Member;
import corea.room.domain.Room;

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
        LocalDateTime reviewDeadline
) {

    public Room toEntity() {
        return new Room(title, content, matchingSize,
                repositoryLink, thumbnailLink, keyword,
                currentParticipantsSize, limitedParticipantsSize, manager,
                recruitmentDeadline, reviewDeadline);
    }
}
