package corea.ranking.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.Map;

@Schema(description = "분야별 랭킹 정보 응답")
public record RankingResponses(@Schema(description = "분야 별 랭킹 응답")
                               Map<String, List<RankingResponse>> responses) {
}
