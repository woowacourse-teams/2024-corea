package corea.feedback.service;

import config.ServiceTest;
import corea.exception.CoreaException;
import corea.feedback.dto.SocialFeedbackRequest;
import corea.feedback.dto.SocialFeedbackResponse;
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

import static org.assertj.core.api.AssertionsForClassTypes.*;

@ServiceTest
class SocialFeedbackServiceTest {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MatchResultRepository matchResultRepository;

    @Autowired
    private SocialFeedbackService socialFeedbackService;

    @Test
    @DisplayName("소셜(리뷰이->리뷰어) 대한 피드백 내용을 생성한다.")
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

        assertThatCode(() -> socialFeedbackService.create(room.getId(), reviewee.getId(), createRequest(reviewer.getId())))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("소셜(리뷰이->리뷰어)에 대한 매칭 결과가 없으면 예외를 발생한다.")
    void throw_exception_when_not_exist_match_result() {
        Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
        Room room = roomRepository.save(RoomFixture.ROOM_DOMAIN(manager));
        Member reviewer = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Member reviewee = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());

        assertThatCode(() -> socialFeedbackService.create(room.getId(), reviewee.getId(), createRequest(reviewer.getId())))
                .isInstanceOf(CoreaException.class);
    }

    @Test
    @DisplayName("유저네임을 통해 방에 대한 자신의 리뷰어를 검색한다.")
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
        socialFeedbackService.create(room.getId(), reviewee.getId(), createRequest(reviewer.getId()));

        SocialFeedbackResponse response = socialFeedbackService.findSocialFeedback(room.getId(), reviewee.getId(), reviewer.getUsername());
        assertThat(response.receiverId()).isEqualTo(reviewer.getId());
    }

    @Test
    @DisplayName("소셜(리뷰이->리뷰어) 피드백 내용을 업데이트한다.")
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
        SocialFeedbackResponse createResponse = socialFeedbackService.create(room.getId(), reviewee.getId(), createRequest(reviewer.getId()));
        SocialFeedbackResponse updateResponse = socialFeedbackService.update(createResponse.feedbackId(), room.getId(), reviewee.getId(), createRequest(reviewer.getId()));

        assertThat(createResponse).isEqualTo(updateResponse);
    }

    @Test
    @DisplayName("없는 소셜(리뷰이->리뷰어) 피드백 내용을 업데이트시 예외를 발생한다.")
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

        assertThatThrownBy(() -> socialFeedbackService.update(room.getId(), -1, reviewer.getId(), createRequest(reviewee.getId())))
                .isInstanceOf(CoreaException.class);
    }

    private SocialFeedbackRequest createRequest(long revieweeId) {
        return new SocialFeedbackRequest(
                revieweeId,
                4,
                List.of("방의 목적에 맞게 코드를 작성했어요.", "코드를 이해하기 쉬웠어요."),
                "처음 자바를 접해봤다고 했는데 \n 생각보다 매우 구성되어 있는 코드 였던거 같습니다. ..."
        );
    }
}
