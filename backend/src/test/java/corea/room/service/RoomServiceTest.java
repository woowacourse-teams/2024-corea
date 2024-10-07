package corea.room.service;

import config.ServiceTest;
import corea.auth.domain.AuthInfo;
import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.fixture.MatchResultFixture;
import corea.fixture.MemberFixture;
import corea.fixture.RoomFixture;
import corea.matching.repository.MatchResultRepository;
import corea.member.domain.Member;
import corea.member.repository.MemberRepository;
import corea.participation.domain.Participation;
import corea.participation.domain.ParticipationStatus;
import corea.participation.repository.ParticipationRepository;
import corea.room.domain.Room;
import corea.room.domain.RoomStatus;
import corea.room.dto.RoomCreateRequest;
import corea.room.dto.RoomParticipantResponses;
import corea.room.dto.RoomResponse;
import corea.room.dto.RoomResponses;
import corea.room.repository.RoomRepository;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ServiceTest
class RoomServiceTest {

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MatchResultRepository matchResultRepository;

    @Autowired
    private ParticipationRepository participationRepository;

    @Test
    @DisplayName("방을 생성할 수 있다.")
    void create() {
        Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());

        roomService.create(manager.getId(), RoomFixture.ROOM_CREATE_REQUEST());

        assertThat(roomRepository.findAll()).hasSize(1);
    }

    @DisplayName("방을 생성할 때 모집 마감 시간은 현재 시간보다 1시간 이후가 아니라면 예외가 발생한다.")
    void invalidRecruitmentDeadline() {
        Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());

        RoomCreateRequest request = RoomFixture.ROOM_CREATE_REQUEST_WITH_RECRUITMENT_DEADLINE(LocalDateTime.now().plusMinutes(59));

        assertThatThrownBy(() -> roomService.create(manager.getId(), request))
                .asInstanceOf(InstanceOfAssertFactories.type(CoreaException.class))
                .extracting(CoreaException::getExceptionType)
                .isEqualTo(ExceptionType.INVALID_RECRUITMENT_DEADLINE);
    }

    @Test
    @DisplayName("방을 생성할 때 리뷰 마감 시간은 모집 마감 시간보다 1일 이후가 아니라면 예외가 발생한다.")
    void invalidReviewDeadline() {
        Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());

        RoomCreateRequest request = RoomFixture.ROOM_CREATE_REQUEST(LocalDateTime.now().plusHours(2), LocalDateTime.now().plusDays(1));

        assertThatThrownBy(() -> roomService.create(manager.getId(), request))
                .asInstanceOf(InstanceOfAssertFactories.type(CoreaException.class))
                .extracting(CoreaException::getExceptionType)
                .isEqualTo(ExceptionType.INVALID_REVIEW_DEADLINE);
    }

    @Test
    @DisplayName("방을 만든 사람이 방을 조회할 때 자신의 참여 상태가 방장이란 것을 알 수 있다.")
    void findOne_manager() {
        Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
        RoomCreateRequest request = RoomFixture.ROOM_CREATE_REQUEST();
        RoomResponse response = roomService.create(manager.getId(), request);

        response = roomService.findOne(response.id(), manager.getId());

        assertThat(response.participationStatus()).isEqualTo(ParticipationStatus.MANAGER);
    }

    @Test
    @DisplayName("방을 조회할 때 자신의 참여 상태를 알 수 있다.")
    void findOne_participated() {
        Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
        Room room = roomRepository.save(RoomFixture.ROOM_DOMAIN(manager));

        Member member = memberRepository.save(MemberFixture.MEMBER_PORORO());
        participationRepository.save(new Participation(room, member.getId()));

        RoomResponse response = roomService.findOne(room.getId(), member.getId());

        assertThat(response.participationStatus()).isEqualTo(ParticipationStatus.PARTICIPATED);
    }

    @Test
    @DisplayName("방을 조회할 때 자신의 참여 상태를 알 수 있다.")
    void findOne_not_participated() {
        Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
        Room room = roomRepository.save(RoomFixture.ROOM_DOMAIN(manager));

        Member member = memberRepository.save(MemberFixture.MEMBER_PORORO());
        RoomResponse response = roomService.findOne(room.getId(), member.getId());

        assertThat(response.participationStatus()).isEqualTo(ParticipationStatus.NOT_PARTICIPATED);
    }

    @Test
    @DisplayName("해당 방에 참여하고 있으나 PR을 제출하지 않아 매칭 결과가 없는 경우를 판단할 수 있다.")
    void findOne_withPullRequestSubmission() {
        Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
        Room room = roomRepository.save(RoomFixture.ROOM_DOMAIN_WITH_PROGRESS(manager));

        Member pororo = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Member movin = memberRepository.save(MemberFixture.MEMBER_MOVIN());
        participationRepository.save(new Participation(room, pororo.getId()));
        participationRepository.save(new Participation(room, movin.getId()));

        matchResultRepository.save(MatchResultFixture.MATCH_RESULT_DOMAIN(room.getId(), movin, manager));

        RoomResponse response = roomService.findOne(room.getId(), pororo.getId());

        assertThat(response.participationStatus()).isEqualTo(ParticipationStatus.PULL_REQUEST_NOT_SUBMITTED);
    }

    @Test
    @DisplayName("현재 로그인한 멤버가 참여 중인 방을 리뷰 마감일이 임박한 순으로 보여준다.")
    void findParticipatedRooms() {
        Member pororo = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Member ash = memberRepository.save(MemberFixture.MEMBER_ASH());

        Room pororoRoom = roomRepository.save(RoomFixture.ROOM_DOMAIN(pororo, LocalDateTime.now().plusDays(2)));
        Room ashRoom = roomRepository.save(RoomFixture.ROOM_DOMAIN(ash, LocalDateTime.now().plusDays(3)));

        Member joyson = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());
        long joysonId = joyson.getId();
        participationRepository.save(new Participation(pororoRoom, joysonId));
        participationRepository.save(new Participation(ashRoom, joysonId));

        RoomResponses response = roomService.findParticipatedRooms(joysonId);
        List<String> managerNames = getManagerNames(response);

        assertThat(managerNames).containsExactly("조경찬", "박민아");
    }

    @ParameterizedTest
    @EnumSource(RoomStatus.class)
    @DisplayName("로그인한 사용자가 자신이 참여하지 않은 방을 상태별로 마감일 임박순으로 조회할 수 있다.")
    void findRoomsWithRoomStatus_login_member(RoomStatus roomStatus) {
        Member pororo = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Member ash = memberRepository.save(MemberFixture.MEMBER_ASH());

        roomRepository.save(RoomFixture.ROOM_DOMAIN(pororo, LocalDateTime.now().plusDays(2), roomStatus));
        roomRepository.save(RoomFixture.ROOM_DOMAIN(ash, LocalDateTime.now().plusDays(3), roomStatus));

        RoomResponses response = roomService.findRoomsWithRoomStatus(pororo.getId(), 0, "all", roomStatus);
        List<String> managerNames = getManagerNames(response);

        assertThat(managerNames).containsExactly("박민아");
    }

    @ParameterizedTest
    @EnumSource(RoomStatus.class)
    @DisplayName("비로그인 사용자가 방을 상태별로 마감일 임박순으로 조회할 수 있다.")
    void findRoomsWithRoomStatus_non_login_member(RoomStatus roomStatus) {
        Member pororo = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Member ash = memberRepository.save(MemberFixture.MEMBER_ASH());

        roomRepository.save(RoomFixture.ROOM_DOMAIN(pororo, LocalDateTime.now().plusDays(2), roomStatus));
        roomRepository.save(RoomFixture.ROOM_DOMAIN(ash, LocalDateTime.now().plusDays(3), roomStatus));

        AuthInfo anonymous = AuthInfo.getAnonymous();

        RoomResponses response = roomService.findRoomsWithRoomStatus(anonymous.getId(), 0, "all", roomStatus);
        List<String> managerNames = getManagerNames(response);

        assertThat(managerNames).containsExactly("조경찬", "박민아");
    }

    @ParameterizedTest
    @CsvSource(value = {"0, false", "1, true"})
    @DisplayName("방을 조회할 때 전달받은 페이지가 마지막 페이지인지 판별할 수 있다.")
    void isLastPage(int pageNumber, boolean expected) {
        Member pororo = memberRepository.save(MemberFixture.MEMBER_PORORO());
        for (int i = 0; i < 9; i++) {
            roomRepository.save(RoomFixture.ROOM_DOMAIN(pororo));
        }

        AuthInfo anonymous = AuthInfo.getAnonymous();
        RoomResponses response = roomService.findRoomsWithRoomStatus(anonymous.getId(), pageNumber, "all", RoomStatus.OPEN);

        assertThat(response.isLastPage()).isEqualTo(expected);
    }

    @Test
    @DisplayName("방을 생성한 방장의 참여 상태는 MANAGER다.")
    void create() {
        Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
        RoomCreateRequest request = RoomFixture.ROOM_CREATE_REQUEST();
        RoomResponse response = roomService.create(manager.getId(), request);

        Optional<Participation> participation = participationRepository.findByRoomIdAndMemberId(response.id(), manager.getId());

        assertAll(
                () -> assertThat(response.manager()).isEqualTo(manager.getName()),
                () -> assertThat(participation.isPresent()).isTrue(),
                () -> assertThat(participation.get().getStatus()).isEqualTo(ParticipationStatus.MANAGER)
        );
    }

    @Test
    @DisplayName("방을 삭제할 수 있다.")
    void delete() {
        Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());

        RoomCreateRequest request = RoomFixture.ROOM_CREATE_REQUEST();
        RoomResponse response = roomService.create(manager.getId(), request);

        long roomId = response.id();
        roomService.delete(roomId, manager.getId());

        assertThat(roomRepository.findById(room.getId())).isEmpty();
    }

    @Test
    @DisplayName("방을 생성한 유저가 아닌 사람이 방을 삭제하려고 하면 예외가 발생한다.")
    void invalidDelete() {
        Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
        Room room = roomRepository.save(RoomFixture.ROOM_DOMAIN(manager));

        Member member = memberRepository.save(MemberFixture.MEMBER_PORORO());

        assertThatThrownBy(() -> roomService.delete(room.getId(), member.getId()))
                .asInstanceOf(InstanceOfAssertFactories.type(CoreaException.class))
                .extracting(CoreaException::getExceptionType)
                .isEqualTo(ExceptionType.ROOM_DELETION_AUTHORIZATION_ERROR);
    }

    @Test
    @DisplayName("본인을 제외하고 방에 참여한 사람의 정보를 최대 6명까지 가져온다.")
    void findParticipants() {
        Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
        Room room = roomRepository.save(RoomFixture.ROOM_DOMAIN(manager));
        participationRepository.save(new Participation(room, manager.getId()));

        List<Member> members = memberRepository.saveAll(MemberFixture.SEVEN_MEMBERS());

        participationRepository.save(new Participation(room, manager));
        participationRepository.saveAll(members.stream().map(member -> new Participation(room, member)).toList());

        matchResultRepository.saveAll(members.stream().map(member -> MatchResultFixture.MATCH_RESULT_DOMAIN(room.getId(), manager, member)).toList());
        matchResultRepository.save(MatchResultFixture.MATCH_RESULT_DOMAIN(room.getId(), members.get(0), manager));

        RoomParticipantResponses participants = roomService.findParticipants(room.getId(), manager.getId());

        assertThat(participants.participants()).hasSize(6);
        assertThat(participants.size()).isEqualTo(6);
    }

    private List<String> getManagerNames(RoomResponses response) {
        return response.rooms()
                .stream()
                .map(RoomResponse::manager)
                .toList();
    }
}
