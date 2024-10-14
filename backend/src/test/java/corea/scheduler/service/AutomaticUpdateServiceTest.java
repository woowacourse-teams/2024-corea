package corea.scheduler.service;

import config.ServiceTest;
import corea.fixture.MemberFixture;
import corea.fixture.RoomFixture;
import corea.member.domain.Member;
import corea.member.repository.MemberRepository;
import corea.room.domain.Room;
import corea.room.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import static org.assertj.core.api.Assertions.assertThat;

@ServiceTest
class AutomaticUpdateServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TaskScheduler taskScheduler;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private AutomaticUpdateExecutor automaticUpdateExecutor;

    private Map<Long, ScheduledFuture<?>> scheduledTasks;
    private AutomaticUpdateService automaticUpdateService;

    @BeforeEach
    void setup() {
        this.scheduledTasks = new HashMap<>();
        this.automaticUpdateService = new AutomaticUpdateService(taskScheduler, automaticUpdateExecutor, scheduledTasks);
    }

    @Test
    @DisplayName("마감 기한에 맞게 자동 업데이트를 등록한다.")
    void updateAtReviewDeadline() {
        Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
        LocalDateTime reviewDeadline = LocalDateTime.now()
                .plusDays(2);
        Room room = roomRepository.save(RoomFixture.ROOM_DOMAIN_REVIEW_DEADLINE(manager, reviewDeadline));

        automaticUpdateService.updateAtReviewDeadline(room);

        assertThat(scheduledTasks.containsKey(room.getId())).isTrue();
    }

    @Test
    @DisplayName("예약된 자동 업데이트를 삭제한다.")
    void cancel() {
        Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
        LocalDateTime reviewDeadline = LocalDateTime.now()
                .plusDays(2);
        Room room = roomRepository.save(RoomFixture.ROOM_DOMAIN_REVIEW_DEADLINE(manager, reviewDeadline));
        automaticUpdateService.updateAtReviewDeadline(room);
        ScheduledFuture<?> scheduledFuture = scheduledTasks.get(room.getId());

        automaticUpdateService.cancel(room.getId());

        assertThat(scheduledFuture.isCancelled()).isTrue();
        assertThat(scheduledTasks.containsKey(room.getId())).isFalse();
    }

    @Test
    @DisplayName("예약된 자동 업데이트를 수정한다.")
    void update() {
        Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
        LocalDateTime reviewDeadline = LocalDateTime.now()
                .plusDays(2);
        Room room = roomRepository.save(RoomFixture.ROOM_DOMAIN_REVIEW_DEADLINE(manager, reviewDeadline));
        automaticUpdateService.updateAtReviewDeadline(room);
        ScheduledFuture<?> task = scheduledTasks.get(room.getId());

        automaticUpdateService.modifyTask(room);
        ScheduledFuture<?> updateTask = scheduledTasks.get(room.getId());

        assertThat(task.isCancelled()).isTrue();
        assertThat(updateTask.isCancelled()).isFalse();
    }

