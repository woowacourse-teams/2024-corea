package corea.dto;

import corea.domain.Participation;

public record ParticipationRequest(long roomId, long memberId) {

    public Participation toEntity() {
        return new Participation(roomId, memberId);
    }
}
