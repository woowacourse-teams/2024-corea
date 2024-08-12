package corea.ranking.controller;

import corea.ranking.dto.RankingResponses;
import corea.ranking.service.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ranks")
@RequiredArgsConstructor
public class RankingController implements RankingControllerSpecification {

    private final RankingService rankingService;

    @GetMapping("/board")
    public ResponseEntity<RankingResponses> findTopRankingsSortByClassification() {
        RankingResponses response = rankingService.findTopRankings();
        return ResponseEntity.ok(response);
    }
}
