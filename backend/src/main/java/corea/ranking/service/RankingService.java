package corea.ranking.service;

import corea.evaluation.domain.EvaluateClassification;
import corea.member.domain.Member;
import corea.member.repository.ProfileRepository;
import corea.ranking.domain.Ranking;
import corea.ranking.dto.RankingResponse;
import corea.ranking.dto.RankingResponses;
import corea.ranking.repository.RankingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RankingService {

    private static final int DEFAULT_PAGE_NUMBER = 0;
    private static final int NUMBER_OF_RANKINGS_TO_BE_SHOWN_TO_MEMBER = 3;

    private final RankingRepository rankingRepository;
    private final ProfileRepository profileRepository;

    public RankingResponses findTopRankings() {
        LocalDate date = LocalDate.now();
        Map<String, List<RankingResponse>> topRankings = Arrays.stream(EvaluateClassification.values())
                .collect(toMap(EvaluateClassification::getExpression, classification -> findTop3Rankings(classification, date)));

        return new RankingResponses(topRankings);
    }

    private List<RankingResponse> findTop3Rankings(EvaluateClassification classification, LocalDate date) {
        PageRequest pageRequest = PageRequest.of(DEFAULT_PAGE_NUMBER, NUMBER_OF_RANKINGS_TO_BE_SHOWN_TO_MEMBER);
        List<Ranking> top3Rankings = rankingRepository.findTopRankingsByClassificationAndDate(classification, date, pageRequest);
        return top3Rankings.stream()
                .map(ranking -> toRankingResponse(classification, ranking))
                .toList();
    }

    private RankingResponse toRankingResponse(EvaluateClassification classification, Ranking ranking) {
        int rank = ranking.getRanking();
        Member member = ranking.getMember();
        long deliverCount = profileRepository.findDeliverCountByMemberId(member.getId());
        float averageEvaluatePoint = ranking.getAverageEvaluatePoint();

        return RankingResponse.of(rank, member, deliverCount, averageEvaluatePoint, classification);
    }
}
