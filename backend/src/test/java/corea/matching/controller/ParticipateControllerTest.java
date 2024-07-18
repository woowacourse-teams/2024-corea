package corea.matching.controller;

import config.ControllerTest;
import corea.fixture.MemberFixture;
import corea.fixture.RoomFixture;
import corea.member.domain.Member;
import corea.member.repository.MemberRepository;
import corea.room.domain.Room;
import corea.room.repository.RoomRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@ControllerTest
class ParticipateControllerTest {
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("사용자가 방에 참여한다.")
    void participate() {
        Member manager = memberRepository.save(MemberFixture.MEMBER_MANAGER());
        Room room = roomRepository.save(RoomFixture.ROOM_DOMAIN(manager));

        Member member = memberRepository.save(MemberFixture.MEMBER_DOMAIN());
        //@formatter:off
        RestAssured.given().header(new Header("Authorization", member.getEmail())).contentType(ContentType.JSON)
                .when().post("/participate/"+room.getId())
                .then().assertThat().statusCode(200);
        //@formatter:on
    }
}
