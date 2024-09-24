package corea.room.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "방 참여 멤버 정보 응답")
public record RoomMemberResponse(
        @Schema(description = "멤버 Github 아이디", example = "hjk0761")
        String githubId,

        @Schema(description = "pr 링크", example = "https://github.com/repos/pr/pull/123")
        String prLink
) {
}
