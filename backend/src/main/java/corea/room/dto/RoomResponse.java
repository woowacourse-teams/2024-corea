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
        LocalDateTime reviewDeadline,
        boolean isParticipated,
        boolean isClosed
) {
    public static RoomResponse from(Room room) {
        return new RoomResponse(
                room.getId(),
                room.getTitle(),
                room.getContent(),
                room.getManager().getName(),
                room.getRepositoryLink(),
                room.getThumbnailLink(),
                room.getMatchingSize(),
                room.getKeyword(),
                room.getCurrentParticipantsSize(),
                room.getLimitedParticipantsSize(),
                room.getRecruitmentDeadline(),
                room.getReviewDeadline(),
                false,
                room.isOpen()
        );
    }

    public static RoomResponse from(Room room,boolean isParticipated) {
        return new RoomResponse(
                room.getId(),
                room.getTitle(),
                room.getContent(),
                room.getManager().getName(),
                room.getRepositoryLink(),
                room.getThumbnailLink(),
                room.getMatchingSize(),
                room.getKeyword(),
                room.getCurrentParticipantsSize(),
                room.getLimitedParticipantsSize(),
                room.getRecruitmentDeadline(),
                room.getReviewDeadline(),
                isParticipated,
                room.isClosed()
        );
    }
}
