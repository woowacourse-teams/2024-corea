package corea.matching.repository;

import config.RepositoryTest;
import corea.fixture.MatchResultFixture;
import corea.fixture.MemberFixture;
import corea.fixture.RoomFixture;
import corea.matching.domain.MatchResult;
import corea.member.domain.Member;
import corea.member.repository.MemberRepository;
import corea.room.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@RepositoryTest
class MatchResultRepositoryTest {

    @Autowired
    private MatchResultRepository matchResultRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RoomRepository roomRepository;

    Member manager, member, reviewer, reviewee;
    long roomId;

    @BeforeEach
    void setUp() {
        manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
        member = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());
        reviewer = memberRepository.save(MemberFixture.MEMBER_ASH());
        reviewee = memberRepository.save(MemberFixture.MEMBER_PORORO());

        roomId = roomRepository.save(RoomFixture.ROOM_DOMAIN(manager)).getId();

        matchResultRepository.save(MatchResultFixture.MATCH_RESULT_DOMAIN(roomId, manager, member));
        matchResultRepository.save(MatchResultFixture.MATCH_RESULT_DOMAIN(roomId, member, manager));
        matchResultRepository.save(MatchResultFixture.MATCH_RESULT_DOMAIN(roomId, reviewer, manager));
        matchResultRepository.save(MatchResultFixture.MATCH_RESULT_DOMAIN(roomId, member, reviewee));
    }

    @Test
    @DisplayName("방 아이디와 멤버 아이디로 해당 멤버의 매칭 결과가 존재하는지 조회할 수 있다.")
    void existsByRoomIdAndMemberId() {
        Member notMatched = memberRepository.save(MemberFixture.MEMBER_CHOCO());

        boolean isNotMatched = matchResultRepository.existsByRoomIdAndMemberId(roomId, notMatched.getId());
        boolean isReviewer = matchResultRepository.existsByRoomIdAndMemberId(roomId, reviewer.getId());
        boolean isReviewee = matchResultRepository.existsByRoomIdAndMemberId(roomId, reviewee.getId());

        assertAll(
                () -> assertThat(isNotMatched).isFalse(),
                () -> assertThat(isReviewer).isTrue(),
                () -> assertThat(isReviewee).isTrue()
        );
    }

    @Test
    @DisplayName("사용자가 입장한 방에서 사용자를 리뷰할 사람들을 조회할 수 있다.")
    void findAllByRoomIdAndToMemberId() {
        List<MatchResult> results = matchResultRepository.findAllByRevieweeIdAndRoomId(manager.getId(), roomId);

        assertThat(results).hasSize(2);
    }
}
