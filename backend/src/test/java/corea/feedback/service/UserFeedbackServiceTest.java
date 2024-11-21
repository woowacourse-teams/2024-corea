package corea.feedback.service;

import config.ServiceTest;
import corea.feedback.domain.FeedbackKeyword;
import corea.feedback.domain.SocialFeedback;
import corea.feedback.dto.FeedbackResponse;
import corea.feedback.dto.FeedbacksResponse;
import corea.feedback.dto.UserFeedbackResponse;
import corea.feedback.repository.DevelopFeedbackRepository;
import corea.feedback.repository.SocialFeedbackRepository;
import corea.fixture.*;
import corea.matchresult.repository.MatchResultRepository;
import corea.member.domain.Member;
import corea.member.repository.MemberRepository;
import corea.room.domain.Room;
import corea.room.domain.RoomStatus;
import corea.room.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
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

    @Autowired
    private MatchResultRepository matchResultRepository;

    @Test
    @DisplayName("작성한 피드백들을 가져온다.")
    void findFeedbacksWithEachRoom() {
        Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
        Room room1 = roomRepository.save(RoomFixture.ROOM_DOMAIN_WITH_CLOSED(manager));
        Room room2 = roomRepository.save(RoomFixture.ROOM_DOMAIN_WITH_CLOSED(manager));
        Room room3 = roomRepository.save(RoomFixture.ROOM_DOMAIN_WITH_PROGRESS(manager));
        Member reviewer = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Member reviewee = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());

        matchResultRepository.save(MatchResultFixture.MATCH_RESULT_DOMAIN(room1.getId(), reviewer, reviewee));
        matchResultRepository.save(MatchResultFixture.MATCH_RESULT_DOMAIN(room2.getId(), reviewer, reviewee));
        matchResultRepository.save(MatchResultFixture.MATCH_RESULT_DOMAIN(room3.getId(), reviewer, reviewee));
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

        matchResultRepository.save(MatchResultFixture.MATCH_RESULT_DOMAIN(room1.getId(), member1, member2));
        matchResultRepository.save(MatchResultFixture.MATCH_RESULT_DOMAIN(room1.getId(), member2, member1));
        matchResultRepository.save(MatchResultFixture.MATCH_RESULT_DOMAIN(room2.getId(), member1, member2));
        matchResultRepository.save(MatchResultFixture.MATCH_RESULT_DOMAIN(room2.getId(), manager, member1));
        developFeedbackRepository.save(DevelopFeedbackFixture.POSITIVE_FEEDBACK(room1.getId(), member1, member2));
        developFeedbackRepository.save(DevelopFeedbackFixture.POSITIVE_FEEDBACK(room1.getId(), member2, member1));
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
    @DisplayName("자신이 해준 피드백을 가져올 때, 방이 최근에 종료된 순으로 가져온다.")
    void getDeliveredFeedback() {
        Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
        LocalDateTime now = LocalDateTime.now();
        Room room1 = roomRepository.save(RoomFixture.ROOM_DOMAIN(manager, now.plusHours(1), now.plusDays(1), RoomStatus.CLOSE));
        Room room2 = roomRepository.save(RoomFixture.ROOM_DOMAIN(manager, now.plusHours(1), now.plusDays(2), RoomStatus.CLOSE));
        Member member1 = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Member member2 = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());

        matchResultRepository.save(MatchResultFixture.MATCH_RESULT_DOMAIN(room1.getId(), member1, member2));
        matchResultRepository.save(MatchResultFixture.MATCH_RESULT_DOMAIN(room2.getId(), member1, member2));
        developFeedbackRepository.save(DevelopFeedbackFixture.POSITIVE_FEEDBACK(room1.getId(), member1, member2));
        developFeedbackRepository.save(DevelopFeedbackFixture.POSITIVE_FEEDBACK(room2.getId(), member1, member2));

        UserFeedbackResponse deliveredFeedback = userFeedbackService.getDeliveredFeedback(member1.getId());
        List<FeedbacksResponse> feedbacksResponses = deliveredFeedback.feedbacks();

        assertAll(
                () -> assertThat(feedbacksResponses.get(0).roomId()).isEqualTo(room2.getId()),
                () -> assertThat(feedbacksResponses.get(1).roomId()).isEqualTo(room1.getId())
        );
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

        matchResultRepository.save(MatchResultFixture.MATCH_RESULT_DOMAIN(room1.getId(), reviewer1, reviewee));
        matchResultRepository.save(MatchResultFixture.MATCH_RESULT_DOMAIN(room2.getId(), reviewer1, reviewee));
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
    void getReceivedFeedback1() {
        Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
        Room room1 = roomRepository.save(RoomFixture.ROOM_DOMAIN_WITH_CLOSED(manager));
        Member reviewer1 = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Member reviewer2 = memberRepository.save(MemberFixture.MEMBER_ASH());
        Member reviewee = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());

        matchResultRepository.save(MatchResultFixture.MATCH_RESULT_DOMAIN(room1.getId(), reviewer1, reviewee));
        developFeedbackRepository.save(DevelopFeedbackFixture.POSITIVE_FEEDBACK(room1.getId(), reviewer1, reviewee));
        saveRevieweeToReviewer(room1.getId(), reviewer1, reviewee);
        saveRevieweeToReviewer(room1.getId(), reviewer2, reviewee);

        UserFeedbackResponse response = userFeedbackService.getReceivedFeedback(reviewee.getId());
        List<FeedbackResponse> feedbackData = response.feedbacks()
                .get(0)
                .developFeedback();
        assertThat(feedbackData).hasSize(1);
    }

    @Test
    @DisplayName("자신이 받은 피드백을 가져올 때, 방이 최근에 종료된 순으로 가져온다.")
    void getReceivedFeedback2() {
        Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
        LocalDateTime now = LocalDateTime.now();
        Room room1 = roomRepository.save(RoomFixture.ROOM_DOMAIN(manager, now.plusHours(1), now.plusDays(1), RoomStatus.CLOSE));
        Room room2 = roomRepository.save(RoomFixture.ROOM_DOMAIN(manager, now.plusHours(1), now.plusDays(2), RoomStatus.CLOSE));
        Member member1 = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Member member2 = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());

        matchResultRepository.save(MatchResultFixture.MATCH_RESULT_DOMAIN(room1.getId(), member1, member2));
        matchResultRepository.save(MatchResultFixture.MATCH_RESULT_DOMAIN(room2.getId(), member1, member2));
        developFeedbackRepository.save(DevelopFeedbackFixture.POSITIVE_FEEDBACK(room1.getId(), member1, member2));
        developFeedbackRepository.save(DevelopFeedbackFixture.POSITIVE_FEEDBACK(room2.getId(), member1, member2));

        UserFeedbackResponse receivedFeedback = userFeedbackService.getReceivedFeedback(member2.getId());
        List<FeedbacksResponse> feedbacksResponses = receivedFeedback.feedbacks();

        assertAll(
                () -> assertThat(feedbacksResponses.get(0).roomId()).isEqualTo(room2.getId()),
                () -> assertThat(feedbacksResponses.get(1).roomId()).isEqualTo(room1.getId())
        );
    }

    private void saveRevieweeToReviewer(long roomId, Member reviewee, Member reviewer) {
        socialFeedbackRepository.save(
                new SocialFeedback(null, roomId, reviewer, reviewee, 4, List.of(FeedbackKeyword.POSITIVE_SOCIAL_FEEDBACK_4, FeedbackKeyword.POSITIVE_SOCIAL_FEEDBACK_1), "유용한 정보들이 많았어요")
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
    @DisplayName("내가 피드백을 작성하지 않은 상대로부터 받은 피드백은 빈 응답으로 치환한다.")
    void feedbackMaskingTest() {
        Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
        Room room1 = roomRepository.save(RoomFixture.ROOM_DOMAIN_WITH_CLOSED(manager));
        Member reviewer1 = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Member reviewer2 = memberRepository.save(MemberFixture.MEMBER_ASH());
        Member reviewer3 = memberRepository.save(MemberFixture.MEMBER_MOVIN());
        Member reviewee = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());

        matchResultRepository.save(MatchResultFixture.MATCH_RESULT_DOMAIN(room1.getId(), reviewer1, reviewee));
        matchResultRepository.save(MatchResultFixture.MATCH_RESULT_DOMAIN(room1.getId(), reviewer2, reviewee));
        matchResultRepository.save(MatchResultFixture.MATCH_RESULT_DOMAIN(room1.getId(), reviewer3, reviewee));
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

    @Nested
    @DisplayName("피드백을 조회할 때, pr링크 및 review링크를 같이 조회할 수 있다.")
    class readFeedbackWithLink {

        private Member reviewer;
        private Member reviewee;

        @BeforeEach
        void setUp() {
            Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
            Room room1 = roomRepository.save(RoomFixture.ROOM_DOMAIN_WITH_CLOSED(manager));
            reviewer = memberRepository.save(MemberFixture.MEMBER_PORORO());
            reviewee = memberRepository.save(MemberFixture.MEMBER_MOVIN());

            matchResultRepository.save(MatchResultFixture.MATCH_RESULT_DOMAIN(room1.getId(), reviewer, reviewee));
            developFeedbackRepository.save(DevelopFeedbackFixture.POSITIVE_FEEDBACK(room1.getId(), reviewer, reviewee));
            socialFeedbackRepository.save(SocialFeedbackFixture.POSITIVE_FEEDBACK(room1.getId(), reviewee, reviewer));
        }

        @Test
        @DisplayName("받은 피드백 중, 개발 피드백에 대해서는 reviewLink를 같이 전달한다.")
        void receivedFeedbackWithReviewLink() {
            UserFeedbackResponse response = userFeedbackService.getReceivedFeedback(reviewee.getId());
            List<FeedbackResponse> developFeedbacks = response.feedbacks()
                    .get(0)
                    .developFeedback();

            assertThat(developFeedbacks.get(0).link()).isEqualTo("reviewLink");
        }

        @Test
        @DisplayName("받은 피드백 중, 소셜 피드백에 대해서는 prLink를 같이 전달한다.")
        void receivedFeedbackWithPrLink() {
            UserFeedbackResponse response = userFeedbackService.getReceivedFeedback(reviewer.getId());
            List<FeedbackResponse> socialFeedbacks = response.feedbacks()
                    .get(0)
                    .socialFeedback();

            assertThat(socialFeedbacks.get(0).link()).isEqualTo("https://github.com/woowacourse-teams/2024-corea/pull/99");
        }

        @Test
        @DisplayName("쓴 피드백 중, 개발 피드백에 대해서는 prLink를 같이 전달한다.")
        void deliveredFeedbackWithPrLink() {
            UserFeedbackResponse response = userFeedbackService.getDeliveredFeedback(reviewer.getId());
            List<FeedbackResponse> developFeedbacks = response.feedbacks()
                    .get(0)
                    .developFeedback();

            assertThat(developFeedbacks.get(0).link()).isEqualTo("https://github.com/woowacourse-teams/2024-corea/pull/99");
        }

        @Test
        @DisplayName("쓴 피드백 중, 소셜 피드백에 대해서는 reviewLink를 같이 전달한다.")
        void deliveredFeedbackWithReviewLink() {
            UserFeedbackResponse response = userFeedbackService.getDeliveredFeedback(reviewee.getId());
            List<FeedbackResponse> socialFeedbacks = response.feedbacks()
                    .get(0)
                    .socialFeedback();

            assertThat(socialFeedbacks.get(0).link()).isEqualTo("reviewLink");
        }
    }
}
