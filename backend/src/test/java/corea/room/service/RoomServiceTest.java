package corea.room.service;

import config.ServiceTest;
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
import corea.room.dto.RoomCreateRequest;
import corea.room.dto.RoomParticipantResponse;
import corea.room.dto.RoomParticipantResponses;
import corea.room.dto.RoomResponse;
import corea.room.repository.RoomRepository;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@ServiceTest
class RoomServiceTest {

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Nested
    @DisplayName("방을 생성, 수정 및 삭제할 수 있다.")
    class RoomWriter {

        private Member manager;

        @BeforeEach
        void setUp() {
            manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
        }

        @Test
        @DisplayName("방을 생성할 수 있다.")
        void create() {
            roomService.create(manager.getId(), RoomFixture.ROOM_CREATE_REQUEST());

            assertThat(roomRepository.findAll()).hasSize(1);
        }

        @Test
        @DisplayName("방을 만든 사람은 방장이다.")
        void manager() {
            RoomResponse response = roomService.create(manager.getId(), RoomFixture.ROOM_CREATE_REQUEST());

            assertThat(response.participationStatus()).isEqualTo(ParticipationStatus.MANAGER);
        }

        @Test
        @DisplayName("방을 생성할 때 모집 마감 시간은 현재 시간보다 이후가 아니라면 예외가 발생한다.")
        void invalidRecruitmentDeadline() {
            RoomCreateRequest request = RoomFixture.ROOM_CREATE_REQUEST_WITH_RECRUITMENT_DEADLINE(LocalDateTime.now().minusMinutes(1));

            assertThatThrownBy(() -> roomService.create(manager.getId(), request))
                    .asInstanceOf(InstanceOfAssertFactories.type(CoreaException.class))
                    .extracting(CoreaException::getExceptionType)
                    .isEqualTo(ExceptionType.INVALID_RECRUITMENT_DEADLINE);
        }

        @Test
        @DisplayName("방을 생성할 때 리뷰 마감 시간은 모집 마감 시간보다 이후가 아니라면 예외가 발생한다.")
        void invalidReviewDeadline() {
            RoomCreateRequest request = RoomFixture.ROOM_CREATE_REQUEST(
                    LocalDateTime.now().plusHours(2),
                    LocalDateTime.now().plusHours(1));

            assertThatThrownBy(() -> roomService.create(manager.getId(), request))
                    .asInstanceOf(InstanceOfAssertFactories.type(CoreaException.class))
                    .extracting(CoreaException::getExceptionType)
                    .isEqualTo(ExceptionType.INVALID_REVIEW_DEADLINE);
        }

        @Test
        @DisplayName("방을 삭제할 수 있다.")
        void delete() {
            RoomResponse response = roomService.create(manager.getId(), RoomFixture.ROOM_CREATE_REQUEST());

            long roomId = response.id();
            roomService.delete(roomId, manager.getId());

            assertThat(roomRepository.findById(roomId)).isEmpty();
        }

        @Test
        @DisplayName("방이 닫힌 상태면 삭제할 수 없다.")
        void throw_exception_when_delete_with_room_is_closed() {
            Room room = roomRepository.save(RoomFixture.ROOM_DOMAIN_WITH_CLOSED(manager));
            assertThatThrownBy(() -> roomService.delete(room.getId(), manager.getId()))
                    .isInstanceOf(CoreaException.class);
        }

        @Test
        @DisplayName("방이 진행 상태면 삭제할 수 없다.")
        void throw_exception_when_delete_with_room_is_progress() {
            Room room = roomRepository.save(RoomFixture.ROOM_DOMAIN_WITH_PROGRESS(manager));
            assertThatThrownBy(() -> roomService.delete(room.getId(), manager.getId()))
                    .isInstanceOf(CoreaException.class);
        }

        @Test
        @DisplayName("방을 생성한 유저가 아닌 사람이 방을 삭제하려고 하면 예외가 발생한다.")
        void invalidDelete() {
            RoomResponse response = roomService.create(manager.getId(), RoomFixture.ROOM_CREATE_REQUEST());

            Member member = memberRepository.save(MemberFixture.MEMBER_PORORO());

            assertThatThrownBy(() -> roomService.delete(response.id(), member.getId()))
                    .asInstanceOf(InstanceOfAssertFactories.type(CoreaException.class))
                    .extracting(CoreaException::getExceptionType)
                    .isEqualTo(ExceptionType.ROOM_MODIFY_AUTHORIZATION_ERROR);
        }

