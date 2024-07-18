package corea.room.service;

import config.ServiceTest;
import corea.matching.dto.ParticipationRequest;
import corea.matching.service.ParticipationService;
import corea.member.domain.Member;
import corea.member.repository.MemberRepository;
import corea.room.dto.RoomResponse;
import corea.room.dto.RoomResponses;
import corea.room.fixture.MemberFixture;
import corea.room.fixture.RoomFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ServiceTest
class RoomServiceTest {

    @Autowired
    RoomService roomService;

    @Autowired
    ParticipationService participationService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("현재 로그인한 멤버가 참여 중인 방을 보여준다.")
    void findParticipatedRooms() {
        Member pororo = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Member ash = memberRepository.save(MemberFixture.MEMBER_ASH());
        RoomResponse roomResponse = roomService.create(RoomFixture.ROOM_CREATE_REQUEST(ash));
        participationService.participate(new ParticipationRequest(roomResponse.id(), pororo.getId()));

        RoomResponses response = roomService.findParticipatedRooms(pororo);

        List<RoomResponse> rooms = response.rooms();
        assertThat(rooms).hasSize(1);
        assertThat(rooms.get(0).author()).isEqualTo("박민아");
    }
}
