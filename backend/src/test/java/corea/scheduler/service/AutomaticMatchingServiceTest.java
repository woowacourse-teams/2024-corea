package corea.scheduler.service;

import config.ServiceTest;
import corea.room.dto.RoomResponse;
import corea.room.service.RoomService;
import corea.scheduler.domain.AutomaticMatching;
import corea.scheduler.repository.AutomaticMatchingRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

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
    private AutomaticMatchingExecutor automaticMatchingExecutor;

    @Test
    @DisplayName("모집 마감 기한이 되면 매칭을 자동으로 진행한다.")
    void matchOnRecruitmentDeadline() throws InterruptedException {
        when(roomService.create(anyLong(), any())).thenReturn(getRoomResponse());
        RoomResponse response = roomService.create(anyLong(), any());
        automaticMatchingRepository.save(new AutomaticMatching(response.id(), response.recruitmentDeadline()));

        // 비동기 작업을 동기화 시키기 위한 클래스
        // 파라미터 인자에 비동기 작업의 개수를 입력해준다.
        // 입력된 개수의 비동기 작업이 종료될 때 까지 스레드는 대기 한다.
        CountDownLatch latch = new CountDownLatch(1);

        // Mokito의 doAnswer()는 특정 메서드가 호출될 때 수행할 작업을 정의한다.
        // automaticMatchingExecutor의 execute 메서드가 호출될 때, latch.countDown()을 호출하여 카운트를 감소시킨다.
        // latch가 현재 1로 설정되어 있기 때문에 카운트가 감소되어 0개가 되면 대기하고 있던 스레드가 계속 작업을 진행할 수 있게 된다.
        doAnswer(invocation -> {
            latch.countDown();
            return null;
        }).when(automaticMatchingExecutor).execute(any());

        // 2초후에 매칭이 되도록 설정된 automaticMatchingService의 matchOnRecruitmentDeadline 메서드를 호출한다.
        automaticMatchingService.matchOnRecruitmentDeadline(response);

        // latch의 카운트가 0이될 때까지 대기할 시간을 정의한다.
        // CountDownLatch의 카운트가 2초 내에 0이 되었을 때 await() 메서드가 즉시 반환되고 true를 반환합니다.
        assertThat(latch.await(2, TimeUnit.SECONDS)).isTrue();

        // automaticMatchingExecutor의 execute() 메서드가 호출되었는지 검증한다.
        verify(automaticMatchingExecutor).execute(any());
    }

    private RoomResponse getRoomResponse() {
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
                LocalDateTime.now().plusSeconds(2),
                LocalDateTime.now().plusDays(2),
                true,
                false);
    }
}