        @Test
        @DisplayName("방의 매니저가 아니면 수정 시, 예외를 발생합니다.")
        void throw_exception_when_update_with_not_manager() {
            RoomResponse response = roomService.create(manager.getId(), RoomFixture.ROOM_CREATE_REQUEST());

            assertThatThrownBy(() -> roomService.update(-1, RoomFixture.ROOM_UPDATE_REQUEST(response.id())))
                    .isInstanceOf(CoreaException.class);
        }

        @Test
        @DisplayName("방이 닫힌 상태면 수정할 수 없다.")
        void throw_exception_when_update_with_room_is_closed() {
            Room room = roomRepository.save(RoomFixture.ROOM_DOMAIN_WITH_CLOSED(manager));
            assertThatThrownBy(() -> roomService.update(room.getId(), RoomFixture.ROOM_UPDATE_REQUEST(room.getId())))
                    .isInstanceOf(CoreaException.class);
        }

        @Test
        @DisplayName("방이 진행 상태면 수정할 수 없다.")
        void throw_exception_when_update_with_room_is_progress() {
            Room room = roomRepository.save(RoomFixture.ROOM_DOMAIN_WITH_PROGRESS(manager));
            assertThatThrownBy(() -> roomService.update(room.getId(), RoomFixture.ROOM_UPDATE_REQUEST(room.getId())))
                    .isInstanceOf(CoreaException.class);
        }

        @Test
        @DisplayName("존재하지 않는 방이면, 예외를 발생합니다.")
        void throw_exception_when_update_with_not_exist_room() {
            roomService.create(manager.getId(), RoomFixture.ROOM_CREATE_REQUEST());

            assertThatThrownBy(() -> roomService.update(manager.getId(), RoomFixture.ROOM_UPDATE_REQUEST(-1)))
                    .isInstanceOf(CoreaException.class);
        }
    }

    @Nested
    @DisplayName("방에 참여한 사람들에 정보를 알 수 있다.")
    class ParticipantsReader {

        @Autowired
        private MatchResultRepository matchResultRepository;

        @Autowired
        private ParticipationRepository participationRepository;

        private Member manager;
        private Room room;
        private Member pororo, ash, joyson, darr, choco, movin, tenten;

        @BeforeEach
        void setUp() {
            manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
            room = roomRepository.save(RoomFixture.ROOM_DOMAIN(manager));
            participationRepository.save(new Participation(room, manager, MemberRole.REVIEWER, ParticipationStatus.MANAGER, room.getMatchingSize()));

            pororo = memberRepository.save(MemberFixture.MEMBER_PORORO());
            ash = memberRepository.save(MemberFixture.MEMBER_ASH());
            joyson = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());
            darr = memberRepository.save(MemberFixture.MEMBER_DARR());
            choco = memberRepository.save(MemberFixture.MEMBER_CHOCO());
            movin = memberRepository.save(MemberFixture.MEMBER_MOVIN());
            tenten = memberRepository.save(MemberFixture.MEMBER_TENTEN());
        }

        @Test
        @DisplayName("방에 참여한 사람의 정보를 최대 6명까지 가져온다.")
        void findParticipants1() {
            participationRepository.save(new Participation(room, pororo, MemberRole.BOTH, 2));
            participationRepository.save(new Participation(room, ash, MemberRole.BOTH, 2));
            participationRepository.save(new Participation(room, joyson, MemberRole.BOTH, 2));
            participationRepository.save(new Participation(room, darr, MemberRole.BOTH, 2));
            participationRepository.save(new Participation(room, choco, MemberRole.BOTH, 2));
            participationRepository.save(new Participation(room, movin, MemberRole.BOTH, 2));
            participationRepository.save(new Participation(room, tenten, MemberRole.BOTH, 2));
            saveAllMatchResult();

            RoomParticipantResponses participants = roomService.findParticipants(room.getId(), manager.getId());

            assertAll(
                    () -> assertThat(participants.participants()).hasSize(6),
                    () -> assertThat(participants.size()).isEqualTo(7)
            );
        }