//    @Test
//    @Transactional
//    @DisplayName("리뷰 마감 시간이 되고 리뷰를 작성했다면 리뷰어는 리뷰 작성한 개수가 증가하고 리뷰이는 리뷰 받은 개수가 증가한다.")
//    void increaseReviewCount() {
//        Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
//
//        LocalDateTime reviewDeadline = LocalDateTime.now()
//                .plusDays(2);
//        RoomResponse response = roomService.create(manager.getId(), RoomFixture.ROOM_CREATE_REQUEST_WITH_REVIEW_DEADLINE(reviewDeadline));
//
//        when(githubOAuthProvider.getPullRequestReview(anyString())).thenReturn(new GithubPullRequestReview[0]);
//        when(taskScheduler.schedule(any(Runnable.class), any(Instant.class))).thenReturn(mock(ScheduledFuture.class));
//
//        Member reviewer = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());
//        Member reviewee = memberRepository.save(MemberFixture.MEMBER_PORORO());
//        MatchResult matchResult = matchResultRepository.save(new MatchResult(response.id(), reviewer, reviewee, "prLink"));
//
//        doAnswer(invocation -> {
//            matchResult.reviewComplete();
//            return null;
//        }).when(reviewService)
//                .completeReview(response.id(), reviewer.getId(), reviewee.getId());
//        reviewService.completeReview(response.id(), reviewer.getId(), reviewee.getId());
//
//        ArgumentCaptor<Runnable> runnableCaptor = ArgumentCaptor.forClass(Runnable.class);
//        ArgumentCaptor<Instant> timeCaptor = ArgumentCaptor.forClass(Instant.class);
//
//        verify(taskScheduler).schedule(runnableCaptor.capture(), timeCaptor.capture());
//        runnableCaptor.getValue()
//                .run();
//
//        assertThat(reviewer.getProfile()
//                .getDeliverCount()).isEqualTo(1);
//        assertThat(reviewee.getProfile()
//                .getReceiveCount()).isEqualTo(1);
//    }
//
//    @Test
//    @Transactional
//    @DisplayName("피드백을 작성했다면 방이 종료되었을 때, 피드백 받은 멤버의 정보가 업데이트된다. (피드백 받은 개수 증가, 평균 평점 계산)")
//    void updateDevelopFeedbackPoint() {
//        Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
//
//        LocalDateTime reviewDeadline = LocalDateTime.now()
//                .plusDays(2);
//        RoomResponse response = roomService.create(manager.getId(), RoomFixture.ROOM_CREATE_REQUEST_WITH_REVIEW_DEADLINE(reviewDeadline));
//
//        when(githubOAuthProvider.getPullRequestReview(anyString())).thenReturn(new GithubPullRequestReview[0]);
//        when(taskScheduler.schedule(any(Runnable.class), any(Instant.class))).thenReturn(mock(ScheduledFuture.class));
//
//        Member reviewer = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());
//        Member reviewee = memberRepository.save(MemberFixture.MEMBER_PORORO());
//        matchResultRepository.save(new MatchResult(response.id(), reviewer, reviewee, "prLink"));
//        reviewService.completeReview(response.id(), reviewer.getId(), reviewee.getId());
//
//        developFeedbackRepository.save(new DevelopFeedback(response.id(), reviewer, reviewee, 5, List.of(FeedbackKeyword.EASY_TO_UNDERSTAND_THE_CODE), "", 3));
//
//        ArgumentCaptor<Runnable> runnableCaptor = ArgumentCaptor.forClass(Runnable.class);
//        ArgumentCaptor<Instant> timeCaptor = ArgumentCaptor.forClass(Instant.class);
//
//        verify(taskScheduler).schedule(runnableCaptor.capture(), timeCaptor.capture());
//        runnableCaptor.getValue()
//                .run();
//
//        Profile profile = reviewee.getProfile();
//        assertThat(profile.getFeedbackCount()).isEqualTo(1);
//        assertThat(profile.getAverageRatingValue()).isEqualTo(5);
//    }
//
//    @Test
//    @Transactional
//    @DisplayName("피드백을 작성했다면 방이 종료되었을 때, 피드백 받은 멤버의 정보가 업데이트된다. (피드백 받은 개수 증가, 평균 평점 계산)")
//    void updateSocialFeedbackPoint() {
//        Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
//
//        LocalDateTime reviewDeadline = LocalDateTime.now()
//                .plusDays(2);
//        RoomResponse response = roomService.create(manager.getId(), RoomFixture.ROOM_CREATE_REQUEST_WITH_REVIEW_DEADLINE(reviewDeadline));
//
//        when(githubOAuthProvider.getPullRequestReview(anyString())).thenReturn(new GithubPullRequestReview[0]);
//        when(taskScheduler.schedule(any(Runnable.class), any(Instant.class))).thenReturn(mock(ScheduledFuture.class));
//
//        Member reviewer = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());
//        Member reviewee = memberRepository.save(MemberFixture.MEMBER_PORORO());
//        matchResultRepository.save(new MatchResult(response.id(), reviewer, reviewee, "prLink"));
//        reviewService.completeReview(response.id(), reviewer.getId(), reviewee.getId());
//
//        socialFeedbackRepository.save(new SocialFeedback(response.id(), reviewee, reviewer, 5, List.of(FeedbackKeyword.GOOD_AT_EXPLAINING), ""));
//
//        ArgumentCaptor<Runnable> runnableCaptor = ArgumentCaptor.forClass(Runnable.class);
//        ArgumentCaptor<Instant> timeCaptor = ArgumentCaptor.forClass(Instant.class);
//
//        verify(taskScheduler).schedule(runnableCaptor.capture(), timeCaptor.capture());
//        runnableCaptor.getValue()
//                .run();
//
//        Profile profile = reviewer.getProfile();
//        assertThat(profile.getFeedbackCount()).isEqualTo(1);
//        assertThat(profile.getAverageRatingValue()).isEqualTo(5);
//    }
}
