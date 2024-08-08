package corea.ranking.dto;

import java.util.List;
import java.util.Map;

public record RankingResponses(Map<String, List<RankingResponse>> responses) {
}
