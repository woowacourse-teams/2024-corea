package corea.scheduler.service;

import config.ServiceTest;
import config.TestAsyncConfig;
import corea.fixture.MemberFixture;
import corea.fixture.RoomFixture;
import corea.member.domain.Member;
import corea.member.repository.MemberRepository;
import corea.room.domain.Room;
import corea.room.dto.RoomCreateRequest;
import corea.room.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

@ServiceTest
@Import(TestAsyncConfig.class)
public class UpdateTest {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MemberRepository memberRepository;

    protected Room room;

    @BeforeEach
    void setUp() {
        Member member = memberRepository.save(MemberFixture.MEMBER_PORORO());
        RoomCreateRequest request = RoomFixture.ROOM_CREATE_REQUEST();

        room = roomRepository.save(request.toEntity(member));
    }
}
