package corea.room.service;

import corea.room.dto.RoomResponse;
import corea.room.dto.RoomResponses;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
class RoomServiceTest {

    @Autowired
    RoomService roomService;

    @Test
    @DisplayName("현재 로그인한 멤버가 참여 중인 방을 보여준다.")
    void findParticipatedRooms() {
        RoomResponses response = roomService.findParticipatedRooms(1);
        List<RoomResponse> rooms = response.rooms();

        assertSoftly(softly -> {
            softly.assertThat(rooms).hasSize(2);
            softly.assertThat(rooms.get(0).author()).isEqualTo("강다빈");
            softly.assertThat(rooms.get(1).author()).isEqualTo("이상엽");
        });
    }

    @ParameterizedTest
    @CsvSource({"be, 2", "fe, 3", "an, 2", "all, 7"})
    @DisplayName("로그인하지 않은 사용자가 분야별로 현재 모집 중인 방들을 조회할 수 있다.")
    void findOpenedRoomsWithoutMember(String expression, int expectedSize) {
        RoomResponses response = roomService.findOpenedRoomsWithoutMember(expression, 0);
        List<RoomResponse> rooms = response.rooms();

        assertThat(rooms).hasSize(expectedSize);
    }

    @ParameterizedTest
    @CsvSource({"be, 1", "fe, 1", "an, 2", "all, 4"})
    @DisplayName("로그인한 사용자가 자신이 참여하지 않고, 분야별로 현재 모집 중인 방들을 조회할 수 있다.")
    void findOpenedRoomsWithMember(String expression, int expectedSize) {
        RoomResponses response = roomService.findOpenedRoomsWithMember(1, expression, 0);
        List<RoomResponse> rooms = response.rooms();

        assertThat(rooms).hasSize(expectedSize);
    }
}
