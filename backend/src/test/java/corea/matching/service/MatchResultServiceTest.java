package corea.matching.service;

import config.ServiceTest;
import corea.exception.CoreaException;
import corea.fixture.MemberFixture;
import corea.fixture.RoomFixture;
import corea.matching.domain.DynamicSizeMatchingStrategy;
import corea.matching.domain.MatchResult;
import corea.matching.dto.MatchResultResponses;
import corea.matching.repository.MatchResultRepository;
import corea.member.domain.Member;
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
    private DynamicSizeMatchingStrategy matchingStrategy;

    private final List<Participation> participations = new ArrayList<>();
    private long findMemberId;
    private Room room;
    private int matchingSize = 3;

    @BeforeEach
    void setUp() {
        room = roomRepository.save(RoomFixture.ROOM_DOMAIN(createMember(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON())));

        findMemberId = createMember(MemberFixture.MEMBER_YOUNGSU()).getId();
        participations.add(new Participation(room, findMemberId, MemberFixture.MEMBER_YOUNGSU().getGithubUserId(), matchingSize));
        participations.add(new Participation(room, createMember(MemberFixture.MEMBER_ASH()).getId(), MemberFixture.MEMBER_ASH().getGithubUserId(), matchingSize));
        participations.add(new Participation(room, createMember(MemberFixture.MEMBER_PORORO()).getId(), MemberFixture.MEMBER_PORORO().getGithubUserId(), matchingSize));
        participations.add(new Participation(room, createMember(MemberFixture.MEMBER_TENTEN()).getId(), MemberFixture.MEMBER_TENTEN().getGithubUserId(), matchingSize));
        participations.add(new Participation(room, createMember(MemberFixture.MEMBER_CHOCO()).getId(), MemberFixture.MEMBER_CHOCO().getGithubUserId(), matchingSize));
        matchResultRepository.saveAll(matchingStrategy.matchPairs(participations)
                .stream()
                .map(pair -> MatchResult.of(room.getId(), pair, ""))
                .toList()
        );
    }

    @Test
    @DisplayName("사용자가 특정 방에서 매칭된 리뷰어 결과를 가져온다.")
    void findReviewers() {
        MatchResultResponses reviewers = matchResultService.findReviewers(findMemberId, room.getId());
        assertThat(reviewers.matchResultResponses()).hasSize(matchingSize);
    }

    @Test
    @DisplayName("리뷰어 결과를 가져올 때 존재하지 않는 방이나 사용자의 정보를 요청하는 경우 예외를 발생한다.")
    void findReviewersInvalidException() {
        assertThatThrownBy(() -> matchResultService.findReviewers(findMemberId, 0))
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
