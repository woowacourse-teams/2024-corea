package corea.room.controller;

import config.BaseControllerTest;
import config.ControllerTest;
import corea.auth.service.TokenService;
import corea.fixture.MemberFixture;
import corea.fixture.RoomFixture;
import corea.global.config.Constants;
import corea.member.domain.Member;
import corea.member.domain.MemberRole;
import corea.member.repository.MemberRepository;
import corea.participation.domain.Participation;
import corea.participation.repository.ParticipationRepository;
import corea.room.domain.Room;
import corea.room.dto.RoomResponse;
import corea.room.dto.RoomResponses;
import corea.room.repository.RoomRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@ControllerTest
class RoomDetailsInquiryControllerTest extends BaseControllerTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TokenService tokenService;

    private Member manager;
    private String managerAccessToken;
    private RoomResponse createdRoom;

    @BeforeEach
    void setUp() {
        manager = memberRepository.save(MemberFixture.MEMBER_PORORO());
        managerAccessToken = tokenService.createAccessToken(manager);

        createdRoom = createRoom(managerAccessToken);
    }

    private RoomResponse createRoom(String accessToken) {
        return RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .contentType(ContentType.JSON)
                .body(RoomFixture.ROOM_CREATE_REQUEST())
                .when().post("/rooms")
                .then().log().all()
                .statusCode(201)
                .extract().as(RoomResponse.class);
    }

    @Nested
    @DisplayName("방 하나를 조회할 수 있다.")
    class RoomReader {

        @Test
        @DisplayName("로그인하지 않은 사용자가 방에 대한 정보를 조회할 수 있다.")
        void roomWithoutLogin() {
            Response response = readRoom(Constants.ANONYMOUS, createdRoom.id());
            assertThat(response.statusCode()).isEqualTo(200);
        }

        @Test
        @DisplayName("로그인한 사용자가 방에 대한 정보를 조회할 수 있다.")
        void roomWithLogin() {
            Response response = readRoom(managerAccessToken, createdRoom.id());
            assertThat(response.statusCode()).isEqualTo(200);
        }

        @Test
        @DisplayName("입력된 방 아이디에 대한 방이 없으면 예외가 발생한다.")
        void roomNotFound() {
            Response response = readRoom(managerAccessToken, 0);
            assertThat(response.getStatusCode()).isEqualTo(404);
        }

        private Response readRoom(String accessToken, long roomId) {
            return RestAssured.given().log().all()
                    .auth().oauth2(accessToken)
                    .when().get("rooms/" + roomId)
                    .then().log().all()
                    .extract().response();
        }
    }

    @Nested
    @DisplayName("참여중인 방들을 조회할 수 있다.")
    class ParticipatedRooms {

        @Autowired
        private RoomRepository roomRepository;

        @Autowired
        private ParticipationRepository participationRepository;

        @Test
        @DisplayName("비로그인 사용자는 참여중인 방들을 조회할 수 없다.")
        void invalid() {
            Response response = readParticipatedRooms(Constants.ANONYMOUS, false);
            assertThat(response.getStatusCode()).isEqualTo(401);
        }

        @Test
        @DisplayName("참여했던 방을 제외하고, 현재 참여중인 방을 조회할 수 있다.")
        void readParticipatedRooms_excludeClosed() {
            Member movin = memberRepository.save(MemberFixture.MEMBER_MOVIN());
            Room savedRoom = roomRepository.save(RoomFixture.ROOM_DOMAIN_WITH_CLOSED(movin));
            participationRepository.save(new Participation(savedRoom, manager, MemberRole.BOTH, 2));

            Response response = readParticipatedRooms(managerAccessToken, false);
            RoomResponses rooms = response.as(RoomResponses.class);

            assertAll(
                    () -> assertThat(response.statusCode()).isEqualTo(200),
                    () -> assertThat(rooms.rooms()).hasSize(1)
            );
        }

        @Test
        @DisplayName("참여했던 방을 포함하고, 현재 참여중인 방을 조회할 수 있다.")
        void readParticipatedRooms_includeClosed() {
            Member movin = memberRepository.save(MemberFixture.MEMBER_MOVIN());
            Room savedRoom = roomRepository.save(RoomFixture.ROOM_DOMAIN_WITH_CLOSED(movin));
            participationRepository.save(new Participation(savedRoom, manager, MemberRole.BOTH, 2));

            Response response = readParticipatedRooms(managerAccessToken, true);
            RoomResponses rooms = response.as(RoomResponses.class);

            assertAll(
                    () -> assertThat(response.statusCode()).isEqualTo(200),
                    () -> assertThat(rooms.rooms()).hasSize(2)
            );
        }

        private Response readParticipatedRooms(String accessToken, boolean includeClosed) {
            return RestAssured.given().log().all()
                    .auth().oauth2(accessToken)
                    .when().get("/rooms/participated?includeClosed=" + includeClosed)
                    .then().log().all()
                    .extract().response();
        }
    }
}
