package corea.matching.service;

import config.ServiceTest;
import corea.exception.CoreaException;
import corea.fixture.MemberFixture;
import corea.fixture.RoomFixture;
import corea.matching.dto.MatchResultResponses;
import corea.member.domain.Member;
import corea.member.repository.MemberRepository;
import corea.participation.domain.Participation;
import corea.room.domain.Room;
import corea.room.repository.RoomRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static corea.exception.ExceptionType.MEMBER_NOT_FOUND;
import static corea.exception.ExceptionType.ROOM_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ServiceTest
@ActiveProfiles("test")
@Transactional
class MatchResultServiceTest {

    @Autowired
    private MatchingService matchingService;

    @Autowired
    private MatchResultService matchResultService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Test
    @DisplayName("사용자가 특정 방에서 매칭된 리뷰어 결과를 가져온다.")
    void findReviewers() {
        Member pororo = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Member youngsu = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());
        Member ash = memberRepository.save(MemberFixture.MEMBER_ASH());
        Member mubin = memberRepository.save(MemberFixture.MEMBER_MUBIN());
        Room room = roomRepository.save(RoomFixture.ROOM_DOMAIN(pororo));
        int matchingSize = 3;
        List<Participation> participations = new ArrayList<>();

        participations.add(new Participation(room.getId(), pororo.getId()));
        participations.add(new Participation(room.getId(), youngsu.getId()));
        participations.add(new Participation(room.getId(), ash.getId()));
        participations.add(new Participation(room.getId(), mubin.getId()));

        matchingService.matchMaking(participations, matchingSize);

        MatchResultResponses reviewers = matchResultService.findReviewers(pororo.getId(), room.getId());

        assertThat(reviewers.matchResultResponses()).hasSize(matchingSize);
    }

    @Test
    @DisplayName("리뷰어 결과를 가져올 때 존재하지 않는 방이나 사용자의 정보를 요청하는 경우 예외를 발생한다.")
    void findReviewersInvalidException() {
        Member pororo = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Member youngsu = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());
        Member ash = memberRepository.save(MemberFixture.MEMBER_ASH());
        Member mubin = memberRepository.save(MemberFixture.MEMBER_MUBIN());
        Room room = roomRepository.save(RoomFixture.ROOM_DOMAIN(pororo));

        int matchingSize = 3;
        List<Participation> participations = new ArrayList<>();

        participations.add(new Participation(room.getId(), pororo.getId()));
        participations.add(new Participation(room.getId(), youngsu.getId()));
        participations.add(new Participation(room.getId(), ash.getId()));
        participations.add(new Participation(room.getId(), mubin.getId()));

        matchingService.matchMaking(participations, matchingSize);

        assertThatThrownBy(() -> matchResultService.findReviewers(pororo.getId(), 0))
                .isInstanceOf(CoreaException.class)
                .satisfies(exception -> {
                    CoreaException coreaException = (CoreaException) exception;
                    assertThat(coreaException.getExceptionType()).isEqualTo(ROOM_NOT_FOUND);
                });
    }

    @Test
    @DisplayName("리뷰어 결과를 가져올 때 존재하지 않는 방이나 사용자의 정보를 요청하는 경우 예외를 발생한다.")
    void findReviewersInvalidException2() {
        Member pororo = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Member youngsu = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());
        Member ash = memberRepository.save(MemberFixture.MEMBER_ASH());
        Member mubin = memberRepository.save(MemberFixture.MEMBER_MUBIN());
        Room room = roomRepository.save(RoomFixture.ROOM_DOMAIN(pororo));
        int matchingSize = 3;
        List<Participation> participations = new ArrayList<>();

        participations.add(new Participation(room.getId(), pororo.getId()));
        participations.add(new Participation(room.getId(), youngsu.getId()));
        participations.add(new Participation(room.getId(), ash.getId()));
        participations.add(new Participation(room.getId(), mubin.getId()));

        matchingService.matchMaking(participations, matchingSize);

        assertThatThrownBy(() -> matchResultService.findReviewers(0, room.getId()))
                .isInstanceOf(CoreaException.class)
                .satisfies(exception -> {
                    CoreaException coreaException = (CoreaException) exception;
                    assertThat(coreaException.getExceptionType()).isEqualTo(MEMBER_NOT_FOUND);
                });
    }
}
