package corea.feedback.service;

import config.ServiceTest;
import corea.feedback.domain.FeedbackKeyword;
import corea.feedback.domain.SocialFeedback;
import corea.feedback.dto.FeedbackResponse;
import corea.feedback.dto.FeedbacksResponse;
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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@ServiceTest
class UserFeedbackServiceTest {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private UserFeedbackService userFeedbackService;

    @Autowired
    private DevelopFeedbackRepository developFeedbackRepository;

    @Autowired
    private SocialFeedbackRepository socialFeedbackRepository;

    @Test
    @DisplayName("작성한 피드백들을 가져온다.")
    void findFeedbacksWithEachRoom() {
        Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
        Room room1 = roomRepository.save(RoomFixture.ROOM_DOMAIN_WITH_CLOSED(manager));
        Room room2 = roomRepository.save(RoomFixture.ROOM_DOMAIN_WITH_CLOSED(manager));
        Room room3 = roomRepository.save(RoomFixture.ROOM_DOMAIN_WITH_PROGRESS(manager));
        Member reviewer = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Member reviewee = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());

        developFeedbackRepository.save(DevelopFeedbackFixture.POSITIVE_FEEDBACK(room1.getId(), reviewer, reviewee));
        developFeedbackRepository.save(DevelopFeedbackFixture.POSITIVE_FEEDBACK(room2.getId(), reviewer, reviewee));
        developFeedbackRepository.save(DevelopFeedbackFixture.POSITIVE_FEEDBACK(room3.getId(), reviewer, reviewee));

        UserFeedbackResponse response = userFeedbackService.getDeliveredFeedback(reviewer.getId());

