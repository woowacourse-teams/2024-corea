package corea.room.controller;

import config.ControllerTest;
import corea.auth.service.TokenService;
import corea.fixture.MemberFixture;
import corea.fixture.RoomFixture;
import corea.member.domain.Member;
import corea.member.repository.MemberRepository;
import corea.participation.domain.ParticipationStatus;
import corea.room.dto.RoomCreateRequest;
import corea.room.dto.RoomResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ControllerTest
@ActiveProfiles("test")
class RoomAcceptanceTest {

    @LocalServerPort
    int port;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TokenService tokenService;

    private Member member;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        member = memberRepository.save(MemberFixture.MEMBER_PORORO());
    }

    @Nested
    @DisplayName("방 하나를 조회할 수 있다.")
    class RoomReader {

        private RoomResponse response;
        private String accessToken;

        @BeforeEach
        void setUp() {
            accessToken = tokenService.createAccessToken(member);
            RoomCreateRequest request = RoomFixture.ROOM_CREATE_REQUEST();

            // 방 생성
            response = RestAssured.given().log().all()
                    .auth().oauth2(accessToken)
                    .contentType(ContentType.JSON)
                    .body(request)
                    .when().post("/rooms")
                    .then().log().all()
                    .statusCode(201)
                    .extract().as(RoomResponse.class);
        }

        @AfterEach
        void tearDown() {
            // 방 삭제
            RestAssured.given().log().all()
                    .auth().oauth2(accessToken)
                    .when().delete("/rooms/" + response.id())
                    .then().log().all()
                    .statusCode(204);
        }

        @Test
        @DisplayName("입력된 방 아이디에 대한 방이 없으면 예외가 발생한다.")
        void roomNotFound() {
            RestAssured.given().log().all()
                    .auth().oauth2(accessToken)
                    .when().get("rooms/" + 0)
                    .then().log().all()
                    .statusCode(404);
        }

        @Test
        @DisplayName("로그인하지 않은 사용자가 방에 대한 정보를 조회할 수 있다.")
        void roomWithoutLogin() {
            RoomResponse result = RestAssured.given().log().all()
                    .header("Authorization", "nothing")
                    .when().get("/rooms/" + response.id())
                    .then().log().all()
                    .statusCode(200)
                    .extract().as(RoomResponse.class);

            assertThat(result.manager()).isEqualTo(member.getUsername());
        }

        @Test
        @DisplayName("로그인한 사용자가 방에 대한 정보를 조회할 수 있다.")
        void roomWithLogin() {
            RoomResponse result = RestAssured.given()
                    .auth().oauth2(accessToken)
                    .when().get("/rooms/" + response.id())
                    .then().log().all()
                    .statusCode(200)
                    .extract().as(RoomResponse.class);

            assertThat(result.manager()).isEqualTo(member.getUsername());
        }

        @Test
        @DisplayName("조회한 방을 자신이 만들었다면 방장이다.")
        void manager() {
            RoomResponse result = RestAssured.given().log().all()
                    .auth().oauth2(accessToken)
                    .when().get("/rooms/" + response.id())
                    .then().log().all()
                    .statusCode(200)
                    .extract().as(RoomResponse.class);

            ParticipationStatus status = result.participationStatus();

            assertThat(status).isEqualTo(ParticipationStatus.MANAGER);
        }

        @Test
        @DisplayName("조회한 방을 참여하지 않았다면 이를 알 수 있다.")
        void notParticipated() {
            RoomResponse result = RestAssured.given().log().all()
                    .header("Authorization", "unknown")
                    .when().get("/rooms/" + response.id())
                    .then().log().all()
                    .statusCode(200)
                    .extract().as(RoomResponse.class);

            ParticipationStatus status = result.participationStatus();

            assertThat(status).isEqualTo(ParticipationStatus.NOT_PARTICIPATED);
        }
    }

