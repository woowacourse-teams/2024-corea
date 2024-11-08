package corea.review.controller;

import config.ControllerTest;
import corea.alarm.domain.UserToUserAlarmReader;
import corea.auth.service.TokenService;
import corea.fixture.GithubFixture;
import corea.fixture.MemberFixture;
import corea.fixture.RoomFixture;
import corea.matchresult.domain.MatchResult;
import corea.matchresult.repository.MatchResultRepository;
import corea.member.domain.Member;
import corea.member.repository.MemberRepository;
import corea.review.dto.ReviewRequest;
import corea.review.infrastructure.GithubReviewProvider;
import corea.room.domain.Room;
import corea.room.repository.RoomRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;

@ControllerTest
class ReviewControllerTest {

    @MockBean
    private GithubReviewProvider reviewProvider;

    @Autowired
    private UserToUserAlarmReader userToUserAlarmReader;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MatchResultRepository matchResultRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private RoomRepository roomRepository;

    @Test
    @DisplayName("리뷰를 완료하면 알림이 생성된다.")
    public void create_alarm() {
        Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
        Member reviewer = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());
        Member reviewee = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Room room = roomRepository.save(RoomFixture.ROOM_DOMAIN_WITH_PROGRESS(manager));
        Mockito.when(reviewProvider.provideReviewInfo(anyString()))
                .thenReturn(
                        GithubFixture.PULL_REQUEST_REVIEW_INFO(reviewer.getGithubUserId())
                );
        matchResultRepository.save(new MatchResult(room.getId(), reviewer, reviewee, "prLink"));

        String token = tokenService.createAccessToken(reviewer);
        //@formatter:off
        RestAssured.given().auth().oauth2(token)
                .body(new ReviewRequest(room.getId(),reviewee.getId())).contentType(ContentType.JSON)
                .when().post("/review/complete")
                .then().assertThat().statusCode(200);
        //@formatter:on

        long count = userToUserAlarmReader.countReceivedAlarm(reviewee, false);
        assertThat(count).isOne();
    }
}
