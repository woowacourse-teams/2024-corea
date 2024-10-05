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
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

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
    private Member tenten;
    private Member darr;
    private Member choco;
    private Room room;


    @BeforeEach
    void setUp() {
        joyson = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
        movin = memberRepository.save(MemberFixture.MEMBER_MOVIN());
        pororo = memberRepository.save(MemberFixture.MEMBER_PORORO());
        ash = memberRepository.save(MemberFixture.MEMBER_ASH());
        tenten = memberRepository.save(MemberFixture.MEMBER_TENTEN());
        darr = memberRepository.save(MemberFixture.MEMBER_DARR());
        choco = memberRepository.save(MemberFixture.MEMBER_CHOCO());
        room = roomRepository.save(RoomFixture.ROOM_DOMAIN(joyson));
    }

    @Test
    @DisplayName("다양한 matchingSize 에 맞게 매칭 결과를 생성한다.")
    void matchPairs() {
        List<Participation> participations = participationRepository.saveAll(List.of(
                new Participation(room, joyson.getId(), joyson.getGithubUserId(), 5),
                new Participation(room, movin.getId(), movin.getGithubUserId(), 2),
                new Participation(room, pororo.getId(), pororo.getGithubUserId(), 6),
                new Participation(room, ash.getId(), ash.getGithubUserId(), 4),
                new Participation(room, tenten.getId(), tenten.getGithubUserId(), 6),
                new Participation(room, darr.getId(), darr.getGithubUserId(), 2),
                new Participation(room, choco.getId(), choco.getGithubUserId(), 3)
        ));

        List<Pair> pairs = matchingStrategy.matchPairs(participations, room.getMatchingSize());

        assertThat(pairs).hasSize(28);
    }

    @Test
    @DisplayName("리뷰어가 포함된 다양한 matchingSize 에 맞게 매칭 결과를 생성한다.")
    void matchPairsWithReviewer() {
        List<Participation> participations = participationRepository.saveAll(List.of(
                new Participation(room, joyson.getId(), joyson.getGithubUserId(), 5),
                new Participation(room, movin.getId(), movin.getGithubUserId(), 2),
                new Participation(room, pororo.getId(), pororo.getGithubUserId(), 5),
                new Participation(room, ash.getId(), ash.getGithubUserId(), 4),
                new Participation(room, tenten.getId(), tenten.getGithubUserId(), 5),
                new Participation(room, darr.getId(), darr.getGithubUserId(), 2),
                new Participation(room, choco.getId(), choco.getGithubUserId(), MemberRole.REVIEWER, 3)
        ));

        List<Pair> pairs = matchingStrategy.matchPairs(participations, room.getMatchingSize());

        assertThat(pairs).hasSize(26);
    }

    @Test
    void some() {
        List<Member> members = memberRepository.saveAll(MemberFixture.CREATE_MEMBERS(100));

        List<Participation> participations = participationRepository.saveAll(
                members.stream()
                        .map(this::createParticipationWithRandom).toList());
        List<Pair> pairs = matchingStrategy.matchPairs(participations, room.getMatchingSize());

        participations.forEach(participation -> validateWithMatchingSize(participation, pairs));
    }

    private Participation createParticipationWithRandom(Member member) {
        return new Participation(room, member.getId(), member.getGithubUserId(), (int)(Math.random() * (10 - 2 + 1)+2));
    }

    private void validateWithMatchingSize(Participation participation, List<Pair> pairs) {
        long deliver_count = pairs.stream()
                .filter(pair -> pair.getDeliver().isMatchingId(participation.getMemberId()))
                .count();

        assertThat(participation.getMatchingSize()).isEqualTo(deliver_count);
    }
}
