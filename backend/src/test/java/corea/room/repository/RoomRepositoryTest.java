package corea.room.repository;

import corea.DataInitializer;
import corea.room.domain.Room;
import corea.room.domain.RoomClassification;
import corea.room.domain.RoomStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@SpringBootTest
@Import(DataInitializer.class)
@ActiveProfiles("test")
class RoomRepositoryTest {

    @Autowired
    private RoomRepository roomRepository;

    @ParameterizedTest
    @CsvSource(value = {"ANDROID, 2", "FRONTEND, 1", "BACKEND, 3"})
    @DisplayName("자신이 참여하지 않고, 계속 모집 중인 방들을 조회할 수 있다.")
    void findAllByMemberAndClassificationAndStatus(RoomClassification classification, int expectedSize) {
        Page<Room> rooms = roomRepository.findAllByMemberAndClassificationAndStatus(1, classification, RoomStatus.OPEN, PageRequest.of(0, 8));

        assertThat(rooms.getContent()).hasSize(expectedSize);
    }

    @Test
    @DisplayName("분야와 상관 없이 자신이 참여하지 않고, 계속 모집 중인 방들을 조회할 수 있다.")
    void findAllByMemberAndStatus() {
        Page<Room> roomsWithPage = roomRepository.findAllByMemberAndStatus(1, RoomStatus.OPEN, PageRequest.of(0, 8));
        List<Room> rooms = roomsWithPage.getContent();

        assertSoftly(softly -> {
            softly.assertThat(rooms.get(0).getId()).isEqualTo(2);
            softly.assertThat(rooms.get(1).getId()).isEqualTo(3);
            softly.assertThat(rooms.get(2).getId()).isEqualTo(4);
            softly.assertThat(rooms.get(3).getId()).isEqualTo(5);
        });
    }

    @Test
    @DisplayName("리뷰 마감일이 임박한 순으로 방 리스트를 반환한다.")
    void findAllByIdInOrderByReviewDeadlineAsc() {
        List<Long> roomIds = new ArrayList<>(List.of(2L, 1L, 4L, 6L, 3L, 5L));
        List<Room> participatedRooms = roomRepository.findAllByIdInOrderByReviewDeadlineAsc(roomIds);

        List<LocalDateTime> reviewDeadLines = participatedRooms.stream()
                .map(Room::getReviewDeadline)
                .toList();

        assertThat(reviewDeadLines).isSortedAccordingTo(LocalDateTime::compareTo);
    }
}
