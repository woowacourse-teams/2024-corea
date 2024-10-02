package corea.participation.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "방 참여 요청")
public record ParticipationRequest(@Schema(description = "방 아이디", example = "1")
                                   long roomId,

                                   @Schema(description = "참여자 아이디", example = "2")
                                   long memberId,

                                   @Schema(description = "참여자 역할", example = "reviewer")
                                   String role,

                                   @Schema(description = "참여자가 상호 리뷰 하고 싶은 인원 수", example = "3")
                                   int matchingSize) {
}
