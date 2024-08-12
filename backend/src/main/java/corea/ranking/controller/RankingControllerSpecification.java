package corea.ranking.controller;

import corea.exception.ExceptionType;
import corea.global.annotation.ApiErrorResponses;
import corea.ranking.dto.RankingResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Rank", description = "랭킹 관련 API")
public interface RankingControllerSpecification {

    @Operation(summary = "랭킹을 반환합니다.", description = "개발 분야를 기준으로 상위 3위까지의 랭킹을 반환합니다.")
    @ApiErrorResponses(value = ExceptionType.SERVER_ERROR)
    ResponseEntity<RankingResponses> findTopRankingsSortByClassification();
}
