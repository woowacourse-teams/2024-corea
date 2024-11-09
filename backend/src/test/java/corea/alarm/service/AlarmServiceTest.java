package corea.alarm.service;

import config.ServiceTest;
import corea.alarm.domain.UserToUserAlarm;
import corea.alarm.domain.UserToUserAlarmRepository;
import corea.alarm.dto.AlarmCheckRequest;
import corea.alarm.dto.AlarmCountResponse;
import corea.alarm.dto.AlarmResponse;
import corea.alarm.dto.AlarmResponses;
import corea.exception.CoreaException;
import corea.fixture.AlarmFixture;
import corea.fixture.MemberFixture;
import corea.fixture.RoomFixture;
import corea.member.domain.Member;
import corea.member.repository.MemberRepository;
import corea.room.domain.Room;
import corea.room.repository.RoomRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ServiceTest
class AlarmServiceTest {

    @Autowired
    AlarmService alarmService;

    @Autowired
    UserToUserAlarmRepository userToUserAlarmRepository;

    @Autowired
    MemberRepository memberRepository;

    private Member actor;
    private Member receiver;
    private Member notReceiver;
    private Room interaction;
    private long interactionId;

    @Autowired
    private RoomRepository roomRepository;

    @BeforeEach
    void setUp() {
        actor = memberRepository.save(MemberFixture.MEMBER_MOVIN());
        receiver = memberRepository.save(MemberFixture.MEMBER_PORORO());
        notReceiver = memberRepository.save(MemberFixture.MEMBER_ASH());
        interaction = roomRepository.save(RoomFixture.ROOM_DOMAIN(memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON())));
        interactionId = interaction.getId();
    }

    @Test
    @DisplayName("자신에게 작성된 알람들을 가져온다.")
    void get_alarm_count() {
        userToUserAlarmRepository.save(AlarmFixture.REVIEW_COMPLETE(actor.getId(), receiver.getId(), interactionId));
        userToUserAlarmRepository.save(AlarmFixture.REVIEW_COMPLETE(actor.getId(), notReceiver.getId(), interactionId));
        AlarmCountResponse response = alarmService.getUnReadAlarmCount(receiver.getId());
        assertThat(response.count()).isEqualTo(1);
    }

    @Test
    @DisplayName("읽지 않은 알람만 가져온다.")
    void get_only_not_read_alarm_count() {
        userToUserAlarmRepository.save(AlarmFixture.REVIEW_COMPLETE(actor.getId(), receiver.getId(), interactionId));
        userToUserAlarmRepository.save(AlarmFixture.READ_REVIEW_COMPLETE(actor.getId(), receiver.getId(), interactionId));
        AlarmCountResponse response = alarmService.getUnReadAlarmCount(receiver.getId());
        assertThat(response.count()).isEqualTo(1);
    }

    @Test
    @DisplayName("사용자의 모든 알람을 최신순으로 가져온다.")
    void get_alarm() {
        UserToUserAlarm alarm1 = userToUserAlarmRepository.save(AlarmFixture.REVIEW_COMPLETE(actor.getId(), receiver.getId(), interactionId));
        UserToUserAlarm alarm2 = userToUserAlarmRepository.save(AlarmFixture.READ_REVIEW_COMPLETE(actor.getId(), receiver.getId(), interactionId));
        AlarmResponses responses = alarmService.getAlarm(receiver.getId());
        assertThat(responses.data()).hasSize(2)
                .usingElementComparatorIgnoringFields("createAt")
                .containsExactly(
                        AlarmResponse.from(alarm2, actor, interaction),
                        AlarmResponse.from(alarm1, actor, interaction)
                );
    }

    @Test
    @DisplayName("자신에게 해당된 알람이 아니면 예외를 발생한다.")
    void throw_exception_when_not_receive_alarm() {
        UserToUserAlarm alarm = userToUserAlarmRepository.save(AlarmFixture.REVIEW_COMPLETE(actor.getId(), receiver.getId(), interactionId));

        Assertions.assertThatThrownBy(() -> alarmService.checkAlarm(actor.getId(), new AlarmCheckRequest(alarm.getId(), "USER")))
                .isInstanceOf(CoreaException.class);
    }

    @Test
    @DisplayName("자신에게 해당된 알람이 아니면 예외를 발생한다.")
    void throw_exception_when_not_exist_alarm() {
        userToUserAlarmRepository.save(AlarmFixture.REVIEW_COMPLETE(actor.getId(), receiver.getId(), interactionId));

        Assertions.assertThatThrownBy(() -> alarmService.checkAlarm(receiver.getId(), new AlarmCheckRequest(-1, "USER")))
                .isInstanceOf(CoreaException.class);
    }

    @Test
    @Transactional
    @DisplayName("알림 체크를 한다.")
    void check_alarm() {
        UserToUserAlarm alarm = userToUserAlarmRepository.save(AlarmFixture.REVIEW_COMPLETE(actor.getId(), receiver.getId(), interactionId));

        alarmService.checkAlarm(receiver.getId(), new AlarmCheckRequest(alarm.getId(), "USER"));
        assertThat(alarm.isRead()).isTrue();
    }

    @Test
    @DisplayName("리뷰어가 리뷰하지 않은 경우 재촉 알람을 발송한다.")
    void urge_alarm() {
        userToUserAlarmRepository.save(AlarmFixture.READ_URGE_REVIEW(actor.getId(), receiver.getId(), interactionId));
        alarmService.createUrgeAlarm(actor.getId(), receiver.getId(), interactionId);

        List<UserToUserAlarm> urgeAlarms = userToUserAlarmRepository.findAllByActorIdAndReceiverIdAndInteractionId(actor.getId(), receiver.getId(), interactionId);

        assertThat(urgeAlarms).hasSize(2);
    }

    @Test
    @DisplayName("안 읽은 재촉 알람이 존재할 때 알람을 생성하지 않는다.")
    void does_not_create_urge_alarm_when_unread_urge_alarm_exist() {
        userToUserAlarmRepository.save(AlarmFixture.URGE_REVIEW(actor.getId(), receiver.getId(), interactionId));
        alarmService.createUrgeAlarm(actor.getId(), receiver.getId(), interactionId);

        List<UserToUserAlarm> urgeAlarms = userToUserAlarmRepository.findAllByActorIdAndReceiverIdAndInteractionId(actor.getId(), receiver.getId(), interactionId);

        assertThat(urgeAlarms).hasSize(1);
    }
}
