package corea.room.service;

import config.ServiceTest;
import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.fixture.MemberFixture;
import corea.fixture.RoomFixture;
import corea.matchresult.domain.FailedMatching;
import corea.matchresult.repository.FailedMatchingRepository;
import corea.member.domain.Member;
import corea.member.domain.MemberRole;
import corea.member.repository.MemberRepository;
import corea.participation.domain.Participation;
import corea.participation.domain.ParticipationStatus;
import corea.participation.repository.ParticipationRepository;
import corea.room.domain.Room;
import corea.room.dto.RoomResponse;
import corea.room.dto.RoomResponses;
import corea.room.repository.RoomRepository;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@ServiceTest
class RoomDetailsInquiryServiceTest {

    @Autowired
    private RoomDetailsInquiryService roomDetailsInquiryService;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ParticipationRepository participationRepository;

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
        @DisplayName("조회하는 방이 존재하지 않는다면 예외가 발생한다.")
        void roomNotFound() {
            assertThatThrownBy(() -> roomDetailsInquiryService.findOne(0, member.getId()))
                    .asInstanceOf(InstanceOfAssertFactories.type(CoreaException.class))
                    .extracting(CoreaException::getExceptionType)
                    .isEqualTo(ExceptionType.ROOM_NOT_FOUND);
        }

        @Test
        @DisplayName("조회하는 방에 참여했다면 참여자이다.")
        void participated() {
            participationRepository.save(new Participation(room, member, MemberRole.BOTH, room.getMatchingSize()));

            RoomResponse response = roomDetailsInquiryService.findOne(room.getId(), member.getId());

            assertThat(response.participationStatus()).isEqualTo(ParticipationStatus.PARTICIPATED);
        }

