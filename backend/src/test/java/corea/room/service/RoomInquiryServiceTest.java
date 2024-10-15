package corea.room.service;

import config.ServiceTest;
import corea.auth.domain.AuthInfo;
import corea.fixture.MemberFixture;
import corea.fixture.RoomFixture;
import corea.member.domain.Member;
import corea.member.domain.MemberRole;
import corea.member.repository.MemberRepository;
import corea.participation.domain.Participation;
import corea.participation.repository.ParticipationRepository;
import corea.room.domain.Room;
import corea.room.domain.RoomStatus;
import corea.room.dto.RoomResponse;
import corea.room.dto.RoomResponses;
import corea.room.repository.RoomRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ServiceTest
public class RoomInquiryServiceTest {

    @Autowired
    private RoomInquiryService roomInquiryService;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ParticipationRepository participationRepository;

    @ParameterizedTest
    @EnumSource(RoomStatus.class)
    @DisplayName("로그인한 사용자가 자신이 참여하지 않은 방을 상태별로 마감일 임박순으로 조회할 수 있다.")
    void findRoomsWithRoomStatus_login_member(RoomStatus roomStatus) {
        Member pororo = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Member ash = memberRepository.save(MemberFixture.MEMBER_ASH());

        roomRepository.save(RoomFixture.ROOM_DOMAIN(pororo, LocalDateTime.now().plusDays(2), roomStatus));
        roomRepository.save(RoomFixture.ROOM_DOMAIN(ash, LocalDateTime.now().plusDays(3), roomStatus));

        RoomResponses response = roomInquiryService.findRoomsWithRoomStatus(pororo.getId(), 0, "all", roomStatus);
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

        RoomResponses response = roomInquiryService.findRoomsWithRoomStatus(anonymous.getId(), 0, "all", roomStatus);
        List<String> managerNames = getManagerNames(response);

        assertThat(managerNames).containsExactly("조경찬", "박민아");
    }

    private List<String> getManagerNames(RoomResponses response) {
        return response.rooms()
                .stream()
                .map(RoomResponse::manager)
                .toList();
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

        RoomResponses response = roomInquiryService.findParticipatedRooms(joysonId, false);
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

        RoomResponses response = roomInquiryService.findParticipatedRooms(joysonId, false);
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

        RoomResponses response = roomInquiryService.findParticipatedRooms(joysonId, true);
        List<String> managerNames = getManagerNames(response);

        assertThat(managerNames).containsExactly("조경찬", "박민아", "김현중");
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
        RoomResponses response = roomInquiryService.findRoomsWithRoomStatus(anonymous.getId(), pageNumber, "all", RoomStatus.OPEN);

        assertThat(response.isLastPage()).isEqualTo(expected);
    }
}
