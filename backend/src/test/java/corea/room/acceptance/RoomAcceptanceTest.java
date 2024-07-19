package corea.room.acceptance;

import corea.matching.dto.ParticipationRequest;
import corea.matching.service.ParticipationService;
import corea.member.domain.Member;
import corea.member.repository.MemberRepository;
import corea.room.dto.RoomResponse;
import corea.room.dto.RoomResponses;
import corea.room.fixture.MemberFixture;
import corea.room.fixture.RoomFixture;
import corea.room.service.RoomService;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RoomAcceptanceTest {

    @LocalServerPort
    int port;

    @Autowired
    RoomService roomService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ParticipationService participationService;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("현재 로그인한 멤버가 참여 중인 방을 보여준다.")
    void participatedRooms() {
        Member pororo = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Member ash = memberRepository.save(MemberFixture.MEMBER_ASH());
        RoomResponse roomResponse = roomService.create(RoomFixture.ROOM_CREATE_REQUEST(ash));
        participationService.participate(new ParticipationRequest(roomResponse.id(), pororo.getId()));

        RoomResponses response = RestAssured.given().log().all()
                .header("Authorization", "namejgc@naver.com")
                .when().get("/rooms/participated")
                .then().log().all()
                .statusCode(200)
                .extract().as(RoomResponses.class);

        List<RoomResponse> rooms = response.rooms();
        assertThat(rooms).hasSize(1);
        assertThat(rooms.get(0).author()).isEqualTo("박민아");
    }
}
