package corea.room.domain;

import corea.exception.CoreaException;
import corea.fixture.MemberFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.time.LocalDateTime;
import java.util.List;

import static corea.exception.ExceptionType.ROOM_RECRUIT_FINISHED;
import static corea.exception.ExceptionType.ROOM_STATUS_INVALID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RoomTest {

    @Test
    @DisplayName("참가자 수가 최대가 되면, 더 이상 신청할 수 없다.")
    void closed_when_participate_full() {
        Room room = new Room("제목", "내용", 2, "repositoryLink", "thumbnailLink", List.of("TDD", "클린코드"),
                0, 1,
                MemberFixture.MEMBER_ROOM_MANAGER_JOYSON(), LocalDateTime.now()
                .plusDays(2), LocalDateTime.now()
                .plusDays(7), RoomClassification.BACKEND, RoomStatus.OPENED);

        room.participate();

        assertThatThrownBy(room::participate)
                .isInstanceOf(CoreaException.class)
                .hasMessageContaining("방 참여 인원 수가 최대입니다.");
    }

    @ParameterizedTest
    @EnumSource(mode = EnumSource.Mode.EXCLUDE, names = {"OPENED"})
    @DisplayName("방이 열린 상태가 아니면, 참가 신청할 수 없다.")
    void can_not_participate_closed_room(RoomStatus roomStatus) {
        Room room = new Room("제목", "내용", 2, "repositoryLink", "thumbnailLink", List.of("TDD", "클린코드"),
                0, 1,
                MemberFixture.MEMBER_ROOM_MANAGER_JOYSON(), LocalDateTime.now()
                .plusDays(2), LocalDateTime.now()
                .plusDays(7), RoomClassification.BACKEND, roomStatus);

        assertThatThrownBy(room::participate)
                .isInstanceOf(CoreaException.class)
                .satisfies(exception -> {
                    CoreaException coreaException = (CoreaException) exception;
                    assertThat(coreaException.getExceptionType()).isEqualTo(ROOM_RECRUIT_FINISHED);
                });
    }

    @ParameterizedTest
    @EnumSource(mode = EnumSource.Mode.EXCLUDE, names = {"OPENED"})
    @DisplayName("방이 열린 상태가 아니면, PROGRESS 상태가 될 수 없다.")
    void can_not_be_progress_if_status_not_opened(RoomStatus roomStatus) {
        Room room = new Room("제목", "내용", 2, "repositoryLink", "thumbnailLink", List.of("TDD", "클린코드"),
                0, 1,
                MemberFixture.MEMBER_ROOM_MANAGER_JOYSON(), LocalDateTime.now()
                .plusDays(2), LocalDateTime.now()
                .plusDays(7), RoomClassification.BACKEND, roomStatus);

        assertThatThrownBy(room::toProgress)
                .isInstanceOf(CoreaException.class)
                .satisfies(exception -> {
                    CoreaException coreaException = (CoreaException) exception;
                    assertThat(coreaException.getExceptionType()).isEqualTo(ROOM_STATUS_INVALID);
                });
    }
}
