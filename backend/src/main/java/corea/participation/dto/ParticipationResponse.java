package corea.participation.dto;

import corea.participation.domain.Participation;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "방 참여 응답")
public record ParticipationResponse(@Schema(description = "Participation 객체 아이디", example = "1")
                                    long id,

                                    @Schema(description = "방 아이디", example = "1")
                                    long roomId,

                                    @Schema(description = "참여자 아이디", example = "2")
                                    long memberId) {

    public static ParticipationResponse from(Participation participation) {
        return new ParticipationResponse(participation.getId(), participation.getRoomsId(), participation.getMembersId());
    }
}