        @Test
        @DisplayName("본인을 제외하고 방에 참여한 사람의 정보를 가져온다.")
        void findParticipants2() {
            participationRepository.save(new Participation(room, pororo, MemberRole.BOTH, 2));
            participationRepository.save(new Participation(room, ash, MemberRole.BOTH, 2));
            participationRepository.save(new Participation(room, joyson, MemberRole.BOTH, 2));
            saveAllMatchResult();

            RoomParticipantResponses participants = roomService.findParticipants(room.getId(), pororo.getId());

            List<String> participantNames = getParticipantNames(participants);
            assertThat(participantNames).containsExactlyInAnyOrder("youngsu5582", "ashsty");
        }

        @Test
        @DisplayName("리뷰어를 제외하고 방에 참여한 사람의 정보를 가져온다.")
        void findParticipants3() {
            participationRepository.save(new Participation(room, pororo, MemberRole.BOTH, 2));
            participationRepository.save(new Participation(room, ash, MemberRole.BOTH, 2));
            participationRepository.save(new Participation(room, movin, MemberRole.REVIEWER, 2));
            saveAllMatchResult();

            RoomParticipantResponses participants = roomService.findParticipants(room.getId(), manager.getId());

            List<String> participantNames = getParticipantNames(participants);
            assertThat(participantNames).containsExactlyInAnyOrder("pororo", "ashsty");
        }

        @Test
        @DisplayName("pr을 제출하지 않은 사람을 제외하고 방에 참여한 사람의 정보를 가져온다.")
        void findParticipants4() {
            participationRepository.save(new Participation(room, pororo, MemberRole.BOTH, 2));
            participationRepository.save(new Participation(room, ash, MemberRole.BOTH, ParticipationStatus.PULL_REQUEST_NOT_SUBMITTED, 2));
            participationRepository.save(new Participation(room, joyson, MemberRole.BOTH, 2));
            saveAllMatchResult();

            RoomParticipantResponses participants = roomService.findParticipants(room.getId(), manager.getId());

            List<String> participantNames = getParticipantNames(participants);
            assertThat(participantNames).containsExactlyInAnyOrder("pororo", "youngsu5582");
        }

        @Test
        @DisplayName("본인, 리뷰어, pr 미제출자를 제외한 후, 방에 참여한 사람의 정보를 가져온다.")
        void findParticipants5() {
            participationRepository.save(new Participation(room, pororo, MemberRole.BOTH, 2));
            participationRepository.save(new Participation(room, joyson, MemberRole.REVIEWER, 2));
            participationRepository.save(new Participation(room, ash, MemberRole.BOTH, ParticipationStatus.PULL_REQUEST_NOT_SUBMITTED, 2));
            participationRepository.save(new Participation(room, choco, MemberRole.BOTH, 2));
            saveAllMatchResult();

            RoomParticipantResponses participants = roomService.findParticipants(room.getId(), pororo.getId());

            List<String> participantNames = getParticipantNames(participants);
            assertThat(participantNames).containsExactlyInAnyOrder("choco");
        }

        private void saveAllMatchResult() {
            List<Member> members = List.of(pororo, ash, joyson, darr, choco, movin, tenten);

            matchResultRepository.saveAll(members.stream()
                    .filter(this::isValidParticipant)
                    .map(member -> MatchResultFixture.MATCH_RESULT_DOMAIN(room.getId(), manager, member))
                    .toList());
        }

        private boolean isValidParticipant(Member member) {
            Optional<Participation> participationOpt = participationRepository.findByRoomIdAndMemberId(room.getId(), member.getId());

            if (participationOpt.isPresent()) {
                Participation participation = participationOpt.get();
                return !participation.isReviewer() && !participation.isPullRequestNotSubmitted();
            }
            return false;
        }

        private List<String> getParticipantNames(RoomParticipantResponses participants) {
            return participants.participants()
                    .stream()
                    .map(RoomParticipantResponse::username)
                    .toList();
        }
    }
}