        @Test
        @DisplayName("조회하는 방에 참여하지 않았다면 참여자가 아니다.")
        void not_participated() {
            RoomResponse response = roomDetailsInquiryService.findOne(room.getId(), member.getId());

            assertThat(response.participationStatus()).isEqualTo(ParticipationStatus.NOT_PARTICIPATED);
        }
    }

    @Nested
    @DisplayName("참여 중인 방들을 조회할 수 있다.")
    class ParticipatedRooms {

        private Member pororo;
        private Member ash;
        private Member movin;
        private Member joysun;

        @BeforeEach
        void setUp() {
            pororo = memberRepository.save(MemberFixture.MEMBER_PORORO());
            ash = memberRepository.save(MemberFixture.MEMBER_ASH());
            movin = memberRepository.save(MemberFixture.MEMBER_MOVIN());
            joysun = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());
        }

        @Test
        @DisplayName("현재 로그인한 멤버가 참여 중인 방을 리뷰 마감일이 임박한 순으로 볼 수 있다.")
        void findParticipatedRooms() {
            Room pororoRoom = roomRepository.save(RoomFixture.ROOM_DOMAIN(pororo, LocalDateTime.now().plusDays(2)));
            Room ashRoom = roomRepository.save(RoomFixture.ROOM_DOMAIN(ash, LocalDateTime.now().plusDays(3)));

            participationRepository.save(new Participation(pororoRoom, joysun, MemberRole.BOTH, pororoRoom.getMatchingSize()));
            participationRepository.save(new Participation(ashRoom, joysun, MemberRole.BOTH, ashRoom.getMatchingSize()));

            RoomResponses response = roomDetailsInquiryService.findParticipatedRooms(joysun.getId(), false);
            List<String> managerNames = getManagerNames(response);

            assertThat(managerNames).containsExactly("조경찬", "박민아");
        }

        @Test
        @DisplayName("현재 로그인한 멤버가 참여 중인 방을 볼 때, 종료된 방을 포함하지 않을 수 있다.")
        void findParticipatedRoomsWithoutClosed() {
            Room pororoRoom = roomRepository.save(RoomFixture.ROOM_DOMAIN(pororo, LocalDateTime.now().plusDays(2)));
            Room ashRoom = roomRepository.save(RoomFixture.ROOM_DOMAIN(ash, LocalDateTime.now().plusDays(3)));
            Room movinRoom = roomRepository.save(RoomFixture.ROOM_DOMAIN_WITH_CLOSED(movin));

            participationRepository.save(new Participation(pororoRoom, joysun, MemberRole.BOTH, pororoRoom.getMatchingSize()));
            participationRepository.save(new Participation(ashRoom, joysun, MemberRole.BOTH, ashRoom.getMatchingSize()));
            participationRepository.save(new Participation(movinRoom, joysun, MemberRole.BOTH, ashRoom.getMatchingSize()));

            RoomResponses response = roomDetailsInquiryService.findParticipatedRooms(joysun.getId(), false);
            List<String> managerNames = getManagerNames(response);

            assertThat(managerNames).containsExactly("조경찬", "박민아");
        }

        @Test
        @DisplayName("현재 로그인한 멤버가 참여 중인 방을 볼 때, 종료된 방을 포함할 수 있다.")
        void findParticipatedRoomsWithClosed() {
            Room pororoRoom = roomRepository.save(RoomFixture.ROOM_DOMAIN(pororo, LocalDateTime.now().plusDays(2)));
            Room ashRoom = roomRepository.save(RoomFixture.ROOM_DOMAIN(ash, LocalDateTime.now().plusDays(3)));
            Room movinRoom = roomRepository.save(RoomFixture.ROOM_DOMAIN_WITH_CLOSED(movin));

            participationRepository.save(new Participation(pororoRoom, joysun, MemberRole.BOTH, pororoRoom.getMatchingSize()));
            participationRepository.save(new Participation(ashRoom, joysun, MemberRole.BOTH, ashRoom.getMatchingSize()));
            participationRepository.save(new Participation(movinRoom, joysun, MemberRole.BOTH, ashRoom.getMatchingSize()));

            RoomResponses response = roomDetailsInquiryService.findParticipatedRooms(joysun.getId(), true);
            List<String> managerNames = getManagerNames(response);

            assertThat(managerNames).containsExactly("조경찬", "박민아", "김현중");
        }

        private List<String> getManagerNames(RoomResponses response) {
            return response.rooms()
                    .stream()
                    .map(RoomResponse::manager)
                    .toList();
        }
    }

    @Nested
    @DisplayName("방 매칭이 실패 했을 경우 실패한 원인에 대해 알 수 있다.")
    class MatchingFailedRoom {

        @Autowired
        private FailedMatchingRepository failedMatchingRepository;

        private Room room;
        private Room closedRoom;
        private Room closedAndMatchFailedRoom;

        @BeforeEach
        void setUp() {
            Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
            room = roomRepository.save(RoomFixture.ROOM_DOMAIN(manager));
            closedRoom = roomRepository.save(RoomFixture.ROOM_DOMAIN_WITH_CLOSED(manager));
            closedAndMatchFailedRoom = roomRepository.save(RoomFixture.ROOM_DOMAIN_WITH_CLOSED(manager));
        }

        @Test
        @DisplayName("방 참여자의 수가 최소 매칭 인원보다 작아 매칭이 진행되지 않았다면 메세지를 통해 원인을 파악할 수 있다.")
        void participant_size_lack() {
            Member member = memberRepository.save(MemberFixture.MEMBER_PORORO());
            participationRepository.save(new Participation(room, member, MemberRole.BOTH, room.getMatchingSize()));

            failedMatchingRepository.save(new FailedMatching(room.getId(), ExceptionType.PARTICIPANT_SIZE_LACK));
            RoomResponse response = roomDetailsInquiryService.findOne(room.getId(), member.getId());

            assertThat(response.message()).isEqualTo("방의 최소 참여 인원보다 참가자가 부족하여 매칭이 진행되지 않았습니다.");
        }

        @Test
        @DisplayName("참여중 탭에서 방을 보여줄 때 종료된 방을 제외하고 매칭이 실패한 원인 메시지를 같이 전달한다.")
        void matchFailedParticipatedRoom_excludeClosed() {
            Member member = memberRepository.save(MemberFixture.MEMBER_PORORO());
            participationRepository.save(new Participation(room, member, MemberRole.BOTH, room.getMatchingSize()));
            participationRepository.save(new Participation(closedRoom, member, MemberRole.BOTH, closedRoom.getMatchingSize()));
            participationRepository.save(new Participation(closedAndMatchFailedRoom, member, MemberRole.BOTH, closedAndMatchFailedRoom.getMatchingSize()));

            failedMatchingRepository.save(new FailedMatching(room.getId(), ExceptionType.ROOM_STATUS_INVALID));
            failedMatchingRepository.save(new FailedMatching(closedAndMatchFailedRoom.getId(), ExceptionType.ROOM_STATUS_INVALID));
            RoomResponses roomResponses = roomDetailsInquiryService.findParticipatedRooms(member.getId(), false);

            List<RoomResponse> rooms = roomResponses.rooms();
            String expectedMessage = "방이 이미 매칭 중이거나, 매칭이 완료되어 더 이상 매칭을 진행할 수 없는 상태입니다.";

            List<RoomResponse> failedRooms = rooms.stream()
                    .filter(roomResponse -> expectedMessage.equals(roomResponse.message()))
                    .toList();

            assertThat(failedRooms).hasSize(1);
        }

        @Test
        @DisplayName("마이페이지에서 참여했던 방을 보여줄 때 매칭이 실패한 원인 메시지를 같이 전달한다.")
        void matchFailedParticipatedRoom_includeClosed() {
            Member member = memberRepository.save(MemberFixture.MEMBER_PORORO());
            participationRepository.save(new Participation(room, member, MemberRole.BOTH, room.getMatchingSize()));
            participationRepository.save(new Participation(closedRoom, member, MemberRole.BOTH, closedRoom.getMatchingSize()));
            participationRepository.save(new Participation(closedAndMatchFailedRoom, member, MemberRole.BOTH, closedAndMatchFailedRoom.getMatchingSize()));

            failedMatchingRepository.save(new FailedMatching(room.getId(), ExceptionType.ROOM_STATUS_INVALID));
            failedMatchingRepository.save(new FailedMatching(closedAndMatchFailedRoom.getId(), ExceptionType.ROOM_STATUS_INVALID));
            RoomResponses roomResponses = roomDetailsInquiryService.findParticipatedRooms(member.getId(), true);

            List<RoomResponse> rooms = roomResponses.rooms();
            String expectedMessage = "방이 이미 매칭 중이거나, 매칭이 완료되어 더 이상 매칭을 진행할 수 없는 상태입니다.";

            List<RoomResponse> failedRooms = rooms.stream()
                    .filter(roomResponse -> expectedMessage.equals(roomResponse.message()))
                    .toList();

            assertAll(
                    () -> assertThat(failedRooms).hasSize(2),
                    () -> assertThat(failedRooms.get(0).message()).isEqualTo(expectedMessage),
                    () -> assertThat(failedRooms.get(1).message()).isEqualTo(expectedMessage)
            );
        }
    }
}
