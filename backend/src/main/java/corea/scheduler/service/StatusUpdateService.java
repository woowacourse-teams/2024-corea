package corea.scheduler.service;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.room.dto.RoomResponse;
import corea.scheduler.domain.AutomaticUpdate;
import corea.scheduler.repository.AutomaticUpdateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatusUpdateService {

    private static final ZoneId ZONE_ID = ZoneId.of("Asia/Seoul");

    private final TaskScheduler taskScheduler;
    private final StatusUpdateExecutor statusUpdateExecutor;
    private final AutomaticUpdateRepository automaticUpdateRepository;

    private final Map<Long, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();

    public void updateAtReviewDeadline(RoomResponse response) {
        long roomId = response.id();
        AutomaticUpdate automaticUpdate = getAutomaticUpdateByRoomId(roomId);

        scheduleMatching(automaticUpdate, roomId, response.reviewDeadline());
    }

    private AutomaticUpdate getAutomaticUpdateByRoomId(long roomId) {
        return automaticUpdateRepository.findByRoomId(roomId)
                .orElseThrow(() -> new CoreaException(ExceptionType.AUTOMATIC_UPDATE_NOT_FOUND));
    }

    private void scheduleMatching(AutomaticUpdate automaticUpdate, long roomId, LocalDateTime updateStartTime) {
        ScheduledFuture<?> schedule = taskScheduler.schedule(
                () -> statusUpdateExecutor.execute(automaticUpdate),
                toInstant(updateStartTime)
        );

        log.info("{}번 방 자동 상태 업데이트 예약 - 예약 시간: {}", roomId, updateStartTime);
        scheduledTasks.put(roomId, schedule);
    }

    private Instant toInstant(LocalDateTime updateStartTime) {
        return updateStartTime.atZone(ZONE_ID).toInstant();
    }
}
