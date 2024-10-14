package corea.scheduler.service;

import config.ServiceTest;
import config.TestAsyncConfig;
import corea.fixture.MemberFixture;
import corea.fixture.RoomFixture;
import corea.member.domain.Member;
import corea.member.repository.MemberRepository;
import corea.room.domain.Room;
import corea.room.domain.RoomStatus;
import corea.room.dto.RoomCreateRequest;
import corea.room.repository.RoomRepository;
import corea.scheduler.domain.AutomaticUpdate;
import corea.scheduler.repository.AutomaticUpdateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@ServiceTest
@Import(TestAsyncConfig.class)
class UpdateExecutorTest {

    @Autowired
    private UpdateExecutor updateExecutor;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AutomaticUpdateRepository automaticUpdateRepository;

    private Room room;

    @BeforeEach
    void setUp() {
        Member member = memberRepository.save(MemberFixture.MEMBER_PORORO());
        RoomCreateRequest request = RoomFixture.ROOM_CREATE_REQUEST();

        room = roomRepository.save(request.toEntity(member));
    }

    @Transactional
    @Test
    @DisplayName("방 상태를 변경한다.")
    void execute() {
        AutomaticUpdate automaticUpdate = automaticUpdateRepository.save(new AutomaticUpdate(room.getId(), room.getReviewDeadline()));

        updateExecutor.update(automaticUpdate.getRoomId());

        assertThat(room.getStatus()).isEqualTo(RoomStatus.CLOSE);
    }
}
