package corea.matchresult.service;

import config.ServiceTest;
import corea.exception.CoreaException;
import corea.fixture.MemberFixture;
import corea.fixture.RoomFixture;
import corea.matchresult.domain.MatchResult;
import corea.matching.strategy.MatchingStrategy;
import corea.matchresult.dto.MatchResultResponses;
import corea.matchresult.repository.MatchResultRepository;
import corea.member.domain.Member;
import corea.member.domain.MemberRole;
import corea.member.repository.MemberRepository;
import corea.participation.domain.Participation;
import corea.room.domain.Room;
import corea.room.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static corea.exception.ExceptionType.MEMBER_NOT_FOUND;
import static corea.exception.ExceptionType.ROOM_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ServiceTest
class MatchResultServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MatchResultService matchResultService;

    @Autowired
    private MatchResultRepository matchResultRepository;

    @Autowired
    private MatchingStrategy matchingStrategy;

    private List<Participation> participations = new ArrayList<>();
    private Member findMember;
    private Room room;
    private int matchingSize = 3;

    @BeforeEach
    void setUp() {
        room = roomRepository.save(RoomFixture.ROOM_DOMAIN(createMember(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON())));

        findMember = createMember(MemberFixture.MEMBER_YOUNGSU());
        participations.add(new Participation(room, findMember, MemberRole.BOTH, room.getMatchingSize()));
        participations.add(new Participation(room, createMember(MemberFixture.MEMBER_ASH()), MemberRole.BOTH, room.getMatchingSize()));
        participations.add(new Participation(room, createMember(MemberFixture.MEMBER_PORORO()), MemberRole.BOTH, room.getMatchingSize()));
        participations.add(new Participation(room, createMember(MemberFixture.MEMBER_TENTEN()), MemberRole.BOTH, room.getMatchingSize()));
        participations.add(new Participation(room, createMember(MemberFixture.MEMBER_CHOCO()), MemberRole.BOTH, room.getMatchingSize()));
        matchResultRepository.saveAll(matchingStrategy.matchPairs(participations, matchingSize)
                .stream()
                .map(pair -> MatchResult.of(room.getId(), pair, ""))
                .toList()
        );
    }

    @Test
    @DisplayName("사용자가 특정 방에서 매칭된 리뷰어 결과를 가져온다.")
    void findReviewers() {
        MatchResultResponses reviewers = matchResultService.findReviewers(findMember.getId(), room.getId());
        assertThat(reviewers.matchResultResponses()).hasSize(matchingSize);
    }

    @Test
    @DisplayName("리뷰어 결과를 가져올 때 존재하지 않는 방이나 사용자의 정보를 요청하는 경우 예외를 발생한다.")
    void findReviewersInvalidException() {
        assertThatThrownBy(() -> matchResultService.findReviewers(findMember.getId(), 0))
                .isInstanceOf(CoreaException.class)
                .satisfies(exception -> {
                    CoreaException coreaException = (CoreaException) exception;
                    assertThat(coreaException.getExceptionType()).isEqualTo(ROOM_NOT_FOUND);
                });
    }

    @Test
    @DisplayName("리뷰어 결과를 가져올 때 존재하지 않는 방이나 사용자의 정보를 요청하는 경우 예외를 발생한다.")
    void findReviewersInvalidException2() {
        long memberId = 0;
        assertThatThrownBy(() -> matchResultService.findReviewers(memberId, room.getId()))
                .isInstanceOf(CoreaException.class)
                .satisfies(exception -> {
                    CoreaException coreaException = (CoreaException) exception;
                    assertThat(coreaException.getExceptionType()).isEqualTo(MEMBER_NOT_FOUND);
                });
    }

    private Member createMember(Member member) {
        return memberRepository.save(member);
    }
}
