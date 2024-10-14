package corea.room.service;

import config.ServiceTest;
import corea.auth.domain.AuthInfo;
import corea.fixture.MemberFixture;
import corea.fixture.RoomFixture;
import corea.member.domain.Member;
import corea.member.repository.MemberRepository;
import corea.room.domain.RoomStatus;
import corea.room.dto.RoomResponse;
import corea.room.dto.RoomResponses;
import corea.room.repository.RoomRepository;
import org.junit.jupiter.api.DisplayName;
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
    private MemberRepository memberRepository;

    @Autowired
    private RoomInquiryService roomInquiryService;

    @Autowired
    private RoomRepository roomRepository;

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

    private List<String> getManagerNames(RoomResponses response) {
        return response.rooms()
                .stream()
                .map(RoomResponse::manager)
                .toList();
    }
}
