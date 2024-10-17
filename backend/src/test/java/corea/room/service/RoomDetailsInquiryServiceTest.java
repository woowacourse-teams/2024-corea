package corea.room.service;

import config.ServiceTest;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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
            RoomResponse response = roomDetailsInquiryService.findOne(room.getId(), member.getId());

            assertThat(response.message()).isEqualTo("방의 최소 참여 인원보다 참가자가 부족하여 매칭이 진행되지 않았습니다.");
        }
    }

    @Test
    @DisplayName("현재 로그인한 멤버가 참여 중인 방을 리뷰 마감일이 임박한 순으로 볼 수 있다.")
    void findParticipatedRooms() {
        Member pororo = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Member ash = memberRepository.save(MemberFixture.MEMBER_ASH());

        Room pororoRoom = roomRepository.save(RoomFixture.ROOM_DOMAIN(pororo, LocalDateTime.now().plusDays(2)));
        Room ashRoom = roomRepository.save(RoomFixture.ROOM_DOMAIN(ash, LocalDateTime.now().plusDays(3)));

        Member joyson = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());
        Long joysonId = joyson.getId();
        participationRepository.save(new Participation(pororoRoom, joyson, MemberRole.BOTH, pororoRoom.getMatchingSize()));
        participationRepository.save(new Participation(ashRoom, joyson, MemberRole.BOTH, ashRoom.getMatchingSize()));

        RoomResponses response = roomDetailsInquiryService.findParticipatedRooms(joysonId, false);
        List<String> managerNames = getManagerNames(response);

        assertThat(managerNames).containsExactly("조경찬", "박민아");
    }

    @Test
    @DisplayName("현재 로그인한 멤버가 참여 중인 방을 볼 때, 종료된 방을 포함하지 않을 수 있다.")
    void findParticipatedRoomsWithoutClosed() {
        Member pororo = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Member ash = memberRepository.save(MemberFixture.MEMBER_ASH());
        Member movin = memberRepository.save(MemberFixture.MEMBER_MOVIN());

        Room pororoRoom = roomRepository.save(RoomFixture.ROOM_DOMAIN(pororo, LocalDateTime.now().plusDays(2)));
        Room ashRoom = roomRepository.save(RoomFixture.ROOM_DOMAIN(ash, LocalDateTime.now().plusDays(3)));
        Room movinRoom = roomRepository.save(RoomFixture.ROOM_DOMAIN_WITH_CLOSED(movin));

        Member joyson = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());
        Long joysonId = joyson.getId();
        participationRepository.save(new Participation(pororoRoom, joyson, MemberRole.BOTH, pororoRoom.getMatchingSize()));
        participationRepository.save(new Participation(ashRoom, joyson, MemberRole.BOTH, ashRoom.getMatchingSize()));
        participationRepository.save(new Participation(movinRoom, joyson, MemberRole.BOTH, ashRoom.getMatchingSize()));

        RoomResponses response = roomDetailsInquiryService.findParticipatedRooms(joysonId, false);
        List<String> managerNames = getManagerNames(response);

        assertThat(managerNames).containsExactly("조경찬", "박민아");
    }

    @Test
    @DisplayName("현재 로그인한 멤버가 참여 중인 방을 볼 때, 종료된 방을 포함할 수 있다.")
    void findParticipatedRoomsWithClosed() {
        Member pororo = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Member ash = memberRepository.save(MemberFixture.MEMBER_ASH());
        Member movin = memberRepository.save(MemberFixture.MEMBER_MOVIN());

        Room pororoRoom = roomRepository.save(RoomFixture.ROOM_DOMAIN(pororo, LocalDateTime.now().plusDays(2)));
        Room ashRoom = roomRepository.save(RoomFixture.ROOM_DOMAIN(ash, LocalDateTime.now().plusDays(3)));
        Room movinRoom = roomRepository.save(RoomFixture.ROOM_DOMAIN_WITH_CLOSED(movin));

        Member joyson = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());
        Long joysonId = joyson.getId();
        participationRepository.save(new Participation(pororoRoom, joyson, MemberRole.BOTH, pororoRoom.getMatchingSize()));
        participationRepository.save(new Participation(ashRoom, joyson, MemberRole.BOTH, ashRoom.getMatchingSize()));
        participationRepository.save(new Participation(movinRoom, joyson, MemberRole.BOTH, ashRoom.getMatchingSize()));

        RoomResponses response = roomDetailsInquiryService.findParticipatedRooms(joysonId, true);
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
