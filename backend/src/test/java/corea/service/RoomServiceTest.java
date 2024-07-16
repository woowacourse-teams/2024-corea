package corea.service;

import config.ServiceTest;
import corea.domain.Member;
import corea.dto.RoomCreateRequest;
import corea.dto.RoomResponse;
import corea.dto.RoomResponses;
import corea.fixture.MemberFixture;
import corea.fixture.RoomFixture;
import corea.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@ServiceTest
class RoomServiceTest {

    @Autowired
    RoomService roomService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("요청을 통해 방을 생성한다.")
    void create() {
        final Member member = memberRepository.save(MemberFixture.MEMBER_PORORO());

        final RoomCreateRequest request = RoomFixture.ROOM_CREATE_REQUEST(member.getId());
        final RoomResponse response = roomService.create(request);

        assertThat(response.title()).isEqualTo("레이싱 카와 함께하는 TDD");
    }

    @Test
    @DisplayName("방을 조회한다.")
    void findOne() {
        final Member member = memberRepository.save(MemberFixture.MEMBER_PORORO());
        final RoomCreateRequest request = RoomFixture.ROOM_CREATE_REQUEST(member.getId());
        final RoomResponse response = roomService.create(request);

        final RoomResponse findRoom = roomService.findOne(response.id());

        assertThat(findRoom).isEqualTo(response);
    }

    @Test
    @DisplayName("전체 방을 조회한다.")
    void findAll() {
        final Member member = memberRepository.save(MemberFixture.MEMBER_PORORO());
        final RoomCreateRequest request = RoomFixture.ROOM_CREATE_REQUEST(member.getId());
        roomService.create(request);

        final RoomResponses response = roomService.findAll();

        assertThat(response.rooms()).hasSize(1);
    }
}
