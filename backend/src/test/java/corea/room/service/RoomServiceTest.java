package corea.room.service;

import corea.auth.domain.AuthInfo;
import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.fixture.MemberFixture;
import corea.fixture.RoomFixture;
import corea.member.domain.Member;
import corea.member.repository.MemberRepository;
import corea.room.domain.RoomClassification;
import corea.room.dto.RoomCreateRequest;
import corea.room.dto.RoomResponse;
import corea.room.dto.RoomResponses;
import corea.room.repository.RoomRepository;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
class RoomServiceTest {

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MemberRepository memberRepository;

    @ParameterizedTest
    @CsvSource({"2, true", "4, false"})
    @DisplayName("해당 방에 자신이 참여 중인지 아닌지를 판단할 수 있다.")
    void findOne(long memberId, boolean expected) {
        RoomResponse response = roomService.findOne(1, memberId);

        boolean actual = response.isParticipated();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("현재 로그인한 멤버가 참여 중인 방을 보여준다.")
    void findParticipatedRooms() {
        RoomResponses response = roomService.findParticipatedRooms(1);
        List<RoomResponse> rooms = response.rooms();

        String firstRoomManager = rooms.get(0).manager();
        String secondRoomManager = rooms.get(1).manager();
        String thirdRoomManager = rooms.get(2).manager();

        assertSoftly(softly -> {
            softly.assertThat(rooms).hasSize(3);
            softly.assertThat(firstRoomManager).isEqualTo("강다빈");
            softly.assertThat(secondRoomManager).isEqualTo("이상엽");
            softly.assertThat(thirdRoomManager).isEqualTo("최진실");
        });
    }

    @ParameterizedTest
    @CsvSource({"be, 4", "fe, 3", "an, 2", "all, 8"})
    @DisplayName("로그인하지 않은 사용자가 분야별로 현재 모집 중인 방들을 조회할 수 있다.")
    void findOpenedRoomsWithoutMember(String expression, int expectedSize) {
        AuthInfo anonymous = AuthInfo.getAnonymous();

        RoomResponses response = roomService.findOpenedRooms(anonymous.getId(), expression, 0);
        List<RoomResponse> rooms = response.rooms();

        assertThat(rooms).hasSize(expectedSize);
    }

    @ParameterizedTest
    @CsvSource({"be, 3", "fe, 1", "an, 2", "all, 6"})
    @DisplayName("로그인한 사용자가 자신이 참여하지 않고, 분야별로 현재 모집 중인 방들을 조회할 수 있다.")
    void findOpenedRoomsWithMember(String expression, int expectedSize) {
        RoomResponses response = roomService.findOpenedRooms(1, expression, 0);
        List<RoomResponse> rooms = response.rooms();

        assertThat(rooms).hasSize(expectedSize);
    }

    @ParameterizedTest
    @CsvSource({"be, 3", "fe, 2", "an, 1", "all, 6"})
    @DisplayName("로그인하지 않은 사용자가 분야별로 현재 모집 완료된 방들을 조회할 수 있다.")
    void findProgressRoomsWithoutMember(String expression, int expectedSize) {
        AuthInfo anonymous = AuthInfo.getAnonymous();

        RoomResponses response = roomService.findProgressRooms(anonymous.getId(), expression, 0);
        List<RoomResponse> rooms = response.rooms();

        assertThat(rooms).hasSize(expectedSize);
    }

    @ParameterizedTest
    @CsvSource({"be, 2", "fe, 2", "an, 1", "all, 5"})
    @DisplayName("로그인한 사용자가 자신이 참여하지 않고, 분야별로 현재 모집 완료된 방들을 조회할 수 있다.")
    void findProgressRoomsWithMember(String expression, int expectedSize) {
        RoomResponses response = roomService.findProgressRooms(1, expression, 0);
        List<RoomResponse> rooms = response.rooms();

        assertThat(rooms).hasSize(expectedSize);
    }

    @ParameterizedTest
    @CsvSource({"be, 1", "fe, 1", "an, 1", "all, 3"})
    @DisplayName("현재 종료된 방들을 조회할 수 있다.")
    void findClosedRooms(String expression, int expectedSize) {
        RoomResponses response = roomService.findClosedRooms(expression, 0);
        List<RoomResponse> rooms = response.rooms();

        assertThat(rooms).hasSize(expectedSize);
    }

    @ParameterizedTest
    @CsvSource({"0, false", "1, true"})
    @DisplayName("방을 조회할 때 전달받은 페이지가 마지막 페이지인지 여부를 판별할 수 있다.")
    void isLastPage(int pageNumber, boolean expected) {
        AuthInfo anonymous = AuthInfo.getAnonymous();

        RoomResponses response = roomService.findOpenedRooms(anonymous.getId(), "all", pageNumber);

        assertThat(response.isLastPage()).isEqualTo(expected);
    }

    @Test
    @DisplayName("모집 마감 시간은 현재 시간보다 1시간 이후가 아니라면 예외가 발생한다.")
    void invalidRecruitmentDeadline() {
        RoomCreateRequest request = new RoomCreateRequest("title", "content", "repoLink",
                "thumLink", 3, List.of("TDD", "클린코드"), 3,
                LocalDateTime.now().plusMinutes(59),
                LocalDateTime.now().plusDays(2),
                RoomClassification.ALL);

        assertThatThrownBy(() -> roomService.create(1, request))
                .asInstanceOf(InstanceOfAssertFactories.type(CoreaException.class))
                .extracting(CoreaException::getExceptionType)
                .isEqualTo(ExceptionType.INVALID_RECRUITMENT_DEADLINE);
    }

    @Test
    @DisplayName("리뷰 마감 시간은 모집 마감 시간보다 1일 이후가 아니라면 예외가 발생한다.")
    void invalidReviewDeadline() {
        LocalDateTime recruitmentDeadline = LocalDateTime.now().plusHours(2);
        RoomCreateRequest request = new RoomCreateRequest("title", "content", "repoLink",
                "thumLink", 3, List.of(), 3,
                recruitmentDeadline,
                recruitmentDeadline.plusHours(23),
                RoomClassification.ALL);

        assertThatThrownBy(() -> roomService.create(1, request))
                .asInstanceOf(InstanceOfAssertFactories.type(CoreaException.class))
                .extracting(CoreaException::getExceptionType)
                .isEqualTo(ExceptionType.INVALID_REVIEW_DEADLINE);
    }

    @Test
    @DisplayName("방을 삭제할 수 있다.")
    void delete() {
        Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
        RoomCreateRequest request = RoomFixture.ROOM_CREATE_REQUEST();
        RoomResponse roomResponse = roomService.create(manager.getId(), request);

        long roomId = roomResponse.id();
        roomService.delete(roomId, manager.getId());

        assertThat(roomRepository.findById(roomId)).isEmpty();
    }

    @Test
    @DisplayName("방을 생성한 유저가 아닌 사람이 방을 삭제하려고 하면 예외가 발생한다.")
    void invalidDelete() {
        Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
        RoomCreateRequest request = RoomFixture.ROOM_CREATE_REQUEST();
        RoomResponse roomResponse = roomService.create(manager.getId(), request);

        Member member = memberRepository.save(MemberFixture.MEMBER_PORORO());

        assertThatThrownBy(() -> roomService.delete(roomResponse.id(), member.getId()))
                .asInstanceOf(InstanceOfAssertFactories.type(CoreaException.class))
                .extracting(CoreaException::getExceptionType)
                .isEqualTo(ExceptionType.ROOM_DELETION_AUTHORIZATION_ERROR);
    }
}
