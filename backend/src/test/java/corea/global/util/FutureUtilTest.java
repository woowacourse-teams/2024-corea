package corea.global.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertTrue;

class FutureUtilTest {

    @Test
    @DisplayName("지정하지 않으면 ForkedJoinPool 에서 동작한다.")
    void default_is_work_with_forked_join_pool() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = FutureUtil.supplyAsync(() -> Thread.currentThread().getName());
        String threadName = future.get();
        assertTrue(threadName.startsWith("ForkJoinPool"));
    }

    @Test
    @DisplayName("지정하면 지정한 Executors 가 제공한 스레드에서 동작한다.")
    void work_with_specific_executor() throws ExecutionException, InterruptedException {
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("testExecutor");
        executor.initialize();

        CompletableFuture<String> future = FutureUtil.supplyAsync(() -> Thread.currentThread().getName(), executor);
        String threadName = future.get();
        assertTrue(threadName.startsWith("testExecutor"));
    }
}
