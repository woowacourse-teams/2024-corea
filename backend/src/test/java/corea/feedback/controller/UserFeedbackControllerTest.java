package corea.feedback.controller;

import config.ControllerTest;
import corea.auth.service.TokenService;
import corea.feedback.dto.UserFeedbackResponse;
import corea.feedback.repository.DevelopFeedbackRepository;
import corea.feedback.repository.SocialFeedbackRepository;
import corea.fixture.DevelopFeedbackFixture;
import corea.fixture.MemberFixture;
import corea.fixture.RoomFixture;
import corea.fixture.SocialFeedbackFixture;
import corea.member.domain.Member;
import corea.member.repository.MemberRepository;
import corea.room.domain.Room;
import corea.room.repository.RoomRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@ControllerTest
class UserFeedbackControllerTest {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private DevelopFeedbackRepository developFeedbackRepository;

    @Autowired
    private SocialFeedbackRepository socialFeedbackRepository;

    @Autowired
    private TokenService tokenService;

    @Test
    @DisplayName("자신이 작성한 피드백을 받는다.")
    void deliveredFeedbacks() {
        Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
        Room room1 = roomRepository.save(RoomFixture.ROOM_DOMAIN(manager));
        Room room2 = roomRepository.save(RoomFixture.ROOM_DOMAIN(manager));
        Member member1 = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Member member2 = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());

        developFeedbackRepository.save(DevelopFeedbackFixture.POSITIVE_FEEDBACK(room1.getId(), member1, member2));
        developFeedbackRepository.save(DevelopFeedbackFixture.POSITIVE_FEEDBACK(room2.getId(), member1, member2));
        socialFeedbackRepository.save(SocialFeedbackFixture.POSITIVE_FEEDBACK(room1.getId(), member1, manager));

        String token = tokenService.createAccessToken(member1);

        UserFeedbackResponse response = RestAssured.given()
                .auth().oauth2(token)
                .when().get("/user/feedbacks/delivered")
                .then().statusCode(200)
                .extract().as(UserFeedbackResponse.class);

        assertThat(response.feedbacks()).hasSize(2);
        assertThat(response.feedbacks().get(0).socialFeedback()).hasSize(1);
        assertThat(response.feedbacks().get(0).developFeedback()).hasSize(1);
    }

    @Test
    @DisplayName("자신이 받은 피드백을 받는다.")
    void receivedFeedbacks() {
        Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
        Room room1 = roomRepository.save(RoomFixture.ROOM_DOMAIN_WITH_CLOSED(manager));
        Room room2 = roomRepository.save(RoomFixture.ROOM_DOMAIN_WITH_CLOSED(manager));
        Member member1 = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Member member2 = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());

        developFeedbackRepository.save(DevelopFeedbackFixture.POSITIVE_FEEDBACK(room1.getId(), member1, member2));
        developFeedbackRepository.save(DevelopFeedbackFixture.POSITIVE_FEEDBACK(room2.getId(), manager, member2));
        socialFeedbackRepository.save(SocialFeedbackFixture.POSITIVE_FEEDBACK(room1.getId(), manager, member2));

        String token = tokenService.createAccessToken(member2);

        UserFeedbackResponse response = RestAssured.given()
                .auth().oauth2(token)
                .when().get("/user/feedbacks/received")
                .then().statusCode(200)
                .extract().as(UserFeedbackResponse.class);

        assertThat(response.feedbacks()).hasSize(2);
        assertThat(response.feedbacks().get(0).developFeedback()).hasSize(1);
        assertThat(response.feedbacks().get(0).socialFeedback()).hasSize(1);
        assertThat(response.feedbacks().get(1).developFeedback()).hasSize(1);
    }

    @Test
    @DisplayName("자신이 받은 피드백은 닫혀있는 방에서 작성된 피드백만 확인할 수 있다")
    void receivedFeedbacksFromClosedRoom() {
        Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
        Room room1 = roomRepository.save(RoomFixture.ROOM_DOMAIN(manager));
        Room room2 = roomRepository.save(RoomFixture.ROOM_DOMAIN(manager));
        Room room3 = roomRepository.save(RoomFixture.ROOM_DOMAIN_WITH_CLOSED(manager));
        Member member1 = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Member member2 = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());

        developFeedbackRepository.save(DevelopFeedbackFixture.POSITIVE_FEEDBACK(room1.getId(), member1, member2));
        developFeedbackRepository.save(DevelopFeedbackFixture.POSITIVE_FEEDBACK(room2.getId(), manager, member2));
        developFeedbackRepository.save(DevelopFeedbackFixture.POSITIVE_FEEDBACK(room3.getId(), manager, member2));
        socialFeedbackRepository.save(SocialFeedbackFixture.POSITIVE_FEEDBACK(room1.getId(), manager, member2));
        socialFeedbackRepository.save(SocialFeedbackFixture.POSITIVE_FEEDBACK(room3.getId(), manager, member2));

        String token = tokenService.createAccessToken(member2);

        UserFeedbackResponse response = RestAssured.given()
                .auth().oauth2(token)
                .when().get("/user/feedbacks/received")
                .then().statusCode(200)
                .extract().as(UserFeedbackResponse.class);

        assertThat(response.feedbacks()).hasSize(1);
        assertThat(response.feedbacks().get(0).developFeedback()).hasSize(1);
        assertThat(response.feedbacks().get(0).socialFeedback()).hasSize(1);
    }
}
