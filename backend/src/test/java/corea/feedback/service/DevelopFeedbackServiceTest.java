package corea.feedback.service;

import config.ServiceTest;
import corea.exception.CoreaException;
import corea.feedback.dto.DevelopFeedbackRequest;
import corea.feedback.dto.DevelopFeedbackResponse;
import corea.fixture.MatchResultFixture;
import corea.fixture.MemberFixture;
import corea.fixture.RoomFixture;
import corea.matching.domain.MatchResult;
import corea.matching.repository.MatchResultRepository;
import corea.member.domain.Member;
import corea.member.repository.MemberRepository;
import corea.room.domain.Room;
import corea.room.repository.RoomRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ServiceTest
class DevelopFeedbackServiceTest {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MatchResultRepository matchResultRepository;

    @Autowired
    private DevelopFeedbackService developFeedbackService;

    @Test
    @Transactional
    @DisplayName("개발(리뷰어->리뷰이) 대한 피드백 내용을 생성한다.")
    void create() {
        Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
        Room room = roomRepository.save(RoomFixture.ROOM_DOMAIN(manager));
        Member deliver = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Member receiver = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());
        MatchResult matchResult = matchResultRepository.save(MatchResultFixture.MATCH_RESULT_DOMAIN(
                room.getId(),
                deliver,
                receiver
        ));

        assertThatCode(() -> developFeedbackService.create(room.getId(), deliver.getId(), createRequest(receiver.getId())))
                .doesNotThrowAnyException();
        assertThat(matchResult.isReviewerCompletedFeedback()).isTrue();
    }

    @Test
    @DisplayName("개발(리뷰어 -> 리뷰이) 에 대한 매칭 결과가 없으면 예외를 발생한다.")
    void throw_exception_when_not_exist_match_result() {
        Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
        Room room = roomRepository.save(RoomFixture.ROOM_DOMAIN(manager));
        Member deliver = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Member receiver = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());

        assertThatCode(() -> developFeedbackService.create(room.getId(), deliver.getId(), createRequest(receiver.getId())))
                .isInstanceOf(CoreaException.class);
    }

    @Test
    @DisplayName("유저네임을 통해 방에 대한 자신의 리뷰이를 검색한다.")
    void findDevelopFeedback() {
        Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
        Room room = roomRepository.save(RoomFixture.ROOM_DOMAIN(manager));
        Member deliver = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Member receiver = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());
        matchResultRepository.save(MatchResultFixture.MATCH_RESULT_DOMAIN(
                room.getId(),
                deliver,
                receiver
        ));
        developFeedbackService.create(room.getId(), deliver.getId(), createRequest(receiver.getId()));

        DevelopFeedbackResponse response = developFeedbackService.findDevelopFeedback(room.getId(), deliver.getId(), receiver.getUsername());
        assertThat(response.receiverId()).isEqualTo(receiver.getId());
    }

    @Test
    @DisplayName("개발(리뷰어 -> 리뷰이) 피드백 내용을 업데이트한다.")
    void update() {
        Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
        Room room = roomRepository.save(RoomFixture.ROOM_DOMAIN(manager));
        Member deliver = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Member receiver = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());
        matchResultRepository.save(MatchResultFixture.MATCH_RESULT_DOMAIN(
                room.getId(),
                deliver,
                receiver
        ));
        DevelopFeedbackResponse createResponse = developFeedbackService.create(room.getId(), deliver.getId(), createRequest(receiver.getId()));
        DevelopFeedbackResponse updateResponse = developFeedbackService.update(createResponse.feedbackId(), deliver.getId(), createRequest(receiver.getId()));

        assertThat(createResponse).isEqualTo(updateResponse);
    }

    @Test
    @DisplayName("없는 개발(리뷰어 -> 리뷰이) 피드백 내용을 업데이트시 예외를 발생한다.")
    void throw_exception_when_update_with_not_exist_feedback() {
        Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
        Room room = roomRepository.save(RoomFixture.ROOM_DOMAIN(manager));
        Member deliver = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Member receiver = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());
        matchResultRepository.save(MatchResultFixture.MATCH_RESULT_DOMAIN(
                room.getId(),
                deliver,
                receiver
        ));

        assertThatThrownBy(() -> developFeedbackService.update(room.getId(), deliver.getId(), createRequest(receiver.getId())))
                .isInstanceOf(CoreaException.class);
    }

    private DevelopFeedbackRequest createRequest(long receiverId) {
        return new DevelopFeedbackRequest(
                receiverId,
                4,
                List.of("방의 목적에 맞게 코드를 작성했어요", "코드를 이해하기 쉬웠어요"),
                "처음 자바를 접해봤다고 했는데 \n 생각보다 매우 구성되어 있는 코드 였던거 같습니다. ...",
                2
        );
    }
}
