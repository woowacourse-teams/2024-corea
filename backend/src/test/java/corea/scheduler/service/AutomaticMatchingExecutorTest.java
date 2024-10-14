package corea.scheduler.service;

import corea.scheduler.domain.AutomaticMatching;
import corea.scheduler.repository.AutomaticMatchingRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AutomaticMatchingExecutorTest extends MatchingTest {

    @Autowired
    private AutomaticMatchingExecutor automaticMatchingExecutor;

    @Autowired
    private AutomaticMatchingRepository automaticMatchingRepository;

    @Test
    @DisplayName("동시에 10개의 자동 매칭을 실행해도 PESSIMISTIC_WRITE 락을 통해 동시성을 제어할 수 있다.")
    void startMatchingWithLock() throws InterruptedException {
        AutomaticMatching automaticMatching = automaticMatchingRepository.save(new AutomaticMatching(room.getId(), LocalDateTime.now().plusDays(1)));

        int threadCount = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        AtomicInteger successCount = new AtomicInteger(0);

        when(pullRequestProvider.getUntilDeadline(any(), any())).thenAnswer(ignore -> {
            successCount.incrementAndGet();
            return getPullRequestInfo(pororo, ash, joysun, movin, ten, cho);
        });

        for (int i = 0; i < threadCount; i++) {
            executorService.execute(() -> {
                try {
                    automaticMatchingExecutor.execute(automaticMatching.getRoomId());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        assertThat(successCount.get()).isEqualTo(1);
    }
}
