package corea.participation.dto;

import corea.participation.domain.Participation;

public record ParticipationRequest(long roomId, long memberId) {

    public Participation toEntity() {
        return new Participation(roomId, memberId);
    }
}
