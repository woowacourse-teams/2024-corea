package corea.service;

import config.ServiceTest;
import corea.domain.Member;
import corea.domain.Room;
import corea.dto.ParticipationRequest;
import corea.dto.ParticipationResponse;
import corea.fixture.MemberFixture;
import corea.fixture.RoomFixture;
import corea.repository.MemberRepository;
import corea.repository.RoomRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ServiceTest
class ParticipationServiceTest {

    @Autowired
    ParticipationService participationService;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("참여 가능한 방에 참여한다.")
    void participate() {
        final Member member = memberRepository.save(MemberFixture.MEMBER_PORORO());
        final Room room = roomRepository.save(RoomFixture.ROOM_RACING_CAR(member.getId()));
        final ParticipationRequest request = new ParticipationRequest(room.getId(), member.getId());

        final ParticipationResponse response = participationService.participate(request);

        assertThat(response.memberId()).isEqualTo(member.getId());
        assertThat(response.roomId()).isEqualTo(room.getId());
    }

    @Test
    @DisplayName("존재하지 않는 멤버 Id로 방에 참여하려 하면 예외가 발생한다.")
    void invalidMemberId() {
        final Member member = memberRepository.save(MemberFixture.MEMBER_PORORO());
        final Room room = roomRepository.save(RoomFixture.ROOM_RACING_CAR(member.getId()));
        final ParticipationRequest request = new ParticipationRequest(room.getId(), -1);

        assertThatThrownBy(() -> participationService.participate(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("해당 Id의 멤버가 없어 방에 참여할 수 없습니다. 입력된 멤버 Id=-1");
    }

    @Test
    @DisplayName("존재하지 않는 방 Id로 참여하려 하면 예외가 발생한다.")
    void invalidRoomId() {
        final Member member = memberRepository.save(MemberFixture.MEMBER_PORORO());
        final ParticipationRequest request = new ParticipationRequest(-1, member.getId());

        assertThatThrownBy(() -> participationService.participate(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("해당 Id의 방이 없어 참여할 수 없습니다. 입력된 방 Id=-1");
    }
}
