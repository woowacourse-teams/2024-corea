package corea.matching.dto;

import corea.matching.domain.Participation;

public record ParticipationRequest(long roomId, long memberId) {

    public Participation toEntity() {
        return new Participation(roomId, memberId);
    }
}
