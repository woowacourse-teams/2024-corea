package corea.participation.service;

import config.ServiceTest;
import corea.exception.CoreaException;
import corea.fixture.MemberFixture;
import corea.fixture.RoomFixture;
import corea.member.domain.Member;
import corea.member.domain.MemberRole;
import corea.member.repository.MemberRepository;
import corea.participation.domain.Participation;
import corea.participation.domain.ParticipationStatus;
import corea.participation.dto.ParticipationRequest;
import corea.participation.repository.ParticipationRepository;
import corea.room.domain.Room;
import corea.room.repository.RoomRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static corea.fixture.MemberFixture.MEMBER_YOUNGSU;
import static corea.fixture.MemberFixture.MEMBER_YOUNGSU_REVIEWER;
import static org.assertj.core.api.Assertions.*;

@ServiceTest
class ParticipationServiceTest {

    @Autowired
    private ParticipationService participationService;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ParticipationRepository participationRepository;

    @Test
    @DisplayName("멤버가 BOTH 로 방에 참여한다.")
    void participate_with_both() {
        Member member = memberRepository.save(MEMBER_YOUNGSU());
        Room room = roomRepository.save(RoomFixture.ROOM_DOMAIN(member));

        assertThatCode(() -> participationService.participate(new ParticipationRequest(room.getId(), member.getId(), "both", 2)))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("리뷰어가 아닌데 reviewer 로 참여시 예외를 발생한다.")
    void throw_exception_when_participate_reviewer_with_not_reviewer() {
        Member member = memberRepository.save(MEMBER_YOUNGSU());
        Room room = roomRepository.save(RoomFixture.ROOM_DOMAIN(member));

        assertThatCode(() -> participationService.participate(new ParticipationRequest(room.getId(), member.getId(), "reviewer", 2)))
                .isInstanceOf(CoreaException.class);
    }

    @Test
    @DisplayName("리뷰어가 reviewer 로 방에 참여한다.")
    void participate_with_reviewer() {
        Member member = memberRepository.save(MEMBER_YOUNGSU_REVIEWER());
        Room room = roomRepository.save(RoomFixture.ROOM_DOMAIN(member));

        assertThatCode(() -> participationService.participate(new ParticipationRequest(room.getId(), member.getId(), "reviewer", 2)))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("리뷰어가 아닌데 reviewer 로 참여시 예외를 발생한다.")
    void throw_exception_when_participate_both_with_not_both() {
        Member member = memberRepository.save(MEMBER_YOUNGSU_REVIEWER());
        Room room = roomRepository.save(RoomFixture.ROOM_DOMAIN(member));

        assertThatCode(() -> participationService.participate(new ParticipationRequest(room.getId(), member.getId(), "both", 2)))
                .isInstanceOf(CoreaException.class);
    }

    @Test
    @DisplayName("ID에 해당하는 방이 없으면 예외를 발생한다.")
    void participate_throw_exception_when_roomId_not_exist() {
        Member member = memberRepository.save(MEMBER_YOUNGSU());

        assertThatCode(() -> participationService.participate(new ParticipationRequest(-1, member.getId(), "both", 2)))
                .isInstanceOf(CoreaException.class);
    }

    @Test
    @DisplayName("ID에 해당하는 멤버가 없으면 예외를 발생한다.")
    void participate_throw_exception_when_memberId_not_exist() {
        Room room = roomRepository.save(RoomFixture.ROOM_DOMAIN(null));

        assertThatCode(() -> participationService.participate(new ParticipationRequest(room.getId(), -1, "both", 2)))
                .isInstanceOf(CoreaException.class);
    }

    @Test
    @DisplayName("role과 일치하는 역할이 없으면 예외를 발생한다.")
    void participate_throw_exception_when_memberRole_not_supported() {
        Member member = memberRepository.save(MEMBER_YOUNGSU());
        Room room = roomRepository.save(RoomFixture.ROOM_DOMAIN(member));

        assertThatCode(() -> participationService.participate(new ParticipationRequest(room.getId(), member.getId(), "thief", 2)))
                .isInstanceOf(CoreaException.class);
    }

    @Test
    @DisplayName("이미 참여중인 방이면 예외를 발생한다.")
    void participate_throw_exception_when_already_participate() {
        Member member = memberRepository.save(MEMBER_YOUNGSU());
        Room room = roomRepository.save(RoomFixture.ROOM_DOMAIN(member));

        participationService.participate(new ParticipationRequest(room.getId(), member.getId(), "both", 2));

        assertThatCode(() -> participationService.participate(new ParticipationRequest(room.getId(), member.getId(), "both", 2)))
                .isInstanceOf(CoreaException.class);
    }

    @Test
    @DisplayName("참여한 방을 취소한다.")
    void cancel_participate() {
        Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
        Member member = memberRepository.save(MEMBER_YOUNGSU());
        Room room = roomRepository.save(RoomFixture.ROOM_DOMAIN(manager));
        participationRepository.save(new Participation(room, member, MemberRole.REVIEWER, ParticipationStatus.MANAGER, room.getMatchingSize()));

        participationService.cancel(room.getId(), member.getId());

        boolean existed = participationRepository.existsByRoomIdAndMemberId(room.getId(), member.getId());

        assertThat(existed).isFalse();
    }

    @Test
    @DisplayName("참여하지 않은 방을 취소 하면 예외를 발생한다.")
    void throw_exception_when_not_participate_room() {
        Member member = memberRepository.save(MEMBER_YOUNGSU());
        Room room = roomRepository.save(RoomFixture.ROOM_DOMAIN(member));

        assertThatThrownBy(() -> participationService.cancel(room.getId(), member.getId()))
                .isInstanceOf(CoreaException.class);
    }
}
