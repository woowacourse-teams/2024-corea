package corea.ranking.repository;

import corea.evaluation.domain.EvaluateClassification;
import corea.fixture.MemberFixture;
import corea.member.domain.Member;
import corea.member.repository.MemberRepository;
import corea.ranking.domain.Ranking;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DataJpaTest
class RankingRepositoryTest {

    @Autowired
    private RankingRepository rankingRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("상위 랭킹의 멤버들을 순서대로 가져올 수 있다.")
    void findTopRankingsByClassificationAndDate() {
        Member pororo = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Member youngsu = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());
        Member ash = memberRepository.save(MemberFixture.MEMBER_ASH());
        Member movin = memberRepository.save(MemberFixture.MEMBER_MOVIN());

        LocalDate date = LocalDate.now();
        EvaluateClassification classification = EvaluateClassification.BACKEND;
        rankingRepository.save(new Ranking(pororo, 1, 5.0f, date, classification));
        rankingRepository.save(new Ranking(youngsu, 2, 4.0f, date, classification));
        rankingRepository.save(new Ranking(ash, 3, 3.0f, date, classification));
        rankingRepository.save(new Ranking(movin, 4, 2.0f, date, classification));

        List<Ranking> rankings = rankingRepository.findTopRankingsByClassificationAndDate(classification, date, PageRequest.of(0, 3));

        assertSoftly(softly -> {
            softly.assertThat(rankings.get(0).getMember()).isEqualTo(pororo);
            softly.assertThat(rankings.get(1).getMember()).isEqualTo(youngsu);
            softly.assertThat(rankings.get(2).getMember()).isEqualTo(ash);
        });
    }
}
