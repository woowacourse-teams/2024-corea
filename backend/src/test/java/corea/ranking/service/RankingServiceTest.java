package corea.ranking.service;

import config.ServiceTest;
import corea.evaluation.domain.EvaluateClassification;
import corea.fixture.MemberFixture;
import corea.member.domain.Member;
import corea.member.repository.MemberRepository;
import corea.ranking.domain.Ranking;
import corea.ranking.dto.RankingResponse;
import corea.ranking.dto.RankingResponses;
import corea.ranking.repository.RankingRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

@ServiceTest
class RankingServiceTest {

    @Autowired
    private RankingService rankingService;

    @Autowired
    private RankingRepository rankingRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("전체 순위를 조회할 수 있다.")
    void findTopRankings() {
        LocalDate date = LocalDate.now();
        Member pororo = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Member youngsu = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());
        Member ash = memberRepository.save(MemberFixture.MEMBER_ASH());
        Member movin = memberRepository.save(MemberFixture.MEMBER_MOVIN());

        rankingRepository.save(new Ranking(pororo, 3, 3.0f, date, EvaluateClassification.ANDROID));
        rankingRepository.save(new Ranking(youngsu, 4, 2.0f, date, EvaluateClassification.ANDROID));
        rankingRepository.save(new Ranking(ash, 1, 5.0f, date, EvaluateClassification.ANDROID));
        rankingRepository.save(new Ranking(movin, 2, 4.0f, date, EvaluateClassification.ANDROID));

        rankingRepository.save(new Ranking(pororo, 1, 5.0f, date, EvaluateClassification.BACKEND));
        rankingRepository.save(new Ranking(youngsu, 2, 4.0f, date, EvaluateClassification.BACKEND));
        rankingRepository.save(new Ranking(ash, 3, 3.0f, date, EvaluateClassification.BACKEND));
        rankingRepository.save(new Ranking(movin, 4, 2.0f, date, EvaluateClassification.BACKEND));

        rankingRepository.save(new Ranking(pororo, 2, 4.0f, date, EvaluateClassification.FRONTEND));
        rankingRepository.save(new Ranking(youngsu, 3, 3.0f, date, EvaluateClassification.FRONTEND));
        rankingRepository.save(new Ranking(ash, 4, 2.0f, date, EvaluateClassification.FRONTEND));
        rankingRepository.save(new Ranking(movin, 1, 5.0f, date, EvaluateClassification.FRONTEND));

        rankingRepository.save(new Ranking(pororo, 4, 2.0f, date, EvaluateClassification.REVIEW));
        rankingRepository.save(new Ranking(youngsu, 1, 5.0f, date, EvaluateClassification.REVIEW));
        rankingRepository.save(new Ranking(ash, 2, 4.0f, date, EvaluateClassification.REVIEW));
        rankingRepository.save(new Ranking(movin, 3, 3.0f, date, EvaluateClassification.REVIEW));

        RankingResponses topRankings = rankingService.findTopRankings();
        Map<String, List<RankingResponse>> responses = topRankings.responses();
        List<RankingResponse> android = responses.get("an");
        List<RankingResponse> backend = responses.get("be");
        List<RankingResponse> frontend = responses.get("fe");
        List<RankingResponse> review = responses.get("re");

        assertSoftly(softly -> {
            softly.assertThat(android.get(0).nickname()).isEqualTo("ashsty");
            softly.assertThat(android.get(0).rank()).isEqualTo(1);
            softly.assertThat(android.get(1).nickname()).isEqualTo("hjk0761");
            softly.assertThat(android.get(1).rank()).isEqualTo(2);
            softly.assertThat(android.get(2).nickname()).isEqualTo("pororo");
            softly.assertThat(android.get(2).rank()).isEqualTo(3);

            softly.assertThat(backend.get(0).nickname()).isEqualTo("pororo");
            softly.assertThat(backend.get(1).nickname()).isEqualTo("youngsu5582");
            softly.assertThat(backend.get(2).nickname()).isEqualTo("ashsty");

            softly.assertThat(frontend.get(0).nickname()).isEqualTo("hjk0761");
            softly.assertThat(frontend.get(1).nickname()).isEqualTo("pororo");
            softly.assertThat(frontend.get(2).nickname()).isEqualTo("youngsu5582");

            softly.assertThat(review.get(0).nickname()).isEqualTo("youngsu5582");
            softly.assertThat(review.get(1).nickname()).isEqualTo("ashsty");
            softly.assertThat(review.get(2).nickname()).isEqualTo("hjk0761");
        });
    }
}
