package corea.review.pullRequest;

import corea.fixture.MemberFixture;
import corea.fixture.RoomFixture;
import corea.matching.domain.MatchResult;
import corea.matching.domain.PullRequestInfo;
import corea.matching.service.MatchingService;
import corea.matching.service.PullRequestProvider;
import corea.member.domain.Member;
import corea.member.repository.MemberRepository;
import corea.participation.dto.ParticipationRequest;
import corea.participation.service.ParticipationService;
import corea.review.service.ReviewService;
import corea.room.domain.Room;
import corea.room.repository.RoomRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReviewPullRequestTest {

    @LocalServerPort
    int port;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MatchingService matchingService;

    @Autowired
    private PullRequestProvider pullRequestProvider;

    @Autowired
    private ParticipationService participationService;

    @Autowired
    private ReviewService reviewService;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("리뷰어가 리뷰를 작성했을 때, 해당 리뷰의 링크로 업데이트 한다.")
    void test() {
        Member manager = memberRepository.save(MemberFixture.MEMBER_MOVIN());
        Member reviewer = memberRepository.save(MemberFixture.MEMBER_PORORO_GITHUB());
        Member reviewee = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());
        Member participant = memberRepository.save(MemberFixture.MEMBER_DARR());

        Room room = roomRepository.save(RoomFixture.ROOM_PULL_REQUEST(manager));

        participationService.participate(new ParticipationRequest(room.getId(), manager.getId()));
        participationService.participate(new ParticipationRequest(room.getId(), reviewer.getId()));
        participationService.participate(new ParticipationRequest(room.getId(), reviewee.getId()));
        participationService.participate(new ParticipationRequest(room.getId(), participant.getId()));

        PullRequestInfo prInfo = pullRequestProvider.getUntilDeadline(room.getRepositoryLink(), room.getRecruitmentDeadline());
        matchingService.match(room.getId(), prInfo);

        MatchResult matchResultBeforeReview = reviewService.getMatchResult(room.getId(), reviewer.getId(), reviewee.getId());
        assertThat(matchResultBeforeReview.getReviewLink()).isEmpty();

        reviewService.review(room.getId(), reviewer.getId(), reviewee.getId());

        MatchResult matchResultAfterReview = reviewService.getMatchResult(room.getId(), reviewer.getId(), reviewee.getId());
        assertThat(matchResultAfterReview.getReviewLink()).isEqualTo("https://github.com/youngsu5582/github-api-test/pull/5#pullrequestreview-2327172283");
    }
}
