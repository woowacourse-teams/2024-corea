package corea.participation.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record ParticipationHttpRequest(
        @Schema(description = "참여자가 원하는 매칭 인원 수", example = "3")
        int matchingSize) {
}
