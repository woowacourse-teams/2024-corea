package corea.ranking.dto;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

public record RankingResponses(List<RankingResponse> responses) {

    public static RankingResponses of(List<RankingResponse> androidRankings, List<RankingResponse> backRankings,
                                      List<RankingResponse> frontRankings, List<RankingResponse> reviewRankings) {
        return Stream.of(androidRankings, backRankings, frontRankings, reviewRankings)
                .flatMap(List::stream)
                .collect(collectingAndThen(toList(), RankingResponses::new));
    }
}