        assertThat(response.feedbacks()).hasSize(3);
    }

    @Test
    @DisplayName("자신이 해준 피드백들만 가져온다.")
    void findFeedbacksWithReviewer() {
        Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
        Room room1 = roomRepository.save(RoomFixture.ROOM_DOMAIN_WITH_CLOSED(manager));
        Room room2 = roomRepository.save(RoomFixture.ROOM_DOMAIN(manager));
        Member member1 = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Member member2 = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());

        developFeedbackRepository.save(DevelopFeedbackFixture.POSITIVE_FEEDBACK(room1.getId(), member1, member2));
        developFeedbackRepository.save(DevelopFeedbackFixture.POSITIVE_FEEDBACK(room1.getId(), manager, member1));
        developFeedbackRepository.save(DevelopFeedbackFixture.POSITIVE_FEEDBACK(room2.getId(), member1, member2));
        developFeedbackRepository.save(DevelopFeedbackFixture.POSITIVE_FEEDBACK(room2.getId(), manager, member1));

        socialFeedbackRepository.save(SocialFeedbackFixture.POSITIVE_FEEDBACK(room1.getId(), member1, member2));

        UserFeedbackResponse response = userFeedbackService.getDeliveredFeedback(member1.getId());
        FeedbacksResponse feedbackResponses = response.feedbacks()
                .get(0);

        assertThat(feedbackResponses.developFeedback()).hasSize(1);
        assertThat(feedbackResponses.socialFeedback()).hasSize(1);
    }

    @Test
    @DisplayName("자신이 받은 피드백을 가져올 땐 방이 닫혀있는 피드백들만 가져온다.")
    void getReceivedFeedbackFromClosedRooms() {
        Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
        Room room1 = roomRepository.save(RoomFixture.ROOM_DOMAIN_WITH_CLOSED(manager));
        Room room2 = roomRepository.save(RoomFixture.ROOM_DOMAIN(manager));
        Member reviewer1 = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Member reviewer2 = memberRepository.save(MemberFixture.MEMBER_ASH());
        Member reviewee = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());

        developFeedbackRepository.save(DevelopFeedbackFixture.POSITIVE_FEEDBACK(room1.getId(), reviewer1, reviewee));
        saveRevieweeToReviewer(room1.getId(), reviewer1, reviewee);
        saveRevieweeToReviewer(room1.getId(), reviewer2, reviewee);

        developFeedbackRepository.save(DevelopFeedbackFixture.POSITIVE_FEEDBACK(room2.getId(), reviewer1, reviewee));
        saveRevieweeToReviewer(room2.getId(), reviewer1, reviewee);
        saveRevieweeToReviewer(room2.getId(), reviewer2, reviewee);

        UserFeedbackResponse response = userFeedbackService.getReceivedFeedback(reviewee.getId());
        List<FeedbackResponse> feedbackData = response.feedbacks()
                .get(0)
                .developFeedback();
        assertThat(feedbackData).hasSize(1);
    }

    @Test
    @DisplayName("자신이 받은 피드백들만 가져온다.")
    void getReceivedFeedback() {
        Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
        Room room1 = roomRepository.save(RoomFixture.ROOM_DOMAIN_WITH_CLOSED(manager));
        Member reviewer1 = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Member reviewer2 = memberRepository.save(MemberFixture.MEMBER_ASH());
        Member reviewee = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());

        developFeedbackRepository.save(DevelopFeedbackFixture.POSITIVE_FEEDBACK(room1.getId(), reviewer1, reviewee));
        saveRevieweeToReviewer(room1.getId(), reviewer1, reviewee);
        saveRevieweeToReviewer(room1.getId(), reviewer2, reviewee);

        UserFeedbackResponse response = userFeedbackService.getReceivedFeedback(reviewee.getId());
        List<FeedbackResponse> feedbackData = response.feedbacks()
                .get(0)
                .developFeedback();
        assertThat(feedbackData).hasSize(1);
    }

    private void saveRevieweeToReviewer(long roomId, Member reviewee, Member reviewer) {
        socialFeedbackRepository.save(
                new SocialFeedback(null, roomId, reviewer, reviewee, 4, List.of(FeedbackKeyword.REVIEW_FAST, FeedbackKeyword.KIND), "유용한 정보들이 많았어요")
        );
    }

    @Test
    @DisplayName("속한 방이 없는 경우 빈 응답을 반환한다.")
    void getFeedback_when_no_room_participated() {
        Member member = memberRepository.save(MemberFixture.MEMBER_PORORO());

        UserFeedbackResponse receivedFeedback = userFeedbackService.getReceivedFeedback(member.getId());
        UserFeedbackResponse deliveredFeedback = userFeedbackService.getDeliveredFeedback(member.getId());

        assertThat(receivedFeedback.feedbacks()).isEmpty();
        assertThat(deliveredFeedback.feedbacks()).isEmpty();
    }

    @Test
    @DisplayName("속한 방에서 작성된 피드백이 없는 경우 빈 응답을 반환한다.")
    void getFeedback_when_no_feedback_written() {
        Member member = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
        roomRepository.save(RoomFixture.ROOM_DOMAIN_WITH_CLOSED(member));

        UserFeedbackResponse receivedFeedback = userFeedbackService.getReceivedFeedback(member.getId());
        UserFeedbackResponse deliveredFeedback = userFeedbackService.getDeliveredFeedback(member.getId());

        assertThat(receivedFeedback.feedbacks()).isEmpty();
        assertThat(deliveredFeedback.feedbacks()).isEmpty();
    }

    @Test
    @DisplayName("받은 피드백 중 피드백을 작성하지 않은 상대의 피드백은 빈 응답을 반환한다.")
    void noFeedbackMaskingTest() {
        Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
        Room room1 = roomRepository.save(RoomFixture.ROOM_DOMAIN_WITH_CLOSED(manager));
        Member reviewer1 = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Member reviewer2 = memberRepository.save(MemberFixture.MEMBER_ASH());
        Member reviewer3 = memberRepository.save(MemberFixture.MEMBER_MOVIN());
        Member reviewee = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());

        developFeedbackRepository.save(DevelopFeedbackFixture.POSITIVE_FEEDBACK(room1.getId(), reviewer1, reviewee));
        developFeedbackRepository.save(DevelopFeedbackFixture.POSITIVE_FEEDBACK(room1.getId(), reviewer3, reviewee));
        saveRevieweeToReviewer(room1.getId(), reviewer1, reviewee);
        saveRevieweeToReviewer(room1.getId(), reviewer2, reviewee);

        UserFeedbackResponse response = userFeedbackService.getReceivedFeedback(reviewee.getId());
        List<FeedbackResponse> feedbackData = response.feedbacks()
                .get(0)
                .developFeedback();
        FeedbackResponse unmaskedFeedbackData = feedbackData.get(0);
        FeedbackResponse maskedFeedbackData = feedbackData.get(1);

        assertAll(
                () -> assertThat(feedbackData).hasSize(2),
                () -> assertThat(unmaskedFeedbackData.isWrited()).isTrue(),
                () -> assertThat(unmaskedFeedbackData.feedbackText()).isNotEmpty(),
                () -> assertThat(maskedFeedbackData.isWrited()).isFalse(),
                () -> assertThat(maskedFeedbackData.feedbackText()).isEmpty()
        );
    }
}
