package corea.matching.domain;

import corea.fixture.MemberFixture;
import corea.fixture.RoomFixture;
import corea.member.domain.Member;
import corea.member.domain.MemberRole;
import corea.member.repository.MemberRepository;
import corea.participation.domain.Participation;
import corea.participation.repository.ParticipationRepository;
import corea.room.domain.Room;
import corea.room.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@SpringBootTest
class DynamicSizeMatchingStrategyTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ParticipationRepository participationRepository;

    @Autowired
    private DynamicSizeMatchingStrategy matchingStrategy;

    private Member joyson;
    private Member movin;
    private Member pororo;
    private Member ash;
    private Room room;


    @BeforeEach
    void setUp() {
        joyson = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
        movin = memberRepository.save(MemberFixture.MEMBER_MOVIN());
        pororo = memberRepository.save(MemberFixture.MEMBER_PORORO());
        ash = memberRepository.save(MemberFixture.MEMBER_ASH());
        room = roomRepository.save(RoomFixture.ROOM_DOMAIN(joyson));
    }

    @Test
    @DisplayName("다양한 matchingSize 에 맞게 매칭 결과를 생성한다.")
    void matchPairs() {
        List<Participation> participations = participationRepository.saveAll(List.of(
                new Participation(room, joyson.getId(), joyson.getGithubUserId(), 4),
                new Participation(room, movin.getId(), movin.getGithubUserId(), 2),
                new Participation(room, pororo.getId(), pororo.getGithubUserId(), 3)
                , new Participation(room, ash.getId(), ash.getGithubUserId(), 3)
        ));

        List<Pair> pairs = matchingStrategy.matchPairs(participations);

        List<Pair> pairWithReviewerMovin = pairs.stream().filter(pair -> pair.getReceiver().getUsername().equals(movin.getUsername())).toList();
        List<Pair> pairWithRevieweeJoyson = pairs.stream().filter(pair -> pair.getDeliver().getUsername().equals(joyson.getUsername())).toList();

        assertThat(pairs).hasSize(11);
        assertSoftly(softly -> {
            softly.assertThat(pairWithReviewerMovin).hasSize(2);
            softly.assertThat(pairWithRevieweeJoyson).hasSize(3);
        });
    }

    @Test
    @DisplayName("리뷰어가 포함된 경우 알맞는 매칭 결과를 생성한다.")
    void matchPairsWithReviewer() {
        List<Participation> participations = participationRepository.saveAll(List.of(
                new Participation(room, joyson.getId(), joyson.getGithubUserId(), 4),
                new Participation(room, movin.getId(), movin.getGithubUserId(), 2),
                new Participation(room, pororo.getId(), pororo.getGithubUserId(), 3)
                , new Participation(room, ash.getId(), ash.getGithubUserId(), MemberRole.REVIEWER, 3)
        ));

        List<Pair> pairs = matchingStrategy.matchPairs(participations);

        pairs.forEach(pair -> System.out.println(pair.getDeliver().getUsername() + ":" + pair.getReceiver().getUsername()));

        assertThat(pairs).hasSize(8);
    }
}
