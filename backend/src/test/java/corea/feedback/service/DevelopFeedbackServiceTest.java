package corea.feedback.service;

import config.ServiceTest;
import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.feedback.dto.DevelopFeedbackCreateRequest;
import corea.feedback.dto.DevelopFeedbackResponse;
import corea.feedback.dto.DevelopFeedbackUpdateRequest;
import corea.fixture.MatchResultFixture;
import corea.fixture.MemberFixture;
import corea.fixture.RoomFixture;
import corea.matchresult.domain.MatchResult;
import corea.matchresult.repository.MatchResultRepository;
import corea.member.domain.Member;
import corea.member.repository.MemberRepository;
import corea.room.domain.Room;
import corea.room.repository.RoomRepository;
import org.assertj.core.api.InstanceOfAssertFactories;
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
                .asInstanceOf(InstanceOfAssertFactories.type(CoreaException.class))
                .extracting(CoreaException::getExceptionType)
                .isEqualTo(ExceptionType.NOT_MATCHED_MEMBER);
    }

    @Test
    @DisplayName("개발(리뷰어 -> 리뷰이) 에 대한 피드백이 이미 있다면 피드백을 생성할 때 예외를 발생한다.")
    void throw_exception_when_already_feedback_exist() {
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

        assertThatCode(() -> developFeedbackService.create(room.getId(), deliver.getId(), createRequest(receiver.getId())))
                .asInstanceOf(InstanceOfAssertFactories.type(CoreaException.class))
                .extracting(CoreaException::getExceptionType)
                .isEqualTo(ExceptionType.ALREADY_COMPLETED_FEEDBACK);
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
        DevelopFeedbackResponse updateResponse = developFeedbackService.update(createResponse.feedbackId(), deliver.getId(), updateRequest());

        assertThat(updateResponse.evaluationPoint()).isEqualTo(2);
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

        assertThatThrownBy(() -> developFeedbackService.update(room.getId(), deliver.getId(), updateRequest()))
                .asInstanceOf(InstanceOfAssertFactories.type(CoreaException.class))
                .extracting(CoreaException::getExceptionType)
                .isEqualTo(ExceptionType.FEEDBACK_NOT_FOUND);
    }

    @Test
    @DisplayName("개발(리뷰어 -> 리뷰이) 피드백 작성자가 아닌 사람이 업데이트시 예외를 발생한다.")
    void throw_exception_when_anonymous_updates_feedback() {
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

        assertThatThrownBy(() -> developFeedbackService.update(createResponse.feedbackId(), receiver.getId(), updateRequest()))
                .asInstanceOf(InstanceOfAssertFactories.type(CoreaException.class))
                .extracting(CoreaException::getExceptionType)
                .isEqualTo(ExceptionType.FEEDBACK_UPDATE_AUTHORIZATION_ERROR);
    }

    private DevelopFeedbackCreateRequest createRequest(long receiverId) {
        return new DevelopFeedbackCreateRequest(
                receiverId,
                4,
                List.of("방의 목적에 맞게 코드를 작성했어요", "코드를 이해하기 쉬웠어요"),
                "처음 자바를 접해봤다고 했는데 \n 생각보다 매우 구성되어 있는 코드 였던거 같습니다. ...",
                2
        );
    }

    private DevelopFeedbackUpdateRequest updateRequest() {
        return new DevelopFeedbackUpdateRequest(
                2,
                List.of("코드를 이해하기 어려웠어요"),
                "처음 자바를 접해봤다고 했는데, 납득 했습니다.",
                1
        );
    }
}
