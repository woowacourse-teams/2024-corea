package corea.dto;

import corea.domain.Participation;

public record ParticipationResponse(long id, long roomId, long memberId) {

    public static ParticipationResponse from(final Participation participation) {
        return new ParticipationResponse(participation.getId(), participation.getRoomId(), participation.getMemberId());
    }
}
