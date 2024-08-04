package corea.feedback.controller;

import config.ControllerTest;
import corea.feedback.dto.UserFeedbackResponse;
import corea.feedback.repository.RevieweeToReviewerFeedbackRepository;
import corea.feedback.repository.ReviewerToRevieweeFeedbackRepository;
import corea.fixture.MemberFixture;
import corea.fixture.RevieweeToReviewerFeedbackFixture;
import corea.fixture.ReviewerToRevieweeFeedbackFixture;
import corea.fixture.RoomFixture;
import corea.member.domain.Member;
import corea.member.repository.MemberRepository;
import corea.room.domain.Room;
import corea.room.repository.RoomRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
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
    private ReviewerToRevieweeFeedbackRepository reviewerToRevieweeFeedbackRepository;

    @Autowired
    private RevieweeToReviewerFeedbackRepository revieweeToReviewerFeedbackRepository;

    @Test
    @DisplayName("자신이 작성한 피드백을 받는다.")
    void deliveredFeedbacks() {
        Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
        Room room1 = roomRepository.save(RoomFixture.ROOM_DOMAIN(manager));
        Room room2 = roomRepository.save(RoomFixture.ROOM_DOMAIN(manager));
        Member member1 = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Member member2 = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());

        reviewerToRevieweeFeedbackRepository.save(ReviewerToRevieweeFeedbackFixture.POSITIVE_FEEDBACK(room1.getId(),member1,member2));
        reviewerToRevieweeFeedbackRepository.save(ReviewerToRevieweeFeedbackFixture.POSITIVE_FEEDBACK(room2.getId(),member1,member2));
        revieweeToReviewerFeedbackRepository.save(RevieweeToReviewerFeedbackFixture.POSITIVE_FEEDBACK(room1.getId(),manager,member1));


        final UserFeedbackResponse response = RestAssured.given().header("Authorization", member1.getUsername()).contentType(ContentType.JSON)
                .when().get("/user/feedbacks/delivered")
                .then().statusCode(200).extract().as(UserFeedbackResponse.class);

        assertThat(response.feedbacks()).hasSize(2);
        assertThat(response.feedbacks().get(0).socialFeedback()).hasSize(1);
        assertThat(response.feedbacks().get(0).developFeedback()).hasSize(1);
    }

    @Test
    @DisplayName("자신이 받은 피드백을 받는다.")
    void receivedFeedbacks() {
        Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
        Room room1 = roomRepository.save(RoomFixture.ROOM_DOMAIN(manager));
        Room room2 = roomRepository.save(RoomFixture.ROOM_DOMAIN(manager));
        Member member1 = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Member member2 = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());

        reviewerToRevieweeFeedbackRepository.save(ReviewerToRevieweeFeedbackFixture.POSITIVE_FEEDBACK(room1.getId(),member1,member2));
        reviewerToRevieweeFeedbackRepository.save(ReviewerToRevieweeFeedbackFixture.POSITIVE_FEEDBACK(room2.getId(),manager,member2));
        revieweeToReviewerFeedbackRepository.save(RevieweeToReviewerFeedbackFixture.POSITIVE_FEEDBACK(room1.getId(),member2,manager));

        final UserFeedbackResponse response = RestAssured.given().header("Authorization", member2.getUsername()).contentType(ContentType.JSON)
                .when().get("/user/feedbacks/received")
                .then().statusCode(200).extract().as(UserFeedbackResponse.class);


        assertThat(response.feedbacks()).hasSize(2);
        assertThat(response.feedbacks().get(0).developFeedback()).hasSize(1);
        assertThat(response.feedbacks().get(0).socialFeedback()).hasSize(1);
        assertThat(response.feedbacks().get(1).developFeedback()).hasSize(1);
    }
}
