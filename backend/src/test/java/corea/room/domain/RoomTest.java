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

    private final Room room = new Room("제목", "내용", 2, "repositoryLink", "thumbnailLink", List.of("TDD", "클린코드"),
            0, 2,
            MemberFixture.MEMBER_ROOM_MANAGER_JOYSON(), LocalDateTime.now()
            .plusDays(2), LocalDateTime.now()
            .plusDays(7), RoomClassification.BACKEND, RoomStatus.OPEN);

    private final Room closedRoom = new Room("제목", "내용", 2, "repositoryLink", "thumbnailLink", List.of("TDD", "클린코드"),
            0, 2,
            MemberFixture.MEMBER_ROOM_MANAGER_JOYSON(), LocalDateTime.now()
            .plusDays(2), LocalDateTime.now()
            .plusDays(7), RoomClassification.BACKEND, RoomStatus.CLOSE);

    @Test
    @DisplayName("방이 열린 상태면, 참가 신청할 수 있다.")
    void participate_opened_room() {
        int currentParticipationSize = room.getCurrentParticipantsSize();
        room.participate();
        assertThat(room.getCurrentParticipantsSize()).isEqualTo(currentParticipationSize + 1);
    }

    @Test
    @DisplayName("방이 닫힌 상태면, 참가 신청할 수 없다.")
    void can_not_participate_closed_room() {
        assertThatThrownBy(() -> closedRoom.participate())
                .isInstanceOf(CoreaException.class);
    }

    @Test
    @DisplayName("방이 열린 상태면, 참가 취소할 수 있다.")
    void cancel_opened_room() {
        room.participate();
        int currentParticipationSize = room.getCurrentParticipantsSize();
        room.cancelParticipation();
        assertThat(room.getCurrentParticipantsSize()).isEqualTo(currentParticipationSize - 1);
    }

    @Test
    @DisplayName("방이 닫힌 상태면, 참가 취소할 수 없다.")
    void can_not_cancel_closed_room() {
        assertThatThrownBy(() -> closedRoom.cancelParticipation())
                .isInstanceOf(CoreaException.class);
    }
}
