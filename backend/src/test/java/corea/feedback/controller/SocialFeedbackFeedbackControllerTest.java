package corea.feedback.controller;

import config.ControllerTest;
import corea.auth.service.GithubOAuthProvider;
import corea.bdd.MemberGiven;
import corea.bdd.RoomGiven;
import corea.feedback.dto.SocialFeedbackRequest;
import corea.fixture.MemberFixture;
import corea.matching.domain.PullRequestInfo;
import corea.matching.dto.MatchResultResponse;
import corea.matching.service.PullRequestProvider;
import corea.room.domain.PullRequestReviews;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.List;

import static corea.bdd.MatchingGiven.리뷰이_목록_조회;
import static corea.bdd.MatchingGiven.매칭;
import static corea.bdd.ParticipationGiven.방_참가;
import static corea.bdd.ReviewGiven.리뷰_완료;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ControllerTest
class SocialFeedbackFeedbackControllerTest {

    @MockBean
    private PullRequestProvider pullRequestProvider;

    @MockBean
    private GithubOAuthProvider githubOAuthProvider;

    @BeforeEach
    void setUp() {
        PullRequestInfo info = Mockito.mock(PullRequestInfo.class);
        when(info.containsGithubMemberId(anyString())).thenReturn(true);
        when(info.getPullrequestLinkWithGithubMemberId(anyString())).thenReturn("");
        when(pullRequestProvider.getUntilDeadline(anyString(), any(LocalDateTime.class)))
                .thenReturn(info);

        PullRequestReviews pullRequestReviews = Mockito.mock(PullRequestReviews.class);
        when(pullRequestReviews.getReviewUrl(anyString())).thenReturn("");

        when(githubOAuthProvider.getPullRequestReview(anyString())).thenReturn(pullRequestReviews);
    }

    @Test
    @DisplayName("소셜(리뷰이 -> 리뷰어)에 대한 피드백을 작성한다.")
    void create() {
        String 액세스_토큰 = MemberGiven.멤버_로그인(githubOAuthProvider, MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
        long 방번호 = RoomGiven.방생성(액세스_토큰, 2)
                .id();

        String 리뷰어_토큰 = MemberGiven.멤버_로그인(githubOAuthProvider, MemberFixture.MEMBER_PORORO());
        String 리뷰이_토큰 = MemberGiven.멤버_로그인(githubOAuthProvider, MemberFixture.MEMBER_YOUNGSU());

        방_참가(리뷰어_토큰, 방번호);
        방_참가(리뷰이_토큰, 방번호);

        매칭(액세스_토큰, 방번호);

        Long 리뷰이_아이디 = 리뷰이_목록_조회(리뷰어_토큰, 방번호)
                .stream()
                .findAny()
                .map(MatchResultResponse::userId)
                .orElseThrow();


        리뷰_완료(리뷰어_토큰, 방번호, 리뷰이_아이디);

        SocialFeedbackRequest request = new SocialFeedbackRequest(
                리뷰이_아이디,
                4,
                List.of("방의 목적에 맞게 코드를 작성했어요", "코드를 이해하기 쉬웠어요"),
                "유용한 블로그나 아티클도 남겨주시고, \n 사소한 부분까지 잘 챙겨준게 좋았씁니다."
        );

        //@formatter:off
        RestAssured.given().auth().oauth2(리뷰어_토큰).body(request).contentType(ContentType.JSON)
                .when().post("/rooms/" + 방번호 + "/social/feedbacks")
                .then().statusCode(200);
        //@formatter:on
    }
}
