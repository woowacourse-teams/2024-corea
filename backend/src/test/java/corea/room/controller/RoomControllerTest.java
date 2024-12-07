package corea.room.controller;

import config.ControllerTest;
import corea.auth.service.TokenService;
import corea.fixture.MemberFixture;
import corea.fixture.RoomFixture;
import corea.global.config.Constants;
import corea.member.domain.Member;
import corea.member.domain.MemberRole;
import corea.member.repository.MemberRepository;
import corea.room.domain.Room;
import corea.room.dto.RefactorRoomResponse;
import corea.room.dto.RoomResponse;
import corea.room.repository.RoomRepository;
import corea.scheduler.domain.AutomaticMatching;
import corea.scheduler.domain.AutomaticUpdate;
import corea.scheduler.repository.AutomaticMatchingRepository;
import corea.scheduler.repository.AutomaticUpdateRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@ControllerTest
class RoomControllerTest {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AutomaticMatchingRepository automaticMatchingRepository;

    @Autowired
    private AutomaticUpdateRepository automaticUpdateRepository;

    @Autowired
    private TokenService tokenService;

    private String  managerAccessToken;

    @BeforeEach
    void setUp() {
        Member manager = memberRepository.save(MemberFixture.MEMBER_PORORO());
        managerAccessToken = tokenService.createAccessToken(manager);
    }

    @Nested
    @DisplayName("방을 만들 수 있다.")
    class RoomCreate {

        @Test
        @DisplayName("로그인한 사용자가 방을 만들 수 있다.")
        void create() {
            Response response = createRoom(managerAccessToken);
            assertThat(response.statusCode()).isEqualTo(201);
        }

        @Test
        @DisplayName("비로그인 사용자가 방을 만들려하면 예외가 발생한다.")
        void invalid() {
            Response response = createRoom(Constants.ANONYMOUS);
            assertThat(response.statusCode()).isEqualTo(401);
        }

        @Test
        @DisplayName("방을 만든 사람은 방장이 된다.")
        void manager() {
            RefactorRoomResponse createdRoom = getCreatedRoomResponse(managerAccessToken);

            String manager = createdRoom.roomInfoResponse().manager();

            assertThat(manager).isEqualTo("pororo");
        }

        @Test
        @DisplayName("방을 만들면 모집 마감 기간에 자동으로 매칭을 실행하도록 저장한다.")
        void createAutomaticMatching() {
            RefactorRoomResponse createdRoom = getCreatedRoomResponse(managerAccessToken);

            AutomaticMatching automaticMatching = automaticMatchingRepository.findByRoomId(createdRoom.id()).get();

            assertThat(automaticMatching.getMatchingStartTime()).isEqualTo(createdRoom.deadlineResponse().recruitmentDeadline());
        }

        @Test
        @DisplayName("방을 만들면 리뷰 마감 기간에 자동으로 방이 종료되도록 저장한다.")
        void createAutomaticUpdate() {
            RefactorRoomResponse createdRoom = getCreatedRoomResponse(managerAccessToken);

            AutomaticUpdate automaticUpdate = automaticUpdateRepository.findByRoomId(createdRoom.id()).get();

            assertThat(automaticUpdate.getUpdateStartTime()).isEqualTo(createdRoom.deadlineResponse().reviewDeadline());
        }
    }

    @Nested
    @DisplayName("방을 수정할 수 있다.")
    class RoomUpdate {

        private RefactorRoomResponse createdRoom;

        @BeforeEach
        void setUp() {
            createdRoom = getCreatedRoomResponse(managerAccessToken);
        }

        @Test
        @DisplayName("방장이 방을 수정할 수 있다.")
        void update() {
            RoomResponse updatedRoom = getUpdatedRoomResponse(createdRoom.id());

            assertAll(
                    () -> assertThat(createdRoom.roomInfoResponse().title()).isEqualTo("Room"),
                    () -> assertThat(updatedRoom.title()).isEqualTo("update Room")
            );
        }

        @Test
        @DisplayName("방장이 아닌 사람이 방을 수정하려 하면 예외가 발생한다.")
        void notMatchManager() {
            Member movin = memberRepository.save(MemberFixture.MEMBER_MOVIN());
            String accessToken = tokenService.createAccessToken(movin);

            Response response = updateRoom(accessToken, createdRoom.id());

            assertThat(response.statusCode()).isEqualTo(401);
        }

        @Test
        @DisplayName("존재하지 않는 방을 수정하려 하면 예외가 발생한다.")
        void invalidRoomId() {
            Response response = updateRoom(managerAccessToken, 0);
            assertThat(response.statusCode()).isEqualTo(404);
        }

