package corea.scheduler.service;

import config.ServiceTest;
import corea.feedback.domain.DevelopFeedback;
import corea.feedback.domain.FeedbackKeyword;
import corea.feedback.domain.SocialFeedback;
import corea.feedback.repository.DevelopFeedbackRepository;
import corea.feedback.repository.SocialFeedbackRepository;
import corea.fixture.MemberFixture;
import corea.fixture.RoomFixture;
import corea.matching.domain.MatchResult;
import corea.matching.repository.MatchResultRepository;
import corea.member.domain.Member;
import corea.member.domain.Profile;
import corea.member.repository.MemberRepository;
import corea.review.service.ReviewService;
import corea.room.dto.RoomResponse;
import corea.room.service.RoomService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ServiceTest
class AutomaticUpdateServiceTest {

    @Autowired
    private AutomaticUpdateService automaticUpdateService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MatchResultRepository matchResultRepository;

    @Autowired
    private DevelopFeedbackRepository developFeedbackRepository;

    @Autowired
    private SocialFeedbackRepository socialFeedbackRepository;

    @MockBean
    private TaskScheduler taskScheduler;

    @Test
    @DisplayName("리뷰 마감 시간이 되면 자동으로 상태를 변경한다.")
    void updateAtReviewDeadline() {
        Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());

        LocalDateTime reviewDeadline = LocalDateTime.now().plusDays(2);
        RoomResponse response = roomService.create(manager.getId(), RoomFixture.ROOM_CREATE_REQUEST_WITH_REVIEW_DEADLINE(reviewDeadline));

        when(taskScheduler.schedule(any(Runnable.class), any(Instant.class))).thenReturn(mock(ScheduledFuture.class));

        automaticUpdateService.updateAtReviewDeadline(response);

        ArgumentCaptor<Runnable> runnableCaptor = ArgumentCaptor.forClass(Runnable.class);
        ArgumentCaptor<Instant> timeCaptor = ArgumentCaptor.forClass(Instant.class);

        verify(taskScheduler).schedule(runnableCaptor.capture(), timeCaptor.capture());
        Instant scheduledTime = timeCaptor.getValue();
        runnableCaptor.getValue().run();

