package corea.room.domain;

import corea.exception.CoreaException;
import corea.fixture.MemberFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RoomTest {

    @Test
    @DisplayName("참가자 수가 최대가 되면, 방은 닫힌 상태가 된다.")
    void closed_when_participate_full() {
        Room room = new Room("제목", "내용", 2, "repositoryLink", "thumbnailLink", List.of("TDD", "클린코드"),
                0, 1,
                MemberFixture.MEMBER_ROOM_MANAGER_JOYSON(), LocalDateTime.now()
                .plusDays(2), LocalDateTime.now()
                .plusDays(7), RoomClassification.BACKEND, RoomStatus.OPENED);

        room.participate();
        assertThat(room.isClosed()).isTrue();
    }
    @Test
    @DisplayName("방이 닫힌 상태면, 참가 신청할 수 없다.")
    void can_not_participate_closed_room(){
        Room room = new Room("제목", "내용", 2, "repositoryLink", "thumbnailLink", List.of("TDD", "클린코드"),
                0, 1,
                MemberFixture.MEMBER_ROOM_MANAGER_JOYSON(), LocalDateTime.now()
                .plusDays(2), LocalDateTime.now()
                .plusDays(7), RoomClassification.BACKEND, RoomStatus.CLOSED);

        assertThatThrownBy(()->room.participate())
                .isInstanceOf(CoreaException.class);
    }

}
