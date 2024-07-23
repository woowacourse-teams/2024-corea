package corea.matching.service;

import config.ServiceTest;
import corea.exception.CoreaException;
import corea.fixture.MemberFixture;
import corea.fixture.RoomFixture;
import corea.matching.dto.ParticipationRequest;
import corea.member.domain.Member;
import corea.member.repository.MemberRepository;
import corea.room.domain.Room;
import corea.room.repository.RoomRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThatCode;

@ServiceTest
class ParticipationServiceTest {

    @Autowired
    private ParticipationService sut;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("멤버가 방에 참여한다.")
    void participate() {
        Member member = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());
        Room room = roomRepository.save(RoomFixture.ROOM_DOMAIN(member));

        assertThatCode(() -> sut.participate(new ParticipationRequest(room.getId(), member.getId())))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("ID에 해당하는 방이 없으면 예외를 발생한다.")
    void participate_throw_exception_when_roomId_not_exist() {
        Member member = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());

        assertThatCode(() -> sut.participate(new ParticipationRequest(-1, member.getId())))
                .isInstanceOf(CoreaException.class);
    }

    @Test
    @DisplayName("ID에 해당하는 멤버가 없으면 예외를 발생한다.")
    void participate_throw_exception_when_memberId_not_exist() {
        Room room = roomRepository.save(RoomFixture.ROOM_DOMAIN(null));

        assertThatCode(() -> sut.participate(new ParticipationRequest(room.getId(), -1)))
                .isInstanceOf(CoreaException.class);
    }

    @Test
    @DisplayName("이미 참여중인 방이면 예외를 발생한다.")
    void participate_throw_exception_when_already_participate() {
        Member member = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());
        Room room = roomRepository.save(RoomFixture.ROOM_DOMAIN(member));

        sut.participate(new ParticipationRequest(room.getId(), member.getId()));

        assertThatCode(() -> sut.participate(new ParticipationRequest(room.getId(), member.getId())))
                .isInstanceOf(CoreaException.class);
    }
}