        assertThat(reviewDeadline.atZone(ZoneId.of("Asia/Seoul")).toInstant()).isEqualTo(scheduledTime);
    }

    @Test
    @DisplayName("예약된 자동 업데이트를 삭제한다.")
    void cancel() {
        Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());

        LocalDateTime reviewDeadline = LocalDateTime.now().plusDays(2);
        RoomResponse response = roomService.create(manager.getId(), RoomFixture.ROOM_CREATE_REQUEST_WITH_REVIEW_DEADLINE(reviewDeadline));

        ScheduledFuture scheduledFuture = mock(ScheduledFuture.class);
        when(taskScheduler.schedule(any(Runnable.class), any(Instant.class))).thenReturn(scheduledFuture);

        automaticUpdateService.updateAtReviewDeadline(response);
        automaticUpdateService.cancel(response.id());

        verify(scheduledFuture).cancel(true);
    }

    @Test
    @Transactional
    @DisplayName("리뷰 마감 시간이 되고 리뷰를 작성했다면 리뷰어는 리뷰 작성한 개수가 증가하고 리뷰이는 리뷰 받은 개수가 증가한다.")
    void increaseReviewCount() {
        Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());

        LocalDateTime reviewDeadline = LocalDateTime.now().plusDays(2);
        RoomResponse response = roomService.create(manager.getId(), RoomFixture.ROOM_CREATE_REQUEST_WITH_REVIEW_DEADLINE(reviewDeadline));

        when(taskScheduler.schedule(any(Runnable.class), any(Instant.class))).thenReturn(mock(ScheduledFuture.class));

        Member reviewer = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());
        Member reviewee = memberRepository.save(MemberFixture.MEMBER_PORORO());
        matchResultRepository.save(new MatchResult(response.id(), reviewer, reviewee, "prLink"));

        reviewService.completeReview(response.id(), reviewer.getId(), reviewee.getId());
        automaticUpdateService.updateAtReviewDeadline(response);

        ArgumentCaptor<Runnable> runnableCaptor = ArgumentCaptor.forClass(Runnable.class);
        ArgumentCaptor<Instant> timeCaptor = ArgumentCaptor.forClass(Instant.class);

        verify(taskScheduler).schedule(runnableCaptor.capture(), timeCaptor.capture());
        runnableCaptor.getValue().run();

        assertThat(reviewer.getProfile().getDeliverCount()).isEqualTo(1);
        assertThat(reviewee.getProfile().getReceiveCount()).isEqualTo(1);
    }

    @Test
    @Transactional
    @DisplayName("피드백을 작성했다면 방이 종료되었을 때, 피드백 받은 멤버의 정보가 업데이트된다. (피드백 받은 개수 증가, 평균 평점 계산)")
    void updateDevelopFeedbackPoint() {
        Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());

        LocalDateTime reviewDeadline = LocalDateTime.now().plusDays(2);
        RoomResponse response = roomService.create(manager.getId(), RoomFixture.ROOM_CREATE_REQUEST_WITH_REVIEW_DEADLINE(reviewDeadline));

        when(taskScheduler.schedule(any(Runnable.class), any(Instant.class))).thenReturn(mock(ScheduledFuture.class));

        Member reviewer = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());
        Member reviewee = memberRepository.save(MemberFixture.MEMBER_PORORO());
        matchResultRepository.save(new MatchResult(response.id(), reviewer, reviewee, "prLink"));
        reviewService.completeReview(response.id(), reviewer.getId(), reviewee.getId());

        developFeedbackRepository.save(new DevelopFeedback(response.id(), reviewer, reviewee, 5, List.of(FeedbackKeyword.EASY_TO_UNDERSTAND_THE_CODE), "", 3));

        automaticUpdateService.updateAtReviewDeadline(response);

        ArgumentCaptor<Runnable> runnableCaptor = ArgumentCaptor.forClass(Runnable.class);
        ArgumentCaptor<Instant> timeCaptor = ArgumentCaptor.forClass(Instant.class);

        verify(taskScheduler).schedule(runnableCaptor.capture(), timeCaptor.capture());
        runnableCaptor.getValue().run();

        Profile profile = reviewee.getProfile();
        assertThat(profile.getFeedbackCount()).isEqualTo(1);
        assertThat(profile.getAverageRatingValue()).isEqualTo(5);
    }

    @Test
    @Transactional
    @DisplayName("피드백을 작성했다면 방이 종료되었을 때, 피드백 받은 멤버의 정보가 업데이트된다. (피드백 받은 개수 증가, 평균 평점 계산)")
    void updateSocialFeedbackPoint() {
        Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());

        LocalDateTime reviewDeadline = LocalDateTime.now().plusDays(2);
        RoomResponse response = roomService.create(manager.getId(), RoomFixture.ROOM_CREATE_REQUEST_WITH_REVIEW_DEADLINE(reviewDeadline));

        when(taskScheduler.schedule(any(Runnable.class), any(Instant.class))).thenReturn(mock(ScheduledFuture.class));

        Member reviewer = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());
        Member reviewee = memberRepository.save(MemberFixture.MEMBER_PORORO());
        matchResultRepository.save(new MatchResult(response.id(), reviewer, reviewee, "prLink"));
        reviewService.completeReview(response.id(), reviewer.getId(), reviewee.getId());

        socialFeedbackRepository.save(new SocialFeedback(response.id(), reviewee, reviewer, 5, List.of(FeedbackKeyword.GOOD_AT_EXPLAINING), ""));

        automaticUpdateService.updateAtReviewDeadline(response);

        ArgumentCaptor<Runnable> runnableCaptor = ArgumentCaptor.forClass(Runnable.class);
        ArgumentCaptor<Instant> timeCaptor = ArgumentCaptor.forClass(Instant.class);

        verify(taskScheduler).schedule(runnableCaptor.capture(), timeCaptor.capture());
        runnableCaptor.getValue().run();

        Profile profile = reviewer.getProfile();
        assertThat(profile.getFeedbackCount()).isEqualTo(1);
        assertThat(profile.getAverageRatingValue()).isEqualTo(5);
    }
}
