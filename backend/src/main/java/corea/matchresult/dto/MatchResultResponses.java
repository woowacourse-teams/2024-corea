package corea.matchresult.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "매칭 결과들 응답")
public record MatchResultResponses(@Schema(description = "매칭 결과들")
                                   List<MatchResultResponse> matchResultResponses) {
}
