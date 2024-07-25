package corea.participation.dto;

import corea.participation.domain.Participation;

public record ParticipationResponse(long id, long roomId, long memberId) {

    public static ParticipationResponse from(Participation participation) {
        return new ParticipationResponse(participation.getId(), participation.getRoomId(), participation.getMemberId());
    }
}
