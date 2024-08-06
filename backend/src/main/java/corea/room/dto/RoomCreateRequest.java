package corea.room.dto;

import corea.member.domain.Member;
import corea.room.domain.Classification;
import corea.room.domain.Room;
import corea.room.domain.RoomStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record RoomCreateRequest(
        @NotBlank
        String title,
        String content,
        @NotBlank
        String repositoryLink,
        String thumbnailLink,
        @NotNull
        int matchingSize,
        List<String> keyword,
        @NotNull
        int limitedParticipantsSize,
        @NotNull
        LocalDateTime recruitmentDeadline,
        @NotNull
        LocalDateTime reviewDeadline,
        @NotNull
        Classification classification
) {

    private static final int INITIAL_PARTICIPANTS_SIZE = 1;
    private static final RoomStatus INITIAL_ROOM_STATUS = RoomStatus.OPENED;

    public Room toEntity(Member manager) {
        return new Room(
                title, content,
                matchingSize, repositoryLink,
                thumbnailLink, keyword,
                INITIAL_PARTICIPANTS_SIZE, limitedParticipantsSize,
                manager, recruitmentDeadline,
                reviewDeadline, classification,
                INITIAL_ROOM_STATUS
        );
    }
}
