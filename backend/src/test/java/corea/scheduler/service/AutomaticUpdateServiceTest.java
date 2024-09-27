package corea.scheduler.service;

import config.ServiceTest;
import corea.room.domain.ParticipationStatus;
import corea.room.dto.RoomResponse;
import corea.room.service.RoomService;
import corea.scheduler.domain.AutomaticUpdate;
import corea.scheduler.repository.AutomaticUpdateRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.scheduling.TaskScheduler;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ServiceTest
class AutomaticUpdateServiceTest {

    @Autowired
    private AutomaticUpdateService automaticUpdateService;

    @Autowired
    private AutomaticUpdateRepository automaticUpdateRepository;

    @MockBean
    private RoomService roomService;

    @MockBean
    private TaskScheduler taskScheduler;

    @MockBean
    private AutomaticUpdateExecutor automaticUpdateExecutor;

    @Test
    @DisplayName("리뷰 마감 시간이 되면 자동으로 상태를 변경한다.")
    void updateAtReviewDeadline() {
        LocalDateTime reviewDeadline = LocalDateTime.now().plusDays(2);
        when(roomService.create(anyLong(), any())).thenReturn(getRoomResponse(reviewDeadline));

        when(taskScheduler.schedule(any(Runnable.class), any(Instant.class))).thenReturn(mock(ScheduledFuture.class));

        RoomResponse response = roomService.create(anyLong(), any());
        automaticUpdateRepository.save(new AutomaticUpdate(response.id(), response.reviewDeadline()));

        automaticUpdateService.updateAtReviewDeadline(response);

        ArgumentCaptor<Runnable> runnableCaptor = ArgumentCaptor.forClass(Runnable.class);
        ArgumentCaptor<Instant> timeCaptor = ArgumentCaptor.forClass(Instant.class);

        verify(taskScheduler).schedule(runnableCaptor.capture(), timeCaptor.capture());
        Instant scheduledTime = timeCaptor.getValue();
        runnableCaptor.getValue().run();

        assertThat(reviewDeadline.atZone(ZoneId.of("Asia/Seoul")).toInstant()).isEqualTo(scheduledTime);
        verify(automaticUpdateExecutor).execute(response.id());
    }

    @Test
    @DisplayName("예약된 자동 업데이트를 삭제한다.")
    void cancel() {
        LocalDateTime reviewDeadline = LocalDateTime.now().plusDays(2);
        when(roomService.create(anyLong(), any())).thenReturn(getRoomResponse(reviewDeadline));
        ScheduledFuture scheduledFuture = mock(ScheduledFuture.class);
        when(taskScheduler.schedule(any(Runnable.class), any(Instant.class))).thenReturn(scheduledFuture);

        RoomResponse response = roomService.create(anyLong(), any());
        automaticUpdateRepository.save(new AutomaticUpdate(response.id(), response.reviewDeadline()));

        automaticUpdateService.updateAtReviewDeadline(response);
        automaticUpdateService.cancel(response.id());

        verify(scheduledFuture).cancel(true);
    }

    private RoomResponse getRoomResponse(LocalDateTime reviewDeadline) {
        return new RoomResponse(10,
                "title",
                "content",
                "managerName",
                "repolink",
                "link",
                2,
                List.of(),
                1,
                10,
                LocalDateTime.now().plusDays(1),
                reviewDeadline,
                ParticipationStatus.PARTICIPATED,
                "OPEN");
    }
}
