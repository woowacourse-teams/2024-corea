package corea.feedback.service;

import config.ServiceTest;
import corea.alarm.domain.AlarmActionType;
import corea.alarm.domain.AlarmsByActionType;
import corea.alarm.domain.UserToUserAlarmReader;
import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.feedback.dto.SocialFeedbackCreateRequest;
import corea.feedback.dto.SocialFeedbackResponse;
import corea.feedback.dto.SocialFeedbackUpdateRequest;
import corea.fixture.MatchResultFixture;
import corea.fixture.MemberFixture;
import corea.fixture.RoomFixture;
import corea.matchresult.domain.MatchResult;
import corea.matchresult.repository.MatchResultRepository;
import corea.member.domain.Member;
import corea.member.domain.Profile;
import corea.member.repository.MemberRepository;
import corea.room.domain.Room;
import corea.room.repository.RoomRepository;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ServiceTest
class SocialFeedbackServiceTest {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MatchResultRepository matchResultRepository;

    @Autowired
    private UserToUserAlarmReader userToUserAlarmReader;

    @Autowired
    private SocialFeedbackService socialFeedbackService;

    private Member manager;
    private Room room;
    private Member deliver;
    private Member receiver;
    private MatchResult matchResult;

    @BeforeEach
    void setUp() {
        manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
        room = roomRepository.save(RoomFixture.ROOM_DOMAIN(manager));
        deliver = memberRepository.save(MemberFixture.MEMBER_PORORO());
        receiver = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());
        matchResult = matchResultRepository.save(MatchResultFixture.MATCH_RESULT_DOMAIN(
                room.getId(),
                receiver,
                deliver
        ));
    }

    //@Transactional
    @Test
    @DisplayName("소셜(리뷰이->리뷰어) 대한 피드백 내용을 생성한다.")
    void create() {
        assertThatCode(() -> socialFeedbackService.create(room.getId(), deliver.getId(), createRequest(receiver.getId())))
                .doesNotThrowAnyException();
        assertThat(matchResult.isRevieweeCompletedFeedback()).isTrue();
    }

    //@Transactional
    @Test
    @DisplayName("소셜 피드백이 작성되면 리뷰이에게 알람이 생성된다.")
    void create_alarm() {
        Assertions.assertThatCode(() -> socialFeedbackService.create(room.getId(), deliver.getId(), createRequest(receiver.getId())))
                .doesNotThrowAnyException();

        long count = userToUserAlarmReader.countReceivedAlarm(receiver, false);
        AlarmsByActionType alarms = userToUserAlarmReader.findAllByReceiver(receiver);

        assertAll(
                () -> assertThat(count).isOne(),
                () -> assertTrue(alarms.data().containsKey(AlarmActionType.FEEDBACK_CREATED))
        );
    }

    //@Transactional
    @Test
    @DisplayName("방이 close 상태가 아닐 때 피드백을 작성하면, 피드백 받은 개수가 증가하지 않는다")
    void notUpdateFeedbackPoint() {
        socialFeedbackService.create(room.getId(), deliver.getId(), createRequest(receiver.getId()));

        Profile profile = receiver.getProfile();
        assertThat(profile.getFeedbackCount()).isEqualTo(0);
    }

    @Transactional
    @Test
    @DisplayName("방이 close 상태일 때 피드백을 작성하면, 피드백 받은 개수가 바로 증가한다.")
    void updateFeedbackPoint() {
        Room closedRoom = roomRepository.save(RoomFixture.ROOM_DOMAIN_WITH_CLOSED(manager));
        matchResultRepository.save(MatchResultFixture.MATCH_RESULT_DOMAIN(
                closedRoom.getId(),
                receiver,
                deliver
        ));

        socialFeedbackService.create(closedRoom.getId(), deliver.getId(), createRequest(receiver.getId()));

        Profile profile = receiver.getProfile();
        assertThat(profile.getFeedbackCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("소셜(리뷰이->리뷰어)에 대한 매칭 결과가 없으면 예외를 발생한다.")
    void throw_exception_when_not_exist_match_result() {
        Member receiver = memberRepository.save(MemberFixture.MEMBER_MOVIN());
        Member deliver = memberRepository.save(MemberFixture.MEMBER_ASH());

        assertThatThrownBy(() -> socialFeedbackService.create(room.getId(), deliver.getId(), createRequest(receiver.getId())))
                .asInstanceOf(InstanceOfAssertFactories.type(CoreaException.class))
                .extracting(CoreaException::getExceptionType)
                .isEqualTo(ExceptionType.NOT_MATCHED_MEMBER);
    }

    @Test
    @DisplayName("소셜(리뷰이 -> 리뷰어) 에 대한 피드백이 이미 있다면 피드백을 생성할 때 예외를 발생한다.")
    void throw_exception_when_already_feedback_exist() {
        socialFeedbackService.create(room.getId(), deliver.getId(), createRequest(receiver.getId()));

        assertThatCode(() -> socialFeedbackService.create(room.getId(), deliver.getId(), createRequest(receiver.getId())))
                .asInstanceOf(InstanceOfAssertFactories.type(CoreaException.class))
                .extracting(CoreaException::getExceptionType)
                .isEqualTo(ExceptionType.ALREADY_COMPLETED_FEEDBACK);
    }

    @Test
    @DisplayName("유저네임을 통해 방에 대한 자신의 리뷰어를 검색한다.")
    void findReviewerToReviewee() {
        socialFeedbackService.create(room.getId(), deliver.getId(), createRequest(receiver.getId()));

        SocialFeedbackResponse response = socialFeedbackService.findSocialFeedback(room.getId(), deliver.getId(), receiver.getUsername());
        assertThat(response.receiverId()).isEqualTo(receiver.getId());
    }

    @Test
    @DisplayName("소셜(리뷰이->리뷰어) 피드백 내용을 업데이트한다.")
    void update() {
        SocialFeedbackResponse createResponse = socialFeedbackService.create(room.getId(), deliver.getId(), createRequest(receiver.getId()));
        SocialFeedbackResponse updateResponse = socialFeedbackService.update(createResponse.feedbackId(), deliver.getId(), updateRequest());

        assertThat(updateResponse.evaluationPoint()).isEqualTo(2);
    }

    @Test
    @DisplayName("없는 소셜(리뷰이->리뷰어) 피드백 내용을 업데이트시 예외를 발생한다.")
    void throw_exception_when_update_with_not_exist_feedback() {
        assertThatThrownBy(() -> socialFeedbackService.update(room.getId(), receiver.getId(), updateRequest()))
                .isInstanceOf(CoreaException.class);
    }

    @Test
    @DisplayName("소셜(리뷰어 -> 리뷰이) 피드백 작성자가 아닌 사람이 업데이트시 예외를 발생한다.")
    void throw_exception_when_anonymous_updates_feedback() {
        SocialFeedbackResponse createResponse = socialFeedbackService.create(room.getId(), deliver.getId(), createRequest(receiver.getId()));

        assertThatThrownBy(() -> socialFeedbackService.update(createResponse.feedbackId(), receiver.getId(), updateRequest()))
                .asInstanceOf(InstanceOfAssertFactories.type(CoreaException.class))
                .extracting(CoreaException::getExceptionType)
                .isEqualTo(ExceptionType.FEEDBACK_UPDATE_AUTHORIZATION_ERROR);
    }

    private SocialFeedbackCreateRequest createRequest(long receiverId) {
        return new SocialFeedbackCreateRequest(
                receiverId,
                4,
                List.of("방의 목적에 맞게 코드를 작성했어요", "코드를 이해하기 쉬웠어요"),
                "처음 자바를 접해봤다고 했는데 \n 생각보다 매우 구성되어 있는 코드 였던거 같습니다. ..."
        );
    }

    private SocialFeedbackUpdateRequest updateRequest() {
        return new SocialFeedbackUpdateRequest(
                2,
                List.of("설명이 부족해요"),
                "설명이 너무 부족해요..."
        );
    }
}
