package corea.matching.service;

import config.ServiceTest;
import corea.exception.CoreaException;
import corea.fixture.MemberFixture;
import corea.fixture.RoomFixture;
import corea.matching.dto.MatchResultResponses;
import corea.member.domain.Member;
import corea.member.repository.MemberRepository;
import corea.participation.domain.Participation;
import corea.room.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static corea.exception.ExceptionType.MEMBER_NOT_FOUND;
import static corea.exception.ExceptionType.ROOM_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ServiceTest
@Transactional
class MatchResultServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MatchingService matchingService;

    @Autowired
    private MatchResultService matchResultService;

    private List<Participation> participations = new ArrayList<>();
    private long findMemberId;
    private long roomId;

    @BeforeEach
    void setUp() {
        roomId = roomRepository.save(RoomFixture.ROOM_DOMAIN(
                        createMember(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON()))).getId();
        findMemberId = createMember(MemberFixture.MEMBER_YOUNGSU()).getId();
        participations.add(new Participation(roomId, findMemberId));
        participations.add(new Participation(roomId, createMember(MemberFixture.MEMBER_ASH()).getId()));
        participations.add(new Participation(roomId, createMember(MemberFixture.MEMBER_PORORO()).getId()));
        participations.add(new Participation(roomId, createMember(MemberFixture.MEMBER_TENTEN()).getId()));
        participations.add(new Participation(roomId, createMember(MemberFixture.MEMBER_CHOCO()).getId()));
    }

    private Member createMember(Member member) {
        return memberRepository.save(member);
    }

    @Test
    @DisplayName("사용자가 특정 방에서 매칭된 리뷰어 결과를 가져온다.")
    void findReviewers() {
        int matchingSize = 3;
        matchingService.matchMaking(participations, matchingSize);

        MatchResultResponses reviewers = matchResultService.findReviewers(findMemberId, roomId);

        assertThat(reviewers.matchResultResponses()).hasSize(matchingSize);
    }

    @Test
    @DisplayName("리뷰어 결과를 가져올 때 존재하지 않는 방이나 사용자의 정보를 요청하는 경우 예외를 발생한다.")
    void findReviewersInvalidException() {
        long roomId = 0;
        int matchingSize = 3;

        matchingService.matchMaking(participations, matchingSize);

        assertThatThrownBy(() -> matchResultService.findReviewers(findMemberId, roomId))
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
        int matchingSize = 3;

        matchingService.matchMaking(participations, matchingSize);

        assertThatThrownBy(() -> matchResultService.findReviewers(memberId, roomId))
                .isInstanceOf(CoreaException.class)
                .satisfies(exception -> {
                    CoreaException coreaException = (CoreaException) exception;
                    assertThat(coreaException.getExceptionType()).isEqualTo(MEMBER_NOT_FOUND);
                });
    }
}
