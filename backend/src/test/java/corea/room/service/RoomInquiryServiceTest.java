package corea.room.service;

import config.ServiceTest;
import corea.auth.domain.AuthInfo;
import corea.fixture.MemberFixture;
import corea.fixture.RoomFixture;
import corea.member.domain.Member;
import corea.member.domain.MemberRole;
import corea.member.repository.MemberRepository;
import corea.participation.domain.Participation;
import corea.participation.domain.ParticipationStatus;
import corea.participation.repository.ParticipationRepository;
import corea.room.domain.Room;
import corea.room.domain.RoomClassification;
import corea.room.domain.RoomStatus;
import corea.room.dto.RoomResponse;
import corea.room.dto.RoomResponses;
import corea.room.dto.RoomSearchResponses;
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
    @DisplayName("로그인한 사용자가 방을 상태별로 마감일 임박순으로 조회할 수 있다.")
    void findRoomsWithRoomStatus_login_member(RoomStatus roomStatus) {
        Member pororo = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Member ash = memberRepository.save(MemberFixture.MEMBER_ASH());

        roomRepository.save(RoomFixture.ROOM_DOMAIN(pororo, LocalDateTime.now().plusDays(2), roomStatus));
        Room ashRoom = roomRepository.save(RoomFixture.ROOM_DOMAIN(ash, LocalDateTime.now().plusDays(3), roomStatus));
        participationRepository.save(new Participation(ashRoom, ash, MemberRole.REVIEWER, ParticipationStatus.MANAGER, ashRoom.getMatchingSize()));

        RoomResponses response = roomInquiryService.findRoomsWithRoomStatus(pororo.getId(), 0, "all", roomStatus);
        List<String> managerNames = getManagerNames(response);

        assertThat(managerNames).containsExactly("pororo", "ashsty");
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

        assertThat(managerNames).containsExactly("pororo", "ashsty");
    }

    private List<String> getManagerNames(RoomResponses response) {
        return response.rooms()
                .stream()
                .map(RoomResponse::manager)
                .toList();
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

    @Test
    @DisplayName("검색 조건과 정확하게 일치하는 방을 검색할 수 있다.")
    void search() {
        Member pororo = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Member ash = memberRepository.save(MemberFixture.MEMBER_ASH());
        Member darr = memberRepository.save(MemberFixture.MEMBER_DARR());

        roomRepository.save(RoomFixture.ROOM_DOMAIN(pororo, LocalDateTime.now().plusDays(2), RoomStatus.OPEN));
        roomRepository.save(RoomFixture.ROOM_DOMAIN_WITH_DIFFERENT_TITLE(ash, LocalDateTime.now().plusDays(3), RoomStatus.CLOSE));
        roomRepository.save(RoomFixture.ROOM_DOMAIN_WITH_DIFFERENT_TITLE(darr, LocalDateTime.now().plusDays(4), RoomStatus.PROGRESS));

        RoomSearchResponses response = roomInquiryService.search(pororo.getId(), RoomStatus.CLOSE, RoomClassification.BACKEND, "코틀린");
        String managerName = getManagerNames(response).get(0);

        assertThat(managerName).isEqualTo("ashsty");
    }

    @ParameterizedTest
    @EnumSource(RoomStatus.class)
    @DisplayName("로그인한 사용자가 상태별 방을 모집 마감일이 오래 남은 순으로 검색할 수 있다.")
    void searchRoomsWithRoomStatus_login_member(RoomStatus roomStatus) {
        Member pororo = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Member ash = memberRepository.save(MemberFixture.MEMBER_ASH());
        Member darr = memberRepository.save(MemberFixture.MEMBER_DARR());

        roomRepository.save(RoomFixture.ROOM_DOMAIN(pororo, LocalDateTime.now().plusDays(2), roomStatus));
        roomRepository.save(RoomFixture.ROOM_DOMAIN(darr, LocalDateTime.now().plusDays(3), roomStatus));

        Room ashRoom = roomRepository.save(RoomFixture.ROOM_DOMAIN_WITH_DIFFERENT_TITLE(ash, LocalDateTime.now().plusDays(3), roomStatus));
        participationRepository.save(new Participation(ashRoom, ash, MemberRole.REVIEWER, ParticipationStatus.MANAGER, ashRoom.getMatchingSize()));

        RoomSearchResponses response = roomInquiryService.search(pororo.getId(), roomStatus, RoomClassification.ALL, "자바");
        List<String> managerNames = getManagerNames(response);

        assertThat(managerNames).containsExactly("darr", "pororo");
    }

    private List<String> getManagerNames(RoomSearchResponses response) {
        return response.rooms()
                .stream()
                .map(RoomResponse::manager)
                .toList();
    }

    @ParameterizedTest
    @EnumSource(RoomStatus.class)
    @DisplayName("비로그인 사용자가 상태별 방을 모집 마감일이 오래 남은 순으로 검색할 수 있다.")
    void searchRoomsWithRoomStatus_non_login_member(RoomStatus roomStatus) {
        Member pororo = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Member darr = memberRepository.save(MemberFixture.MEMBER_DARR());
        Member ash = memberRepository.save(MemberFixture.MEMBER_ASH());

        roomRepository.save(RoomFixture.ROOM_DOMAIN(pororo, LocalDateTime.now().plusDays(2), roomStatus));
        roomRepository.save(RoomFixture.ROOM_DOMAIN(darr, LocalDateTime.now().plusDays(3), roomStatus));
        roomRepository.save(RoomFixture.ROOM_DOMAIN_WITH_DIFFERENT_TITLE(ash, LocalDateTime.now().plusDays(3), roomStatus));

        AuthInfo anonymous = AuthInfo.getAnonymous();

        RoomSearchResponses response = roomInquiryService.search(anonymous.getId(), roomStatus, RoomClassification.ALL, "자바");
        List<String> managerNames = getManagerNames(response);

        assertThat(managerNames).containsExactly("darr", "pororo");
    }
}
