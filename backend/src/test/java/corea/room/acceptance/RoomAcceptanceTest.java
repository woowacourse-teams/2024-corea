package corea.room.acceptance;

import corea.room.dto.RoomResponse;
import corea.room.dto.RoomResponses;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class RoomAcceptanceTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("로그인하지 않은 사용자가 방에 대한 정보를 조회할 수 있다.")
    void roomWithoutLogin() {
        RoomResponse response = RestAssured.given().log().all()
                .header("Authorization", "nothing")
                .when().get("/rooms/7")
                .then().log().all()
                .statusCode(200)
                .extract().as(RoomResponse.class);

        assertSoftly(softly -> {
            softly.assertThat(response.author()).isEqualTo("이상엽");
            softly.assertThat(response.isParticipated()).isEqualTo(false);
        });
    }

    @Test
    @DisplayName("로그인한 사용자가 방에 대한 정보를 조회할 수 있다.")
    void roomWithLogin() {
        RoomResponse response = RestAssured.given().log().all()
                .header("Authorization", "namejgc@naver.com")
                .when().get("/rooms/7")
                .then().log().all()
                .statusCode(200)
                .extract().as(RoomResponse.class);

        assertSoftly(softly -> {
            softly.assertThat(response.author()).isEqualTo("이상엽");
            softly.assertThat(response.isParticipated()).isEqualTo(true);
        });
    }

    @Test
    @DisplayName("로그인하지 않은 멤버가 참여 중인 방을 조회하려고 하면 예외가 발생한다.")
    void participatedRoomsWithoutLogin() {
        RestAssured.given().log().all()
                .header("Authorization", "nothing")
                .when().get("/rooms/participated")
                .then().log().all()
                .statusCode(401);
    }

    @Test
    @DisplayName("현재 로그인한 멤버가 참여 중인 방을 보여준다.")
    void participatedRoomsWithLogin() {
        RoomResponses response = RestAssured.given().log().all()
                .header("Authorization", "namejgc@naver.com")
                .when().get("/rooms/participated")
                .then().log().all()
                .statusCode(200)
                .extract().as(RoomResponses.class);

        List<RoomResponse> rooms = response.rooms();

        assertSoftly(softly -> {
            softly.assertThat(rooms).hasSize(2);
            softly.assertThat(rooms.get(0).author()).isEqualTo("강다빈");
            softly.assertThat(rooms.get(1).author()).isEqualTo("이상엽");
        });
    }

    @Test
    @DisplayName("로그인하지 않은 사용자가 분야별로 현재 모집 중인 방들을 조회할 수 있다.")
    void openedRoomsWithoutLogin() {
        RoomResponses response = RestAssured.given().log().all()
                .header("Authorization", "nothing")
                .when().get("/rooms/opened?classification=be")
                .then().log().all()
                .statusCode(200)
                .extract().as(RoomResponses.class);

        List<RoomResponse> rooms = response.rooms();

        assertSoftly(softly -> {
            softly.assertThat(rooms).hasSize(2);
            softly.assertThat(rooms.get(0).author()).isEqualTo("조경찬");
            softly.assertThat(rooms.get(1).author()).isEqualTo("박민아");
        });
    }

    @Test
    @DisplayName("로그인한 사용자가 분야별로 현재 모집 중인 방들을 조회할 수 있다.")
    void openedRoomsWithLogin() {
        RoomResponses response = RestAssured.given().log().all()
                .header("Authorization", "namejgc@naver.com")
                .when().get("/rooms/opened?classification=be")
                .then().log().all()
                .statusCode(200)
                .extract().as(RoomResponses.class);

        List<RoomResponse> rooms = response.rooms();

        assertSoftly(softly -> {
            softly.assertThat(rooms).hasSize(1);
            softly.assertThat(rooms.get(0).author()).isEqualTo("박민아");
        });
    }

    @Test
    @DisplayName("모집 완료된 방들을 조회할 수 있다.")
    void closedRooms() {
        RoomResponses response = RestAssured.given().log().all()
                .header("Authorization", "namejgc@naver.com")
                .when().get("/rooms/closed?classification=all")
                .then().log().all()
                .statusCode(200)
                .extract().as(RoomResponses.class);

        List<RoomResponse> rooms = response.rooms();

        assertSoftly(softly -> {
            softly.assertThat(rooms).hasSize(3);
            softly.assertThat(rooms.get(0).author()).isEqualTo("조경찬");
            softly.assertThat(rooms.get(1).author()).isEqualTo("이영수");
            softly.assertThat(rooms.get(2).author()).isEqualTo("최진실");
        });
    }
}
