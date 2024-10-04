package corea.scheduler.service;

import config.ServiceTest;
import corea.participation.domain.ParticipationStatus;
import corea.room.dto.RoomResponse;
import corea.room.service.RoomService;
import corea.scheduler.domain.AutomaticMatching;
import corea.scheduler.repository.AutomaticMatchingRepository;
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
class AutomaticMatchingServiceTest {

    @Autowired
    private AutomaticMatchingService automaticMatchingService;

    @Autowired
    private AutomaticMatchingRepository automaticMatchingRepository;

    @MockBean
    private RoomService roomService;

    @MockBean
    private TaskScheduler taskScheduler;

    @MockBean
    private AutomaticMatchingExecutor automaticMatchingExecutor;

    @Test
    @DisplayName("모집 마감 기한이 되면 매칭을 자동으로 진행한다.")
    void matchOnRecruitmentDeadline() {
        // 현재 시간으로부터 10시간 후로 모집 마감 시간 설정
        LocalDateTime recruitmentDeadline = LocalDateTime.now().plusHours(10);
        when(roomService.create(anyLong(), any())).thenReturn(getRoomResponse(recruitmentDeadline));

        when(taskScheduler.schedule(any(Runnable.class), any(Instant.class))).thenReturn(mock(ScheduledFuture.class));

        RoomResponse response = roomService.create(anyLong(), any());
        automaticMatchingRepository.save(new AutomaticMatching(response.id(), response.recruitmentDeadline()));

        // taskScheduler를 사용하는 메소드 호출
        automaticMatchingService.matchOnRecruitmentDeadline(response);

        // taskScheduler.schedule 메소드에 전달된 인자를 캡처하기 위한 ArgumentCaptor 설정
        ArgumentCaptor<Runnable> runnableCaptor = ArgumentCaptor.forClass(Runnable.class);
        ArgumentCaptor<Instant> timeCaptor = ArgumentCaptor.forClass(Instant.class);
        // taskScheduler.schedule 메소드가 호출되었는지 확인하고 전달된 인자 캡처
        verify(taskScheduler).schedule(runnableCaptor.capture(), timeCaptor.capture());
        Instant scheduledTime = timeCaptor.getValue();
        runnableCaptor.getValue().run();

        // 예약된 시간이 설정한 모집 마감 시간과 일치하는지 확인
        assertThat(recruitmentDeadline.atZone(ZoneId.of("Asia/Seoul")).toInstant()).isEqualTo(scheduledTime);
        // automaticMatchingExecutor.execute 메소드가 호출되었는지 확인
        verify(automaticMatchingExecutor).execute(response.id());
    }

    @Test
    @DisplayName("예약된 자동 매칭을 삭제한다.")
    void cancel() {
        LocalDateTime recruitmentDeadline = LocalDateTime.now().plusHours(10);
        when(roomService.create(anyLong(), any())).thenReturn(getRoomResponse(recruitmentDeadline));
        ScheduledFuture scheduledFuture = mock(ScheduledFuture.class);
        when(taskScheduler.schedule(any(Runnable.class), any(Instant.class))).thenReturn(scheduledFuture);

        RoomResponse response = roomService.create(anyLong(), any());
        automaticMatchingRepository.save(new AutomaticMatching(response.id(), response.recruitmentDeadline()));

        automaticMatchingService.matchOnRecruitmentDeadline(response);
        automaticMatchingService.cancel(response.id());

        verify(scheduledFuture).cancel(true);
    }

    private RoomResponse getRoomResponse(LocalDateTime recruitmentDeadline) {
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
                recruitmentDeadline,
                LocalDateTime.now().plusDays(3),
                ParticipationStatus.PARTICIPATED,
                "OPEN");
    }
}