//    @Test
//    @DisplayName("로그인하지 않은 멤버가 참여 중인 방을 조회하려고 하면 예외가 발생한다.")
//    void participatedRoomsWithoutLogin() {
//        RestAssured.given().log().all()
//                .header("Authorization", "nothing")
//                .when().get("/rooms/participated")
//                .then().log().all()
//                .statusCode(401);
//    }
//
//    @Test
//    @DisplayName("현재 로그인한 멤버가 참여 중인 방을 보여준다.")
//    void participatedRoomsWithLogin() {
//        String accessToken = tokenService.createAccessToken(memberRepository.findByUsername("jcoding-play").get());
//
//        RoomResponses response = RestAssured.given()
//                .auth().oauth2(accessToken)
//                .when().get("/rooms/participated")
//                .then().log().all()
//                .statusCode(200)
//                .extract().as(RoomResponses.class);
//
//        List<RoomResponse> rooms = response.rooms();
//
//        List<String> managers = rooms.stream()
//                .map(RoomResponse::manager)
//                .toList();
//
//        assertSoftly(softly -> {
//            softly.assertThat(rooms)
//                    .hasSize(3);
//            softly.assertThat(managers)
//                    .containsExactlyInAnyOrder("00kang", "pp449", "chlwlstlf");
//        });
//    }
//
//    @Test
//    @DisplayName("참여 중인 방을 종료된 방도 포함해서 보여줄 수 있다.")
//    void participatedRooms_IncludeClosed() {
//        String accessToken = tokenService.createAccessToken(memberRepository.findByUsername("jcoding-play").get());
//
//        RoomResponses response = RestAssured.given().log().all()
//                .auth().oauth2(accessToken)
//                .when().get("/rooms/participated?includeClosed=true")
//                .then().log().all()
//                .statusCode(200)
//                .extract().as(RoomResponses.class);
//
//        List<RoomResponse> rooms = response.rooms();
//
//        List<String> managers = rooms.stream()
//                .map(RoomResponse::manager)
//                .toList();
//
//        assertThat(managers).containsExactlyInAnyOrder("jcoding-play", "00kang", "pp449", "chlwlstlf");
//    }
//
//    @Test
//    @DisplayName("참여 중인 방을 종료된 방도 제외해서 보여줄 수 있다.")
//    void participatedRooms_ExcludeClosed() {
//        String accessToken = tokenService.createAccessToken(memberRepository.findByUsername("jcoding-play").get());
//
//        RoomResponses response = RestAssured.given().log().all()
//                .auth().oauth2(accessToken)
//                .when().get("/rooms/participated?includeClosed=false")
//                .then().log().all()
//                .statusCode(200)
//                .extract().as(RoomResponses.class);
//
//        List<RoomResponse> rooms = response.rooms();
//
//        List<String> managers = rooms.stream()
//                .map(RoomResponse::manager)
//                .toList();
//
//        assertThat(managers).containsExactlyInAnyOrder("00kang", "pp449", "chlwlstlf");
//    }
//
//    @Test
//    @DisplayName("로그인하지 않은 사용자가 분야별로 현재 모집 중인 방들을 조회할 수 있다.")
//    void openedRoomsWithoutLogin() {
//        RoomResponses response = RestAssured.given().log().all()
//                .header("Authorization", "nothing")
//                .when().get("/rooms/opened?classification=be")
//                .then().log().all()
//                .statusCode(200)
//                .extract().as(RoomResponses.class);
//
//        List<RoomResponse> rooms = response.rooms();
//
//        assertSoftly(softly -> {
//            softly.assertThat(rooms).hasSize(4);
//            softly.assertThat(rooms.get(0).manager()).isEqualTo("jcoding-play");
//            softly.assertThat(rooms.get(1).manager()).isEqualTo("ashsty");
//            softly.assertThat(rooms.get(2).manager()).isEqualTo("pobi");
//            softly.assertThat(rooms.get(3).manager()).isEqualTo("pobi");
//        });
//    }
//
//    @Test
//    @DisplayName("로그인한 사용자가 분야별로 현재 모집 중인 방들을 조회할 수 있다.")
//    void openedRoomsWithLogin() {
//        String accessToken = tokenService.createAccessToken(memberRepository.findByUsername("jcoding-play").get());
//
//        RoomResponses response = RestAssured.given()
//                .auth().oauth2(accessToken)
//                .when().get("/rooms/opened?classification=be")
//                .then().log().all()
//                .statusCode(200)
//                .extract().as(RoomResponses.class);
//
//        List<RoomResponse> rooms = response.rooms();
//
//        assertSoftly(softly -> {
//            softly.assertThat(rooms).hasSize(4);
//            softly.assertThat(rooms.get(0).manager()).isEqualTo("jcoding-play");
//            softly.assertThat(rooms.get(1).manager()).isEqualTo("ashsty");
//            softly.assertThat(rooms.get(2).manager()).isEqualTo("pobi");
//            softly.assertThat(rooms.get(3).manager()).isEqualTo("pobi");
//        });
//    }
//
//    @Test
//    @DisplayName("모집 완료된 방들을 조회할 수 있다.")
//    void closedRooms() {
//        RoomResponses response = RestAssured.given().log().all()
//                .header("Authorization", "jcoding-play")
//                .when().get("/rooms/closed?classification=all")
//                .then().log().all()
//                .statusCode(200)
//                .extract().as(RoomResponses.class);
//
//        List<RoomResponse> rooms = response.rooms();
//
//        assertSoftly(softly -> {
//            softly.assertThat(rooms).hasSize(3);
//            softly.assertThat(rooms.get(0).manager()).isEqualTo("jcoding-play");
//            softly.assertThat(rooms.get(1).manager()).isEqualTo("youngsu5582");
//            softly.assertThat(rooms.get(2).manager()).isEqualTo("chlwlstlf");
//        });
//    }
}
