package corea.scheduler.service;

import config.ServiceTest;
import config.TestAsyncConfig;
import corea.fixture.MemberFixture;
import corea.fixture.RoomFixture;
import corea.matchresult.domain.MatchResult;
import corea.matchresult.domain.ReviewStatus;
import corea.matchresult.repository.MatchResultRepository;
import corea.member.domain.Member;
import corea.member.repository.MemberRepository;
import corea.room.domain.Room;
import corea.room.dto.RoomCreateRequest;
import corea.room.repository.RoomRepository;
import corea.scheduler.domain.AutomaticUpdate;
import corea.scheduler.repository.AutomaticUpdateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ServiceTest
@Import(TestAsyncConfig.class)
class AutomaticUpdateExecutorTest {

    @Autowired
    private AutomaticUpdateExecutor automaticUpdateExecutor;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AutomaticUpdateRepository automaticUpdateRepository;

    @MockBean
    private MatchResultRepository matchResultRepository;

    private Room room;

    @BeforeEach
    void setUp() {
        Member member = memberRepository.save(MemberFixture.MEMBER_PORORO());
        RoomCreateRequest request = RoomFixture.ROOM_CREATE_REQUEST();

        room = roomRepository.save(request.toEntity(member));
    }

    @Test
    @DisplayName("동시에 10개의 자동 업데이트를 실행해도 PESSIMISTIC_WRITE 락을 통해 동시성을 제어할 수 있다.")
    void startMatchingWithLock() throws InterruptedException {
        AutomaticUpdate automaticUpdate = automaticUpdateRepository.save(new AutomaticUpdate(room.getId(), LocalDateTime.now().plusDays(1)));

        int threadCount = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        AtomicInteger successCount = new AtomicInteger(0);

        when(matchResultRepository.findAllByRoomIdAndReviewStatus(anyLong(), any(ReviewStatus.class))).thenAnswer(ignore -> {
            successCount.incrementAndGet();
            return Collections.singletonList(new MatchResult(room.getId(), MemberFixture.MEMBER_PORORO(), MemberFixture.MEMBER_MOVIN(), ""));
        });

        for (int i = 0; i < threadCount; i++) {
            executorService.execute(() -> {
                try {
                    automaticUpdateExecutor.execute(automaticUpdate.getRoomId());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        assertThat(successCount.get()).isEqualTo(1);
    }
}
