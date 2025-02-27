package corea.feedback.controller;

import config.ControllerTest;
import corea.alarm.domain.AlarmActionType;
import corea.alarm.domain.AlarmsByActionType;
import corea.alarm.domain.UserToUserAlarmReader;
import corea.auth.service.TokenService;
import corea.feedback.dto.DevelopFeedbackCreateRequest;
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
class DevelopFeedbackFeedbackControllerTest {

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
    private DevelopFeedbackCreateRequest request;

    @BeforeEach
    void setUp() {
        manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
        room = roomRepository.save(RoomFixture.ROOM_DOMAIN(manager));
        reviewer = memberRepository.save(MemberFixture.MEMBER_PORORO());
        reviewee = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());
        token = tokenService.createAccessToken(reviewer);
        matchResultRepository.save(MatchResultFixture.MATCH_RESULT_DOMAIN(
                room.getId(),
                reviewer,
                reviewee
        ));

        request = new DevelopFeedbackCreateRequest(
                reviewee.getId(),
                4,
                List.of("방의 목적에 맞게 코드를 작성했어요", "코드를 이해하기 쉬웠어요"),
                "처음 자바를 접해봤다고 했는데 \n 생각보다 매우 구성되어 있는 코드 였던거 같습니다. ...",
                2
        );
    }

    @Test
    @DisplayName("개발(리뷰어 -> 리뷰이) 피드백을 작성한다.")
    void create() {
        RestAssured.given()
                .auth()
                .oauth2(token)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/rooms/" + room.getId() + "/develop/feedbacks")
                .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("개발 피드백 작성 시 리뷰이에게 알람이 생성된다.")
    void create_alarm() {
        RestAssured.given()
                .auth()
                .oauth2(token)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/rooms/" + room.getId() + "/develop/feedbacks")
                .then()
                .statusCode(200);

        long reviewerAlarmCount = userToUserAlarmReader.countReceivedAlarm(reviewer, false);
        long revieweeAlarmCount = userToUserAlarmReader.countReceivedAlarm(reviewee, false);
        AlarmsByActionType alarms = userToUserAlarmReader.findAllByReceiver(reviewee);

        assertAll(
                () -> assertEquals(reviewerAlarmCount, 0),
                () -> assertEquals(revieweeAlarmCount, 1),
                () -> assertTrue(alarms.data().containsKey(AlarmActionType.FEEDBACK_CREATED))
        );
    }
}
