package corea.room.service;

import config.ServiceTest;
import corea.auth.domain.AuthInfo;
import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.fixture.MatchResultFixture;
import corea.fixture.MemberFixture;
import corea.fixture.RoomFixture;
import corea.matchresult.repository.MatchResultRepository;
import corea.member.domain.Member;
import corea.member.domain.MemberRole;
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
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

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

    @Disabled
    @Test
    @DisplayName("방을 생성할 때 모집 마감 시간은 현재 시간보다 1시간 이후가 아니라면 예외가 발생한다.")
    void invalidRecruitmentDeadline() {
        Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());

        RoomCreateRequest request = RoomFixture.ROOM_CREATE_REQUEST_WITH_RECRUITMENT_DEADLINE(LocalDateTime.now().plusMinutes(59));

        assertThatThrownBy(() -> roomService.create(manager.getId(), request))
                .asInstanceOf(InstanceOfAssertFactories.type(CoreaException.class))
                .extracting(CoreaException::getExceptionType)
                .isEqualTo(ExceptionType.INVALID_RECRUITMENT_DEADLINE);
    }

    @Disabled
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
        participationRepository.save(new Participation(room, member, MemberRole.BOTH, room.getMatchingSize()));

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
    @DisplayName("현재 로그인한 멤버가 참여 중인 방을 리뷰 마감일이 임박한 순으로 보여준다.")
    void findParticipatedRooms() {
        Member pororo = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Member ash = memberRepository.save(MemberFixture.MEMBER_ASH());

        Room pororoRoom = roomRepository.save(RoomFixture.ROOM_DOMAIN(pororo, LocalDateTime.now().plusDays(2)));
        Room ashRoom = roomRepository.save(RoomFixture.ROOM_DOMAIN(ash, LocalDateTime.now().plusDays(3)));

        Member joyson = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());
        Long joysonId = joyson.getId();
        participationRepository.save(new Participation(pororoRoom, joyson, MemberRole.BOTH, pororoRoom.getMatchingSize()));
        participationRepository.save(new Participation(ashRoom, joyson, MemberRole.BOTH, ashRoom.getMatchingSize()));

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
    void create_participationStatus_manager() {
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

        assertThat(roomRepository.findById(roomId)).isEmpty();
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
        participationRepository.save(new Participation(room, manager));

        List<Member> members = memberRepository.saveAll(MemberFixture.SEVEN_MEMBERS());

        participationRepository.save(new Participation(room, manager));
        participationRepository.saveAll(members.stream().map(member -> new Participation(room, member, MemberRole.BOTH, 2)).toList());

        matchResultRepository.saveAll(members.stream().map(member -> MatchResultFixture.MATCH_RESULT_DOMAIN(room.getId(), manager, member)).toList());
        matchResultRepository.save(MatchResultFixture.MATCH_RESULT_DOMAIN(room.getId(), members.get(0), manager));

        RoomParticipantResponses participants = roomService.findParticipants(room.getId(), manager.getId());

        assertThat(participants.participants()).hasSize(6);
        assertThat(participants.size()).isEqualTo(6);
    }

    @Transactional
    @RepeatedTest(10)
    @DisplayName("Pull Request를 제출하지 않은 사람은 방 참여 목록 인원에 포함하지 않는다.")
    void findParticipants_withNoPullRequestParticipants() {
        Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
        Member pullRequestNotSubmittedMember = memberRepository.save(MemberFixture.MEMBER_ASH());
        Room room = roomRepository.save(RoomFixture.ROOM_DOMAIN(manager));

        participationRepository.save(new Participation(room, manager));
        Participation participation = participationRepository.save(new Participation(room, pullRequestNotSubmittedMember, MemberRole.BOTH, 2));
        participation.invalidate();

        List<Member> members = memberRepository.saveAll(MemberFixture.SEVEN_MEMBERS());

        participationRepository.saveAll(members.stream().map(member -> new Participation(room, member, MemberRole.BOTH, 2)).toList());

        matchResultRepository.saveAll(members.stream().map(member -> MatchResultFixture.MATCH_RESULT_DOMAIN(room.getId(), manager, member)).toList());
        matchResultRepository.save(MatchResultFixture.MATCH_RESULT_DOMAIN(room.getId(), members.get(0), manager));


        RoomParticipantResponses participants = assertDoesNotThrow(() -> roomService.findParticipants(room.getId(), manager.getId()));

        assertAll(
                () -> assertThat(participants.participants()).hasSize(6),
                () -> assertThat(participants.size()).isEqualTo(6)
        );
    }

    private List<String> getManagerNames(RoomResponses response) {
        return response.rooms()
                .stream()
                .map(RoomResponse::manager)
                .toList();
    }
}
