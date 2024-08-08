package corea.participation.dto;

import corea.participation.domain.Participation;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "방 참여 요청들")
public record ParticipationsResponse(@Schema(description = "방 참여 요청들")
                                     List<Participation> participations) {
}
