package corea.review.service;

import config.ServiceTest;
import corea.alarm.dto.AlarmResponses;
import corea.alarm.service.AlarmService;
import corea.auth.dto.GithubUserInfo;
import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.fixture.MatchResultFixture;
import corea.fixture.MemberFixture;
import corea.fixture.RoomFixture;
import corea.matchresult.domain.MatchResult;
import corea.matchresult.domain.ReviewStatus;
import corea.matchresult.repository.MatchResultRepository;
import corea.member.domain.Member;
import corea.member.repository.MemberRepository;
import corea.review.dto.GithubPullRequestReview;
import corea.review.dto.GithubPullRequestReviewInfo;
import corea.review.infrastructure.GithubReviewProvider;
import corea.room.domain.Room;
import corea.room.repository.RoomRepository;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@ServiceTest
class ReviewServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private MatchResultRepository matchResultRepository;

    @MockBean
    private GithubReviewProvider githubReviewProvider;

    @Autowired
    private AlarmService alarmService;

    @Test
    @Transactional
    @DisplayName("리뷰를 완료한다.")
    void completeReview() {
        Member reviewer = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());
        Member reviewee = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Room room = roomRepository.save(RoomFixture.ROOM_DOMAIN_WITH_PROGRESS(memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON())));
        MatchResult matchResult = matchResultRepository.save(MatchResultFixture.MATCH_RESULT_DOMAIN(room.getId(), reviewer, reviewee));

        when(githubReviewProvider.provideReviewInfo(anyString()))
                .thenReturn(new GithubPullRequestReviewInfo(
                        Map.of(
                                reviewer.getGithubUserId(),
                                new GithubPullRequestReview(
                                        "id",
                                        new GithubUserInfo(
                                                reviewer.getUsername(),
                                                reviewer.getName(),
                                                reviewer.getThumbnailUrl(),
                                                reviewer.getGithubUserId()),
                                        "html_url")
                        )));

        reviewService.completeReview(room.getId(), reviewer.getId(), reviewee.getId());

        assertThat(matchResult.getReviewStatus()).isEqualTo(ReviewStatus.COMPLETE);
    }

    @Test
    @DisplayName("리뷰를 작성하지 않았지만 코드 리뷰 완료 버튼을 누르면 예외가 발생한다.")
    void notCompleteReview() {
        Member reviewer = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());
        Member reviewee = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Room room = roomRepository.save(RoomFixture.ROOM_DOMAIN_WITH_PROGRESS(memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON())));
        matchResultRepository.save(MatchResultFixture.MATCH_RESULT_DOMAIN(room.getId(), reviewer, reviewee));

        when(githubReviewProvider.provideReviewInfo(anyString()))
                .thenReturn(new GithubPullRequestReviewInfo(Collections.emptyMap()));

        assertThatThrownBy(() -> reviewService.completeReview(room.getId(), reviewer.getId(), reviewee.getId()))
                .asInstanceOf(InstanceOfAssertFactories.type(CoreaException.class))
                .extracting(CoreaException::getExceptionType)
                .isEqualTo(ExceptionType.NOT_COMPLETE_GITHUB_REVIEW);
    }

    @Test
    @DisplayName("방이 종료되고 코드 리뷰 완료 버튼을 누르면 예외가 발생한다.")
    void completeReviewAfterRoomClosed() {
        Member reviewer = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());
        Member reviewee = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Room room = roomRepository.save(RoomFixture.ROOM_DOMAIN_WITH_CLOSED(memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON())));
        matchResultRepository.save(MatchResultFixture.MATCH_RESULT_DOMAIN(room.getId(), reviewer, reviewee));

        assertThatThrownBy(() -> reviewService.completeReview(room.getId(), reviewer.getId(), reviewee.getId()))
                .asInstanceOf(InstanceOfAssertFactories.type(CoreaException.class))
                .extracting(CoreaException::getExceptionType)
                .isEqualTo(ExceptionType.ROOM_STATUS_IS_NOT_PROGRESS);
    }

    @Test
    @DisplayName("방과 멤버들에 해당하는 매칭결과가 없으면 예외를 발생한다.")
    void completeReview_throw_exception_when_not_exist_room_and_members() {
        assertThatThrownBy(() -> reviewService.completeReview(-1, -1, -1))
                .isInstanceOf(CoreaException.class);
    }

    @Test
    @DisplayName("리뷰어에게 리뷰 재촉 알람을 발송한다.")
    void urgeReview() {
        Member reviewer = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());
        Member reviewee = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Room room = roomRepository.save(RoomFixture.ROOM_DOMAIN_WITH_PROGRESS(memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON())));
        matchResultRepository.save(MatchResultFixture.MATCH_RESULT_DOMAIN_NOT_REVIEWED(room.getId(), reviewer, reviewee));

        reviewService.urgeReview(room.getId(), reviewer.getId(), reviewee.getId());

        AlarmResponses alarm = alarmService.getAlarm(reviewer.getId());
        assertThat(alarm.data()).hasSize(1);
    }

    @Test
    @DisplayName("이미 리뷰한 리뷰어에게 리뷰 재촉 알람을 발송한 경우 예외를 발생한다.")
    void throw_exception_when_urge_already_reviewed() {
        Member reviewer = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());
        Member reviewee = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Room room = roomRepository.save(RoomFixture.ROOM_DOMAIN_WITH_PROGRESS(memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON())));
        matchResultRepository.save(MatchResultFixture.MATCH_RESULT_DOMAIN(room.getId(), reviewer, reviewee));

        assertThatThrownBy(() -> reviewService.urgeReview(room.getId(), reviewer.getId(), reviewee.getId()))
                .isInstanceOf(CoreaException.class);
    }

    @Test
    @DisplayName("진행중이 아닌 방에서 리뷰 재촉 알람을 발송한 경우 예외를 발생한다.")
    void throw_exception_when_urge_not_progress_room() {
        Member reviewer = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());
        Member reviewee = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Room room = roomRepository.save(RoomFixture.ROOM_DOMAIN_WITH_CLOSED(memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON())));
        matchResultRepository.save(MatchResultFixture.MATCH_RESULT_DOMAIN(room.getId(), reviewer, reviewee));

        assertThatThrownBy(() -> reviewService.urgeReview(room.getId(), reviewer.getId(), reviewee.getId()))
                .isInstanceOf(CoreaException.class);
    }
}
