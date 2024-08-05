package corea.ranking.service;

import config.ServiceTest;
import corea.evaluation.domain.EvaluateClassification;
import corea.fixture.MemberFixture;
import corea.member.domain.Member;
import corea.member.repository.MemberRepository;
import corea.profile.domain.Profile;
import corea.profile.repository.ProfileRepository;
import corea.ranking.domain.Ranking;
import corea.ranking.dto.RankingResponse;
import corea.ranking.dto.RankingResponses;
import corea.ranking.repository.RankingRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

@ServiceTest
class RankingServiceTest {

    @Autowired
    private RankingService rankingService;

    @Autowired
    private RankingRepository rankingRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Test
    @DisplayName("전체 순위를 조회할 수 있다.")
    void findTopRankings() {
        LocalDate date = LocalDate.now();
        Member pororo = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Member youngsu = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());
        Member ash = memberRepository.save(MemberFixture.MEMBER_ASH());
        Member mubin = memberRepository.save(MemberFixture.MEMBER_MUBIN());

        profileRepository.save(new Profile(pororo, 0, 0, 0, 1.0f, 36.5f));
        profileRepository.save(new Profile(youngsu, 0, 0, 0, 1.0f, 36.5f));
        profileRepository.save(new Profile(ash, 0, 0, 0, 1.0f, 36.5f));
        profileRepository.save(new Profile(mubin, 0, 0, 0, 1.0f, 36.5f));

        rankingRepository.save(new Ranking(pororo, 3, 3.0f, date, EvaluateClassification.ANDROID));
        rankingRepository.save(new Ranking(youngsu, 4, 2.0f, date, EvaluateClassification.ANDROID));
        rankingRepository.save(new Ranking(ash, 1, 5.0f, date, EvaluateClassification.ANDROID));
        rankingRepository.save(new Ranking(mubin, 2, 4.0f, date, EvaluateClassification.ANDROID));

        rankingRepository.save(new Ranking(pororo, 1, 5.0f, date, EvaluateClassification.BACKEND));
        rankingRepository.save(new Ranking(youngsu, 2, 4.0f, date, EvaluateClassification.BACKEND));
        rankingRepository.save(new Ranking(ash, 3, 3.0f, date, EvaluateClassification.BACKEND));
        rankingRepository.save(new Ranking(mubin, 4, 2.0f, date, EvaluateClassification.BACKEND));

        rankingRepository.save(new Ranking(pororo, 2, 4.0f, date, EvaluateClassification.FRONTEND));
        rankingRepository.save(new Ranking(youngsu, 3, 3.0f, date, EvaluateClassification.FRONTEND));
        rankingRepository.save(new Ranking(ash, 4, 2.0f, date, EvaluateClassification.FRONTEND));
        rankingRepository.save(new Ranking(mubin, 1, 5.0f, date, EvaluateClassification.FRONTEND));

        rankingRepository.save(new Ranking(pororo, 4, 2.0f, date, EvaluateClassification.REVIEW));
        rankingRepository.save(new Ranking(youngsu, 1, 5.0f, date, EvaluateClassification.REVIEW));
        rankingRepository.save(new Ranking(ash, 2, 4.0f, date, EvaluateClassification.REVIEW));
        rankingRepository.save(new Ranking(mubin, 3, 3.0f, date, EvaluateClassification.REVIEW));

        RankingResponses topRankings = rankingService.findTopRankings();
        List<RankingResponse> response = topRankings.responses();

        assertSoftly(softly -> {
            softly.assertThat(response.get(0).nickname()).isEqualTo("ashsty");
            softly.assertThat(response.get(1).nickname()).isEqualTo("hjk0761");
            softly.assertThat(response.get(2).nickname()).isEqualTo("pororo");

            softly.assertThat(response.get(3).nickname()).isEqualTo("pororo");
            softly.assertThat(response.get(4).nickname()).isEqualTo("youngsu5582");
            softly.assertThat(response.get(5).nickname()).isEqualTo("ashsty");

            softly.assertThat(response.get(6).nickname()).isEqualTo("hjk0761");
            softly.assertThat(response.get(7).nickname()).isEqualTo("pororo");
            softly.assertThat(response.get(8).nickname()).isEqualTo("youngsu5582");

            softly.assertThat(response.get(9).nickname()).isEqualTo("youngsu5582");
            softly.assertThat(response.get(10).nickname()).isEqualTo("ashsty");
            softly.assertThat(response.get(11).nickname()).isEqualTo("hjk0761");
        });
    }
}
