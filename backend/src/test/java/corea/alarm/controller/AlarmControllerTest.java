package corea.alarm.controller;

import config.ControllerTest;
import corea.alarm.domain.UserToUserAlarm;
import corea.alarm.domain.UserToUserAlarmRepository;
import corea.alarm.dto.AlarmCheckRequest;
import corea.auth.service.TokenService;
import corea.fixture.AlarmFixture;
import corea.fixture.MemberFixture;
import corea.fixture.RoomFixture;
import corea.member.domain.Member;
import corea.member.repository.MemberRepository;
import corea.room.domain.Room;
import corea.room.repository.RoomRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@ControllerTest
class AlarmControllerTest {
    @Autowired
    private UserToUserAlarmRepository userToUserAlarmRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TokenService tokenService;

    private Member actor;
    private Member receiver;
    private Room interaction;
    private long interactionId;

    @Autowired
    private RoomRepository roomRepository;

    @BeforeEach
    void setUp() {
        this.actor = memberRepository.save(MemberFixture.MEMBER_MOVIN());
        this.receiver = memberRepository.save(MemberFixture.MEMBER_PORORO());
        this.interaction = roomRepository.save(RoomFixture.ROOM_DOMAIN(memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON())));
        interactionId = interaction.getId();
    }

    @Test
    @DisplayName("알림을 체크하면 읽은 상태가 된다.")
    void alarm_check() {
        UserToUserAlarm alarm = userToUserAlarmRepository.save(AlarmFixture.REVIEW_COMPLETE(actor.getId(), receiver.getId(), interactionId));
        //@formatter:off
        RestAssured.given().auth().oauth2(tokenService.createAccessToken(receiver))
                .body(new AlarmCheckRequest(alarm.getId(),"USER")).contentType(ContentType.JSON)
                .when().post("/alarm/check")
                .then().assertThat().statusCode(200);
        //@formatter:on

        alarm = userToUserAlarmRepository.findById(alarm.getId())
                .get();
        assertThat(alarm.isRead()).isTrue();
    }
}
