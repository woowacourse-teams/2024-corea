package corea.review.service;

import config.ServiceTest;
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
import corea.review.infrastructure.GithubCommentClient;
import corea.review.infrastructure.GithubReviewClient;
import corea.room.domain.Room;
import corea.room.repository.RoomRepository;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

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
    private GithubReviewClient githubReviewClient;

    @MockBean
    private GithubCommentClient githubCommentClient;

    @Test
    @Transactional
    @DisplayName("리뷰를 완료한다.")
    void completeReview() {
        Member reviewer = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());
        Member reviewee = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Room room = roomRepository.save(RoomFixture.ROOM_DOMAIN_WITH_PROGRESS(memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON())));
        MatchResult matchResult = matchResultRepository.save(MatchResultFixture.MATCH_RESULT_DOMAIN(room.getId(), reviewer, reviewee));

        when(githubReviewClient.getPullRequestReviews(anyString()))
                .thenReturn(List.of(
                        new GithubPullRequestReview(
                                "id",
                                new GithubUserInfo(
                                        reviewer.getUsername(),
                                        reviewer.getName(),
                                        reviewer.getThumbnailUrl(),
                                        reviewer.getEmail(),
                                        String.valueOf(reviewer.getGithubUserId())),
                                "html_url"))
                );
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

        when(githubReviewClient.getPullRequestReviews(anyString())).thenReturn(Collections.emptyList());
        when(githubCommentClient.getPullRequestComments(anyString())).thenReturn(Collections.emptyList());

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
                .isEqualTo(ExceptionType.ROOM_STATUS_INVALID);
    }

    @Test
    @DisplayName("방과 멤버들에 해당하는 매칭결과가 없으면 예외를 발생한다.")
    void completeReview_throw_exception_when_not_exist_room_and_members() {
        assertThatThrownBy(() -> reviewService.completeReview(-1, -1, -1))
                .isInstanceOf(CoreaException.class);
    }
}
