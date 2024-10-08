package corea.matching.service;

import config.ServiceTest;
import corea.exception.CoreaException;
import corea.fixture.MemberFixture;
import corea.fixture.RoomFixture;
import corea.matching.domain.MatchingStrategy;
import corea.member.domain.Member;
import corea.member.repository.MemberRepository;
import corea.participation.domain.Participation;
import corea.room.domain.Room;
import corea.room.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static corea.exception.ExceptionType.PARTICIPANT_SIZE_LACK;
import static org.assertj.core.api.Assertions.*;

@ServiceTest
class MatchingStrategyTest {

    @Autowired
    private MatchingStrategy matchingStrategy;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MemberRepository memberRepository;

    private Room room;
    private Member manager;
    private Member joyson;
    private Member pororo;

    private List<Participation> participations;

    @BeforeEach
    void setUp() {
        manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
        joyson = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());
        pororo = memberRepository.save(MemberFixture.MEMBER_PORORO());

        room = roomRepository.save(RoomFixture.ROOM_DOMAIN(manager));
        participations = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            participations.add(new Participation(room, joyson));
            participations.add(new Participation(room, pororo));
        }
    }

    @Test
    @DisplayName("인원 수가 매칭 사이즈보다 큰 경우 매칭을 수행한다.")
    void matchMaking() {
        int matchingSize = 3;

        assertThatCode(() -> matchingStrategy.matchPairs(participations, matchingSize))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("매칭을 수행할 때에, 인원 수가 매칭 사이즈보다 작거나 같으면 예외를 발생한다.")
    void matchMakingLackOfParticipationException() {
        int matchingSize = 9;

        assertThatThrownBy(() -> matchingStrategy.matchPairs(participations, matchingSize))
                .isInstanceOf(CoreaException.class)
                .satisfies(exception -> {
                    CoreaException coreaException = (CoreaException) exception;
                    assertThat(coreaException.getExceptionType()).isEqualTo(PARTICIPANT_SIZE_LACK);
                });
    }
}
