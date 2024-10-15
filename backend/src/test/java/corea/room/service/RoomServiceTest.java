package corea.room.service;

import config.ServiceTest;
import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.fixture.MatchResultFixture;
import corea.fixture.MemberFixture;
import corea.fixture.RoomFixture;
import corea.matchresult.domain.FailedMatching;
import corea.matchresult.repository.FailedMatchingRepository;
import corea.matchresult.repository.MatchResultRepository;
import corea.member.domain.Member;
import corea.member.domain.MemberRole;
import corea.member.repository.MemberRepository;
import corea.participation.domain.Participation;
import corea.participation.domain.ParticipationStatus;
import corea.participation.repository.ParticipationRepository;
import corea.room.domain.Room;
import corea.room.dto.RoomCreateRequest;
import corea.room.dto.RoomParticipantResponses;
import corea.room.dto.RoomResponse;
import corea.room.repository.RoomRepository;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

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

    @Nested
    @DisplayName("방을 생성 및 삭제할 수 있다.")
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

        @Disabled
        @Test
        @DisplayName("방을 생성할 때 모집 마감 시간은 현재 시간보다 1시간 이후가 아니라면 예외가 발생한다.")
        void invalidRecruitmentDeadline() {
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
            RoomCreateRequest request = RoomFixture.ROOM_CREATE_REQUEST(LocalDateTime.now().plusHours(2), LocalDateTime.now().plusDays(1));

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
        @DisplayName("방을 생성한 유저가 아닌 사람이 방을 삭제하려고 하면 예외가 발생한다.")
        void invalidDelete() {
            RoomResponse response = roomService.create(manager.getId(), RoomFixture.ROOM_CREATE_REQUEST());

            Member member = memberRepository.save(MemberFixture.MEMBER_PORORO());

            assertThatThrownBy(() -> roomService.delete(response.id(), member.getId()))
                    .asInstanceOf(InstanceOfAssertFactories.type(CoreaException.class))
                    .extracting(CoreaException::getExceptionType)
                    .isEqualTo(ExceptionType.ROOM_DELETION_AUTHORIZATION_ERROR);
        }
    }

    @Nested
    @DisplayName("방을 조회할 수 있다.")
    class RoomReader {

        private Member manager;
        private Member member;
        private Room room;

        @BeforeEach
        void setUp() {
            manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
            member = memberRepository.save(MemberFixture.MEMBER_PORORO());
            room = roomRepository.save(RoomFixture.ROOM_DOMAIN(manager));
        }

        @Test
        @DisplayName("조회하는 방을 만든 사람은 방장이다.")
        void manager() {
            RoomResponse response = roomService.create(manager.getId(), RoomFixture.ROOM_CREATE_REQUEST());

            assertThat(response.participationStatus()).isEqualTo(ParticipationStatus.MANAGER);
        }

        @Test
        @DisplayName("조회하는 방에 참여했다면 참여자이다.")
        void participated() {
            participationRepository.save(new Participation(room, member, MemberRole.BOTH, room.getMatchingSize()));

            RoomResponse response = roomService.findOne(room.getId(), member.getId());

            assertThat(response.participationStatus()).isEqualTo(ParticipationStatus.PARTICIPATED);
        }

        @Test
        @DisplayName("조회하는 방에 참여하지 않았다면 참여자가 아니다.")
        void not_participated() {
            RoomResponse response = roomService.findOne(room.getId(), member.getId());

            assertThat(response.participationStatus()).isEqualTo(ParticipationStatus.NOT_PARTICIPATED);
        }
    }

    @Nested
    @DisplayName("방 매칭이 실패 했을 경우 실패한 원인에 대해 알 수 있다.")
    class MatchingFailedRoom {

        @Autowired
        private FailedMatchingRepository failedMatchingRepository;

        private Member manager;
        private Room room;

        @BeforeEach
        void setUp() {
            manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
            room = roomRepository.save(RoomFixture.ROOM_DOMAIN(manager));
        }

        @Test
        @DisplayName("방 참여자의 수가 최소 매칭 인원보다 작다면 매칭이 진행되지 않았다면 메세지를 통해 원인을 파악할 수 있다.")
        void participant_size_lack() {
            Member member = memberRepository.save(MemberFixture.MEMBER_PORORO());
            participationRepository.save(new Participation(room, member, MemberRole.BOTH, room.getMatchingSize()));

            failedMatchingRepository.save(new FailedMatching(room.getId(), ExceptionType.PARTICIPANT_SIZE_LACK));
            RoomResponse response = roomService.findOne(room.getId(), member.getId());

            assertThat(response.message()).isEqualTo("방의 최소 참여 인원보다 참가자가 부족하여 매칭이 진행되지 않았습니다.");
        }
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
}
