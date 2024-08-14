package corea.feedback.controller;

import config.ControllerTest;
import corea.auth.service.LoginService;
import corea.feedback.dto.DevelopFeedbackRequest;
import corea.fixture.MatchResultFixture;
import corea.fixture.MemberFixture;
import corea.fixture.RoomFixture;
import corea.matching.repository.MatchResultRepository;
import corea.member.domain.Member;
import corea.member.repository.MemberRepository;
import corea.room.domain.Room;
import corea.room.repository.RoomRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@ControllerTest
class DevelopFeedbackFeedbackControllerTest {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MatchResultRepository matchResultRepository;

    @Autowired
    private LoginService loginService;

    @Test
    @DisplayName("개발(리뷰어 -> 리뷰이) 피드백을 작성한다.")
    void create() {
        Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
        Room room = roomRepository.save(RoomFixture.ROOM_DOMAIN(manager));
        Member reviewer = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Member reviewee = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());
        String token = loginService.createAccessToken(reviewer);
        matchResultRepository.save(MatchResultFixture.MATCH_RESULT_DOMAIN(
                room.getId(),
                reviewer,
                reviewee
        ));

        DevelopFeedbackRequest request = new DevelopFeedbackRequest(
                reviewee.getId(),
                4,
                List.of("방의 목적에 맞게 코드를 작성했어요", "코드를 이해하기 쉬웠어요"),
                "처음 자바를 접해봤다고 했는데 \n 생각보다 매우 구성되어 있는 코드 였던거 같습니다. ...",
                2
        );

        RestAssured.given().header("Authorization", token).contentType(ContentType.JSON).body(request)
                .when().post("/rooms/" + room.getId() + "/develop/feedbacks")
                .then().statusCode(200);
    }
}
