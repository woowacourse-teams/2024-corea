package corea.matching.domain;

import corea.fixture.MemberFixture;
import corea.fixture.RoomFixture;
import corea.member.domain.Member;
import corea.member.repository.MemberRepository;
import corea.participation.domain.Participation;
import corea.participation.repository.ParticipationRepository;
import corea.room.domain.Room;
import corea.room.repository.RoomRepository;
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

    @Test
    @DisplayName("다양한 matchingSize 에 맞게 매칭 결과를 생성한다.")
    void matchPairs() {
        Member joyson = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
        Member movin = memberRepository.save(MemberFixture.MEMBER_MOVIN());
        Member pororo = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Member ash = memberRepository.save(MemberFixture.MEMBER_ASH());
        Room room = roomRepository.save(RoomFixture.ROOM_DOMAIN(joyson));

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
}
