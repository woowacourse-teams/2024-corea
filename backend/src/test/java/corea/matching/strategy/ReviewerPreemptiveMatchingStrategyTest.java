package corea.matching.strategy;

import corea.fixture.MemberFixture;
import corea.fixture.RoomFixture;
import corea.matching.domain.Pair;
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

@SpringBootTest
class ReviewerPreemptiveMatchingStrategyTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ParticipationRepository participationRepository;

    @Autowired
    private ReviewerPreemptiveMatchingStrategy matchingStrategy;

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
                new Participation(room, joyson, MemberRole.BOTH, 5),
                new Participation(room, movin, MemberRole.BOTH, 2),
                new Participation(room, pororo, MemberRole.BOTH, 6),
                new Participation(room, ash, MemberRole.BOTH, 4),
                new Participation(room, tenten, MemberRole.BOTH, 6),
                new Participation(room, darr, MemberRole.BOTH, 2),
                new Participation(room, choco, MemberRole.BOTH, 3)
        ));

        List<Pair> pairs = matchingStrategy.matchPairs(participations, room.getMatchingSize());

        for (Participation participation : participations) {
            long reviewerCount = pairs.stream()
                    .filter(pair -> pair.getDeliver().getGithubUserId().equals(participation.getMemberGithubId()))
                    .count();
            long revieweeCount = pairs.stream()
                    .filter(pair -> pair.getReceiver().getGithubUserId().equals(participation.getMemberGithubId()))
                    .count();
            assertThat(reviewerCount).isLessThanOrEqualTo(participation.getMatchingSize());
            assertThat(reviewerCount).isGreaterThanOrEqualTo(room.getMatchingSize());
            assertThat(revieweeCount).isLessThanOrEqualTo(participation.getMatchingSize());
            assertThat(revieweeCount).isGreaterThanOrEqualTo(room.getMatchingSize());
        }
    }

    @Test
    @DisplayName("리뷰어가 포함된 다양한 matchingSize 에 맞게 매칭 결과를 생성한다.")
    void matchPairsWithReviewer() {
        List<Participation> participations = participationRepository.saveAll(List.of(
                new Participation(room, joyson, MemberRole.BOTH, 5),
                new Participation(room, movin, MemberRole.BOTH, 2),
                new Participation(room, pororo, MemberRole.BOTH, 5),
                new Participation(room, ash, MemberRole.BOTH, 4),
                new Participation(room, tenten, MemberRole.BOTH, 5),
                new Participation(room, darr, MemberRole.BOTH, 2),
                new Participation(room, choco, MemberRole.REVIEWER, 3)
        ));

        List<Pair> pairs = matchingStrategy.matchPairs(participations, room.getMatchingSize());
        long totalRevieweeCount = participations.stream()
                .filter(participation -> !participation.isReviewer())
                .count();

        for (Participation participation : participations) {
            long reviewerCount = pairs.stream()
                    .filter(pair -> pair.getDeliver().getGithubUserId().equals(participation.getMemberGithubId()))
                    .count();
            long revieweeCount = pairs.stream()
                    .filter(pair -> pair.getReceiver().getGithubUserId().equals(participation.getMemberGithubId()))
                    .count();
            if (participation.getMemberRole().isReviewer()) {
                assertThat(reviewerCount).isEqualTo(totalRevieweeCount);
                assertThat(revieweeCount).isZero();
            } else {
                assertThat(reviewerCount).isLessThanOrEqualTo(participation.getMatchingSize());
                assertThat(reviewerCount).isGreaterThanOrEqualTo(room.getMatchingSize());
                assertThat(revieweeCount).isLessThanOrEqualTo(participation.getMatchingSize() + reviewerCount);
                assertThat(revieweeCount).isGreaterThanOrEqualTo(room.getMatchingSize());
            }
        }
    }

    @Test
    @DisplayName("다수의 매칭에 대해서 matchingSize 보다 작거나 같은 수의 매칭을 보장한다.")
    void matchPairsWith100Members() {
        List<Member> members = memberRepository.saveAll(MemberFixture.CREATE_MEMBERS(100));

        List<Participation> participations = participationRepository.saveAll(
                members.stream()
                        .map(this::createParticipationWithRandom).toList());
        List<Pair> pairs = matchingStrategy.matchPairs(participations, room.getMatchingSize());

        participations.forEach(participation -> validateWithMatchingSize(participation, pairs));
    }

    private Participation createParticipationWithRandom(Member member) {
        return new Participation(room, member, MemberRole.BOTH, (int) (Math.random() * (10 - 2 + 1) + 2));
    }

    private void validateWithMatchingSize(Participation participation, List<Pair> pairs) {
        long deliver_count = pairs.stream()
                .filter(pair -> pair.getDeliver().isMatchingId(participation.getMember().getId()))
                .count();

        assertThat(participation.getMatchingSize()).isGreaterThanOrEqualTo((int) deliver_count);
    }
}
