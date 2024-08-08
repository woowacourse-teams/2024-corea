package corea.room.acceptance;

import config.ControllerTest;
import corea.auth.service.LoginService;
import corea.feedback.dto.DevelopFeedbackRequest;
import corea.feedback.dto.SocialFeedbackRequest;
import corea.fixture.MatchResultFixture;
import corea.fixture.MemberFixture;
import corea.fixture.RoomFixture;
import corea.matching.dto.MatchResultResponse;
import corea.matching.dto.MatchResultResponses;
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
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;

@ControllerTest
class RoomReviewerAcceptanceTest {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MatchResultRepository matchResultRepository;

    @Autowired
    private LoginService authService;

    @Test
    @DisplayName("개발 피드백을 작성하면 리뷰어가 리뷰이에게 피드백을 쓴 상태가 된다.")
    void reviewer_match_result_should_be_writed() {
        Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
        Room room = roomRepository.save(RoomFixture.ROOM_DOMAIN(manager));
        Member reviewer = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Member reviewee = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());
        matchResultRepository.save(MatchResultFixture.MATCH_RESULT_DOMAIN(
                room.getId(),
                reviewer,
                reviewee
        ));
        String accessToken = authService.createAccessToken(reviewer);

        DevelopFeedbackRequest request = new DevelopFeedbackRequest(
                reviewee.getId(),
                4,
                List.of("방의 목적에 맞게 코드를 작성했어요.", "코드를 이해하기 쉬웠어요."),
                "처음 자바를 접해봤다고 했는데 \n 생각보다 매우 구성되어 있는 코드 였던거 같습니다. ...",
                2
        );

        //@formatter:off
        RestAssured.given().header("Authorization", accessToken).contentType(ContentType.JSON).body(request)
                .when().post("/rooms/" + room.getId() + "/develop/feedbacks")
                .then().statusCode(200);
        //@formatter:on


        //@formatter:off
        MatchResultResponses response = RestAssured.given().header("Authorization", accessToken)
                .when().get("/rooms/" + room.getId() + "/reviewees")
                .then().statusCode(200).extract().as(MatchResultResponses.class);
        //@formatter:on


        MatchResultResponse revieweeData = response.matchResultResponses()
                .stream()
                .filter(matchResultResponse -> matchResultResponse.userId() == reviewee.getId())
                .findFirst()
                .orElseThrow(NoSuchElementException::new);

        assertThat(revieweeData.isWrited()).isTrue();
    }

    @Test
    @DisplayName("소설 피드백을 작성하면 리뷰이가 리뷰어이에게 피드백을 쓴 상태가 된다.")
    void reviewee_match_result_should_be_writed() {
        Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
        Room room = roomRepository.save(RoomFixture.ROOM_DOMAIN(manager));
        Member reviewer = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Member reviewee = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());
        String accessToken = authService.createAccessToken(reviewee);
        matchResultRepository.save(MatchResultFixture.MATCH_RESULT_DOMAIN(
                room.getId(),
                reviewer,
                reviewee
        ));

        SocialFeedbackRequest request = new SocialFeedbackRequest(
                reviewer.getId(),
                4,
                List.of("방의 목적에 맞게 코드를 작성했어요.", "코드를 이해하기 쉬웠어요."),
                "처음 자바를 접해봤다고 했는데 \n 생각보다 매우 구성되어 있는 코드 였던거 같습니다. ..."
        );

        //@formatter:off
        RestAssured.given().header("Authorization", accessToken).contentType(ContentType.JSON).body(request)
                .when().post("/rooms/" + room.getId() + "/social/feedbacks")
                .then().statusCode(200);
        //@formatter:on

        //@formatter:off
        MatchResultResponses response = RestAssured.given().header("Authorization", accessToken)
                .when().get("/rooms/" + room.getId() + "/reviewers")
                .then().statusCode(200).extract().as(MatchResultResponses.class);
        //@formatter:on


        MatchResultResponse reviewerData = response.matchResultResponses()
                .stream()
                .filter(matchResultResponse -> matchResultResponse.userId() == reviewer.getId())
                .findFirst()
                .orElseThrow(NoSuchElementException::new);

        assertThat(reviewerData.isWrited()).isTrue();
    }
}
