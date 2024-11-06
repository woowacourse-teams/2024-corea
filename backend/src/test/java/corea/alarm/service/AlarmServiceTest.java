package corea.alarm.service;

import config.ServiceTest;
import corea.alarm.domain.UserToUserAlarmRepository;
import corea.alarm.dto.AlarmCountResponse;
import corea.fixture.AlarmFixture;
import corea.fixture.MemberFixture;
import corea.member.domain.Member;
import corea.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@ServiceTest
class AlarmServiceTest {

    @Autowired
    AlarmService alarmService;

    @Autowired
    UserToUserAlarmRepository userToUserAlarmRepository;

    @Autowired
    MemberRepository memberRepository;

    Member actor;
    Member receiver;
    Member notReceiver;
    long interactionId = 3L;

    @BeforeEach
    void setUp(){
        this.actor = memberRepository.save(MemberFixture.MEMBER_MOVIN());
        this.receiver = memberRepository.save(MemberFixture.MEMBER_PORORO());
        this.notReceiver = memberRepository.save(MemberFixture.MEMBER_ASH());
    }

    @Test
    @DisplayName("자신에게 작성된 알람들을 가져온다.")
    void get_alarm_count() {
        userToUserAlarmRepository.save(AlarmFixture.REVIEW_COMPLETE(actor.getId(), receiver.getId(), interactionId));
        userToUserAlarmRepository.save(AlarmFixture.REVIEW_COMPLETE(actor.getId(), notReceiver.getId(), interactionId));
        AlarmCountResponse response = alarmService.getNotReadAlarmCount(receiver.getId());
        assertThat(response.count()).isEqualTo(1);
    }

    @Test
    @DisplayName("읽지 않은 알람만 가져온다.")
    void get_only_not_read_alarm_count() {
        userToUserAlarmRepository.save(AlarmFixture.REVIEW_COMPLETE(actor.getId(), receiver.getId(), interactionId));
        userToUserAlarmRepository.save(AlarmFixture.READ_REVIEW_COMPLETE(actor.getId(), receiver.getId(), interactionId));
        AlarmCountResponse response = alarmService.getNotReadAlarmCount(receiver.getId());
        assertThat(response.count()).isEqualTo(1);
    }
}
