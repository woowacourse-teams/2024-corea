package corea.ranking.service;

import corea.evaluation.domain.EvaluateClassification;
import corea.member.domain.Member;
import corea.profile.repository.ProfileRepository;
import corea.ranking.domain.Ranking;
import corea.ranking.dto.RankingResponse;
import corea.ranking.dto.RankingResponses;
import corea.ranking.repository.RankingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

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
        List<RankingResponse> androidRankings = findTop3Rankings(EvaluateClassification.ANDROID, date);
        List<RankingResponse> backRankings = findTop3Rankings(EvaluateClassification.BACKEND, date);
        List<RankingResponse> frontRankings = findTop3Rankings(EvaluateClassification.FRONTEND, date);
        List<RankingResponse> reviewRankings = findTop3Rankings(EvaluateClassification.REVIEW, date);

        return RankingResponses.of(androidRankings, backRankings, frontRankings, reviewRankings);
    }

    private List<RankingResponse> findTop3Rankings(EvaluateClassification classification, LocalDate date) {
        PageRequest pageRequest = PageRequest.of(DEFAULT_PAGE_NUMBER, NUMBER_OF_RANKINGS_TO_BE_SHOWN_TO_MEMBER);
        List<Ranking> top3Rankings = rankingRepository.findTopRankingsByClassificationAndDate(classification, date, pageRequest);

        return top3Rankings.stream()
                .map(ranking -> toRankingResponse(classification, ranking))
                .toList();
    }

    private RankingResponse toRankingResponse(EvaluateClassification classification, Ranking ranking) {
        Member member = ranking.getMember();
        long deliverCount = profileRepository.findDeliverCountByMember(member);
        float averageEvaluatePoint = ranking.getAverageEvaluatePoint();

        return RankingResponse.of(member, deliverCount, averageEvaluatePoint, classification);
    }
}
