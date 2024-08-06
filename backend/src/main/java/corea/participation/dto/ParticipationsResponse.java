package corea.participation.dto;

import corea.participation.domain.Participation;

import java.util.List;

public record ParticipationsResponse(List<Participation> participations) {
}
