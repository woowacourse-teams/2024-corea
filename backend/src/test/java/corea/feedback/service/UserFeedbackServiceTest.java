package corea.feedback.service;

import config.ServiceTest;
import corea.feedback.domain.FeedbackKeyword;
import corea.feedback.domain.RevieweeToReviewerFeedback;
import corea.feedback.dto.FeedbackResponse;
import corea.feedback.dto.FeedbacksResponse;
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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ServiceTest
class UserFeedbackServiceTest {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private UserFeedbackService userFeedbackService;

    @Autowired
    private ReviewerToRevieweeFeedbackRepository reviewerToRevieweeFeedbackRepository;

    @Autowired
    private RevieweeToReviewerFeedbackRepository revieweeToReviewerFeedbackRepository;

    @Test
    @DisplayName("방마다 작성한 피드백들을 구분해서 가져온다.")
    void findFeedbacksWithEachRoom() {
        Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
        Room room1 = roomRepository.save(RoomFixture.ROOM_DOMAIN(manager));
        Room room2 = roomRepository.save(RoomFixture.ROOM_DOMAIN(manager));
        Member reviewer = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Member reviewee = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());

        reviewerToRevieweeFeedbackRepository.save(ReviewerToRevieweeFeedbackFixture.POSITIVE_FEEDBACK(room1.getId(), reviewer, reviewee));
        reviewerToRevieweeFeedbackRepository.save(ReviewerToRevieweeFeedbackFixture.POSITIVE_FEEDBACK(room2.getId(), reviewer, reviewee));

        UserFeedbackResponse response = userFeedbackService.getDeliveredFeedback(reviewer.getId());

        assertThat(response.feedbacks()).hasSize(2);
    }

    @Test
    @DisplayName("자신이 해준 피드백들만 가져온다.")
    void findFeedbacksWithReviewer() {
        Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
        Room room = roomRepository.save(RoomFixture.ROOM_DOMAIN(manager));
        Member member1 = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Member member2 = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());

        reviewerToRevieweeFeedbackRepository.save(ReviewerToRevieweeFeedbackFixture.POSITIVE_FEEDBACK(room.getId(), member1, member2));
        reviewerToRevieweeFeedbackRepository.save(ReviewerToRevieweeFeedbackFixture.POSITIVE_FEEDBACK(room.getId(), manager, member1));

        revieweeToReviewerFeedbackRepository.save(RevieweeToReviewerFeedbackFixture.POSITIVE_FEEDBACK(room.getId(), member2, member1));

        UserFeedbackResponse response = userFeedbackService.getDeliveredFeedback(member1.getId());
        FeedbacksResponse feedbackResponses = response.feedbacks()
                .get(0);

        assertThat(feedbackResponses.developFeedback()).hasSize(1);
        assertThat(feedbackResponses.socialFeedback()).hasSize(1);

    }

    @Test
    @DisplayName("자신이 받은 피드백들만 가져온다.")
    void getReceivedFeedback() {
        Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
        Room room = roomRepository.save(RoomFixture.ROOM_DOMAIN(manager));
        Member reviewer1 = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Member reviewer2 = memberRepository.save(MemberFixture.MEMBER_ASH());
        Member reviewee = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());

        reviewerToRevieweeFeedbackRepository.save(
                ReviewerToRevieweeFeedbackFixture.POSITIVE_FEEDBACK(room.getId(), reviewer1, reviewee));
        saveRevieweeToReviewer(room.getId(), reviewer1, reviewee);
        saveRevieweeToReviewer(room.getId(), reviewer2, reviewee);

        UserFeedbackResponse response = userFeedbackService.getReceivedFeedback(reviewee.getId());
        List<FeedbackResponse> feedbackData = response.feedbacks()
                .get(0)
                .developFeedback();
        assertThat(feedbackData).hasSize(1);
    }

    private void saveRevieweeToReviewer(long roomId, Member reviewee, Member reviewer) {
        revieweeToReviewerFeedbackRepository.save(
                new RevieweeToReviewerFeedback(null, roomId, reviewer, reviewee, 4, List.of(FeedbackKeyword.REVIEW_FAST, FeedbackKeyword.KIND), "유용한 정보들이 많았어요")
        );
    }

}
