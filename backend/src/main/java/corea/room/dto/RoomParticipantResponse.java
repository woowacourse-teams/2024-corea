package corea.room.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "방 참여 멤버 정보 응답")
public record RoomParticipantResponse(
        @Schema(description = "깃허브 ID", example = "98307410")
        String githubId,

        @Schema(description = "아이디", example = "hjk0761")
        String username,

        @Schema(description = "pr 링크", example = "https://github.com/repos/pr/pull/123")
        String prLink,

        @Schema(description = "썸네일 링크", example = "www.myProfile.jpg")
        String thumbnailLink
) {
}