        @Test
        @DisplayName("모집중인 상태가 아닌 방을 수정하려 하면 예외가 발생한다.")
        void invalidRoomStatus() {
            Room room = roomRepository.findById(createdRoom.id()).get();
            room.updateStatusToProgress();
            roomRepository.save(room);

            Response response = updateRoom(managerAccessToken, createdRoom.id());

            assertThat(response.statusCode()).isEqualTo(400);
        }

        @Test
        @DisplayName("방을 수정하면 변경된 모집 마감 기간에 자동으로 매칭을 실행하도록 수정한다.")
        void updateAutomaticMatching() {
            RoomResponse updatedRoom = getUpdatedRoomResponse(createdRoom.id());

            AutomaticMatching automaticMatching = automaticMatchingRepository.findByRoomId(createdRoom.id()).get();

            assertThat(automaticMatching.getMatchingStartTime()).isEqualTo(updatedRoom.recruitmentDeadline());
        }

        @Test
        @DisplayName("방을 수정하면 변경된 리뷰 마감 기간에 자동으로 방이 종료되도록 수정한다.")
        void updateAutomaticUpdate() {
            RoomResponse updatedRoom = getUpdatedRoomResponse(createdRoom.id());

            AutomaticUpdate automaticUpdate = automaticUpdateRepository.findByRoomId(createdRoom.id()).get();

            assertThat(automaticUpdate.getUpdateStartTime()).isEqualTo(updatedRoom.reviewDeadline());
        }
    }

    @Nested
    @DisplayName("방을 삭제할 수 있다.")
    class RoomDelete {

        private RefactorRoomResponse createdRoom;

        @BeforeEach
        void setUp() {
            createdRoom = getCreatedRoomResponse(managerAccessToken);
        }

        @Test
        @DisplayName("방장이 방을 삭제할 수 있다.")
        void delete() {
            Response response = deleteRoom(managerAccessToken, createdRoom.id());
            assertThat(response.statusCode()).isEqualTo(204);
        }

        @Test
        @DisplayName("방장이 아닌 사람이 방을 삭제하려 하면 예외가 발생한다.")
        void notMatchManager() {
            Member movin = memberRepository.save(MemberFixture.MEMBER_MOVIN());
            String accessToken = tokenService.createAccessToken(movin);

            Response response = deleteRoom(accessToken, createdRoom.id());

            assertThat(response.statusCode()).isEqualTo(401);
        }

        @Test
        @DisplayName("존재하지 않는 방을 삭제하려 하면 예외가 발생한다.")
        void invalidRoomId() {
            Response response = deleteRoom(managerAccessToken, 0);
            assertThat(response.statusCode()).isEqualTo(404);
        }

        @Test
        @DisplayName("모집중인 상태가 아닌 방을 삭제하려 하면 예외가 발생한다.")
        void invalidRoomStatus() {
            Room room = roomRepository.findById(createdRoom.id()).get();
            room.updateStatusToProgress();
            roomRepository.save(room);

            Response response = deleteRoom(managerAccessToken, createdRoom.id());

            assertThat(response.statusCode()).isEqualTo(400);
        }

        @Test
        @Disabled
        @DisplayName("방을 삭제하면 저장된 자동 매칭도 삭제한다.")
        void deleteAutomaticMatching() {
            deleteRoom(managerAccessToken, createdRoom.id());

            Optional<AutomaticMatching> automaticMatchingOpt = automaticMatchingRepository.findByRoomId(createdRoom.id());

            assertThat(automaticMatchingOpt).isEmpty();
        }

        @Test
        @Disabled
        @DisplayName("방을 삭제하면 저장된 자동 업데이트도 삭제한다.")
        void deleteAutomaticUpdate() {
            deleteRoom(managerAccessToken, createdRoom.id());

            Optional<AutomaticUpdate> automaticUpdateOpt = automaticUpdateRepository.findByRoomId(createdRoom.id());

            assertThat(automaticUpdateOpt).isEmpty();
        }
    }

    private RefactorRoomResponse getCreatedRoomResponse(String accessToken) {
        Response response = createRoom(accessToken);
        return response.as(RefactorRoomResponse.class);
    }

    private RoomResponse getUpdatedRoomResponse(long createdRoomId) {
        Response response = updateRoom(managerAccessToken, createdRoomId);
        return response.as(RoomResponse.class);
    }

    private Response createRoom(String accessToken) {
        return RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .contentType(ContentType.JSON)
                .body(RoomFixture.ROOM_REQUEST())
                .when().post("/rooms")
                .then().log().all()
                .extract().response();
    }

    private Response updateRoom(String accessToken, long roomId) {
        return RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .contentType(ContentType.JSON)
                .body(RoomFixture.ROOM_UPDATE_REQUEST(roomId))
                .when().put("/rooms")
                .then().log().all()
                .extract().response();
    }

    private Response deleteRoom(String accessToken, long roomId) {
        return RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .when().delete("/rooms/" + roomId)
                .then().log().all()
                .extract().response();
    }
}
