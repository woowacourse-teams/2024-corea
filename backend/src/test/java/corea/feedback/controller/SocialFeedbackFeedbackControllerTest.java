package corea.feedback.controller;

import config.ControllerTest;
import corea.auth.service.LoginService;
import corea.feedback.dto.SocialFeedbackRequest;
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
class SocialFeedbackFeedbackControllerTest {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MatchResultRepository matchResultRepository;

    @Autowired
    private LoginService loginService;

    @Test
    @DisplayName("소셜(리뷰이 -> 리뷰어)에 대한 피드백을 작성한다.")
    void create() {
        Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
        Room room = roomRepository.save(RoomFixture.ROOM_DOMAIN(manager));
        Member reviewer = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Member reviewee = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());
        String token = loginService.createAccessToken(reviewee);
        matchResultRepository.save(MatchResultFixture.MATCH_RESULT_DOMAIN(
                room.getId(),
                reviewer,
                reviewee
        ));

        SocialFeedbackRequest request = new SocialFeedbackRequest(
                reviewer.getId(),
                4,
                List.of("방의 목적에 맞게 코드를 작성했어요.", "코드를 이해하기 쉬웠어요."),
                "유용한 블로그나 아티클도 남겨주시고, \n 사소한 부분까지 잘 챙겨준게 좋았씁니다."
        );

        RestAssured.given().header("Authorization", token).contentType(ContentType.JSON).body(request)
                .when().post("/rooms/" + room.getId() + "/social/feedbacks")
                .then().statusCode(200);
    }
}
