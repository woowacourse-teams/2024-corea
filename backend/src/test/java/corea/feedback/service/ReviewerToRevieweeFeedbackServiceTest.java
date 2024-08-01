package corea.feedback.service;

import config.ServiceTest;
import corea.exception.CoreaException;
import corea.feedback.dto.ReviewerToRevieweeRequest;
import corea.feedback.dto.ReviewerToRevieweeResponse;
import corea.fixture.MatchResultFixture;
import corea.fixture.MemberFixture;
import corea.fixture.RoomFixture;
import corea.matching.repository.MatchResultRepository;
import corea.member.domain.Member;
import corea.member.repository.MemberRepository;
import corea.room.domain.Room;
import corea.room.repository.RoomRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ServiceTest
class ReviewerToRevieweeFeedbackServiceTest {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MatchResultRepository matchResultRepository;

    @Autowired
    private ReviewerToRevieweeFeedbackService reviewerToRevieweeFeedbackService;

    @Test
    @DisplayName("리뷰어->리뷰이 대한 피드백 내용을 생성한다.")
    void create() {
        Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
        Room room = roomRepository.save(RoomFixture.ROOM_DOMAIN(manager));
        Member reviewer = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Member reviewee = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());
        matchResultRepository.save(MatchResultFixture.MATCH_RESULT_DOMAIN(
                room.getId(),
                reviewer,
                reviewee
        ));

        assertThatCode(() -> reviewerToRevieweeFeedbackService.create(room.getId(), reviewer.getId(), createRequest(reviewee.getId())))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("리뷰어 -> 리뷰이에 대한 매칭 결과가 없으면 예외를 발생한다.")
    void throw_exception_when_not_exist_match_result() {
        Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
        Room room = roomRepository.save(RoomFixture.ROOM_DOMAIN(manager));
        Member reviewer = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Member reviewee = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());

        assertThatCode(() -> reviewerToRevieweeFeedbackService.create(room.getId(), reviewer.getId(), createRequest(reviewee.getId())))
                .isInstanceOf(CoreaException.class);
    }

    @Test
    @DisplayName("유저네임을 통해 방에 대한 자신의 리뷰이를 검색한다.")
    void findReviewerToReviewee() {
        Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
        Room room = roomRepository.save(RoomFixture.ROOM_DOMAIN(manager));
        Member reviewer = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Member reviewee = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());
        matchResultRepository.save(MatchResultFixture.MATCH_RESULT_DOMAIN(
                room.getId(),
                reviewer,
                reviewee
        ));
        reviewerToRevieweeFeedbackService.create(room.getId(), reviewer.getId(), createRequest(reviewee.getId()));

        ReviewerToRevieweeResponse response = reviewerToRevieweeFeedbackService.findReviewerToReviewee(room.getId(), reviewer.getId(), reviewee.getUsername());
        assertThat(response.revieweeId()).isEqualTo(reviewee.getId());
    }

    @Test
    @DisplayName("리뷰어->리뷰이 피드백 내용을 업데이트한다.")
    void update() {
        Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
        Room room = roomRepository.save(RoomFixture.ROOM_DOMAIN(manager));
        Member reviewer = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Member reviewee = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());
        matchResultRepository.save(MatchResultFixture.MATCH_RESULT_DOMAIN(
                room.getId(),
                reviewer,
                reviewee
        ));
        ReviewerToRevieweeResponse createResponse = reviewerToRevieweeFeedbackService.create(room.getId(), reviewer.getId(), createRequest(reviewee.getId()));
        ReviewerToRevieweeResponse updateResponse = reviewerToRevieweeFeedbackService.update(createResponse.feedbackId(), room.getId(), reviewer.getId(), createRequest(reviewee.getId()));

        assertThat(createResponse).isEqualTo(updateResponse);
    }

    @Test
    @DisplayName("없는 리뷰어->리뷰이 피드백 내용을 업데이트시 예외를 발생한다.")
    void throw_exception_when_update_with_not_exist_feedback() {
        Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
        Room room = roomRepository.save(RoomFixture.ROOM_DOMAIN(manager));
        Member reviewer = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Member reviewee = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());
        matchResultRepository.save(MatchResultFixture.MATCH_RESULT_DOMAIN(
                room.getId(),
                reviewer,
                reviewee
        ));

        assertThatThrownBy(() -> reviewerToRevieweeFeedbackService.update(room.getId(), -1, reviewer.getId(), createRequest(reviewee.getId())))
                .isInstanceOf(CoreaException.class);
    }

    private ReviewerToRevieweeRequest createRequest(long revieweeId) {
        return new ReviewerToRevieweeRequest(
                revieweeId,
                4,
                List.of("방의 목적에 맞게 코드를 작성했어요.", "코드를 이해하기 쉬웠어요."),
                "처음 자바를 접해봤다고 했는데 \n 생각보다 매우 구성되어 있는 코드 였던거 같습니다. ...",
                2
        );
    }
}
