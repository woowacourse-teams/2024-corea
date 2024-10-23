package corea.room.domain;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.fixture.MemberFixture;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RoomTest {

    private Room openedRoom;

    private Room closedRoom;

    @BeforeEach
    void setUp() {
        openedRoom = new Room("제목", "내용", 2,
                "repositoryLink", "thumbnailLink", List.of("TDD", "클린코드"),
                0, 0, 2,
                MemberFixture.MEMBER_ROOM_MANAGER_JOYSON(), LocalDateTime.now()
                .plusDays(2), LocalDateTime.now()
                .plusDays(7), RoomClassification.BACKEND, RoomStatus.OPEN);

        closedRoom = new Room("제목", "내용", 2,
                "repositoryLink", "thumbnailLink", List.of("TDD", "클린코드"),
                0, 0, 2,
                MemberFixture.MEMBER_ROOM_MANAGER_JOYSON(), LocalDateTime.now()
                .plusDays(2), LocalDateTime.now()
                .plusDays(7), RoomClassification.BACKEND, RoomStatus.CLOSE);
    }

    @Test
    @DisplayName("방이 열린 상태고, 리뷰어가 방에 참여하면 카운트가 증가한다.")
    void reviewer_participate_opened_room() {
        openedRoom.increaseReviewerCount();

        assertThat(openedRoom.getReviewerCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("방이 닫힌 상태고, 리뷰어가 방에 참여하면 예외가 발생한다.")
    void reviewer_can_not_participate_closed_room() {
        assertThatThrownBy(() -> closedRoom.increaseReviewerCount())
                .asInstanceOf(InstanceOfAssertFactories.type(CoreaException.class))
                .extracting(CoreaException::getExceptionType)
                .isEqualTo(ExceptionType.ROOM_STATUS_INVALID);
    }

    @Test
    @DisplayName("방이 열린 상태고, 리뷰이가 방에 참여하면 카운트가 증가한다.")
    void reviewee_participate_opened_room() {
        openedRoom.increaseBothCount();

        assertThat(openedRoom.getBothCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("방이 닫힌 상태고, 리뷰이가 방에 참여하면 예외가 발생한다.")
    void reviewee_can_not_participate_closed_room() {
        assertThatThrownBy(() -> closedRoom.increaseBothCount())
                .asInstanceOf(InstanceOfAssertFactories.type(CoreaException.class))
                .extracting(CoreaException::getExceptionType)
                .isEqualTo(ExceptionType.ROOM_STATUS_INVALID);
    }

    @Test
    @DisplayName("방이 열린 상태고, 리뷰이가 방에 참여할때 최대 참여 인원에 꽉 찼으면 예외가 발생한다.")
    void reviewee_can_not_participate_limited_participants_room() {
        openedRoom.increaseBothCount();
        openedRoom.increaseBothCount();

        assertThatThrownBy(() -> openedRoom.increaseBothCount())
                .asInstanceOf(InstanceOfAssertFactories.type(CoreaException.class))
                .extracting(CoreaException::getExceptionType)
                .isEqualTo(ExceptionType.ROOM_PARTICIPANT_EXCEED);
    }

    @Test
    @DisplayName("방이 열린 상태면, 참가 취소할 수 있다.")
    void cancel_opened_room() {
        openedRoom.increaseReviewerCount();
        openedRoom.decreaseReviewerCount();

        assertThat(openedRoom.getReviewerCount()).isEqualTo(0);
    }

    @Test
    @DisplayName("방이 닫힌 상태면, 참가 취소할 수 없다.")
    void can_not_cancel_closed_room() {
        assertThatThrownBy(() -> closedRoom.decreaseReviewerCount())
                .isInstanceOf(CoreaException.class);
    }
}
