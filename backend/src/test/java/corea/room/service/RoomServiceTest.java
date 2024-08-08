package corea.room.service;

import corea.auth.domain.AuthInfo;
import corea.exception.CoreaException;
import corea.room.domain.RoomClassification;
import corea.room.dto.RoomCreateRequest;
import corea.room.dto.RoomResponse;
import corea.room.dto.RoomResponses;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
class RoomServiceTest {

    @Autowired
    private RoomService roomService;

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

        assertSoftly(softly -> {
            softly.assertThat(rooms).hasSize(2);
            softly.assertThat(rooms.get(0).manager()).isEqualTo("강다빈");
            softly.assertThat(rooms.get(1).manager()).isEqualTo("이상엽");
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
    @CsvSource({"be, 1", "fe, 1", "an, 1", "all, 3"})
    @DisplayName("현재 모집 완료된 방들을 조회할 수 있다.")
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
                "thumLink", 3, null, 3,
                LocalDateTime.now().plusMinutes(59), LocalDateTime.now().plusDays(2), RoomClassification.ALL);

        assertThatThrownBy(() -> roomService.create(1, request))
                .isInstanceOf(CoreaException.class);
    }

    @Test
    @DisplayName("리뷰 마감 시간은 모집 마감 시간보다 1일 이후가 아니라면 예외가 발생한다.")
    void invalidReviewDeadline() {
        RoomCreateRequest request = new RoomCreateRequest("title", "content", "repoLink",
                "thumLink", 3, null, 3,
                LocalDateTime.now().plusHours(2), LocalDateTime.now().plusHours(23), RoomClassification.ALL);

        assertThatThrownBy(() -> roomService.create(1, request))
                .isInstanceOf(CoreaException.class);
    }
}
