package corea.feedback.controller;

import config.ControllerTest;
import corea.alarm.domain.AlarmActionType;
import corea.alarm.domain.AlarmsByActionType;
import corea.alarm.domain.UserToUserAlarmReader;
import corea.auth.service.TokenService;
import corea.feedback.dto.SocialFeedbackCreateRequest;
import corea.fixture.MatchResultFixture;
import corea.fixture.MemberFixture;
import corea.fixture.RoomFixture;
import corea.matchresult.repository.MatchResultRepository;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ControllerTest
class SocialFeedbackFeedbackControllerTest {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MatchResultRepository matchResultRepository;

    @Autowired
    private UserToUserAlarmReader userToUserAlarmReader;

    @Autowired
    private TokenService tokenService;

    private Member manager;
    private Room room;
    private Member reviewer;
    private Member reviewee;
    private String token;
    private SocialFeedbackCreateRequest request;

    @BeforeEach
    void setUp() {
        manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
        room = roomRepository.save(RoomFixture.ROOM_DOMAIN(manager));
        reviewer = memberRepository.save(MemberFixture.MEMBER_PORORO());
        reviewee = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());
        token = tokenService.createAccessToken(reviewee);
        matchResultRepository.save(MatchResultFixture.MATCH_RESULT_DOMAIN(
                room.getId(),
                reviewer,
                reviewee
        ));

        request = new SocialFeedbackCreateRequest(
                reviewer.getId(),
                4,
                List.of("친절했어요", "리뷰 속도가 빨랐어요"),
                "유용한 블로그나 아티클도 남겨주시고, \n 사소한 부분까지 잘 챙겨준게 좋았습니다."
        );
    }

    @Test
    @DisplayName("소셜(리뷰이 -> 리뷰어)에 대한 피드백을 작성한다.")
    void create() {
        RestAssured.given()
                .auth()
                .oauth2(token)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/rooms/" + room.getId() + "/social/feedbacks")
                .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("소셜 피드백 작성 시 리뷰어에게 알람이 생성된다.")
    void create_alarm() {
        RestAssured.given()
                .auth()
                .oauth2(token)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/rooms/" + room.getId() + "/social/feedbacks")
                .then()
                .statusCode(200);

        long reviewerAlarmCount = userToUserAlarmReader.countReceivedAlarm(reviewer, false);
        long revieweeAlarmCount = userToUserAlarmReader.countReceivedAlarm(reviewee, false);
        AlarmsByActionType alarms = userToUserAlarmReader.findAllByReceiver(reviewer);

        assertAll(
                () -> assertEquals(revieweeAlarmCount, 0),
                () -> assertEquals(reviewerAlarmCount, 1),
                () -> assertTrue(alarms.data().containsKey(AlarmActionType.FEEDBACK_CREATED))
        );
    }
}
