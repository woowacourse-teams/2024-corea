package corea.scheduler.service;

import corea.room.domain.Room;
import corea.scheduler.domain.AutomaticUpdate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@Transactional(readOnly = true)
public class AutomaticUpdateScheduler {

    private static final ZoneId ZONE_ID = ZoneId.of("Asia/Seoul");

    private final TaskScheduler taskScheduler;
    private final AutomaticUpdateExecutor automaticUpdateExecutor;
    private final Map<Long, ScheduledFuture<?>> scheduledTasks;

    @Autowired
    public AutomaticUpdateScheduler(TaskScheduler taskScheduler, AutomaticUpdateExecutor automaticUpdateExecutor) {
        this(taskScheduler, automaticUpdateExecutor, new ConcurrentHashMap<>());
    }

    public AutomaticUpdateScheduler(TaskScheduler taskScheduler, AutomaticUpdateExecutor automaticUpdateExecutor, Map<Long, ScheduledFuture<?>> scheduledTasks) {
        this.taskScheduler = taskScheduler;
        this.automaticUpdateExecutor = automaticUpdateExecutor;
        this.scheduledTasks = scheduledTasks;
    }

    public void updateAtReviewDeadline(Room room) {
        scheduleUpdate(room.getId(), room.getReviewDeadline());
    }

    public void updateAtReviewDeadline(AutomaticUpdate automaticUpdate) {
        scheduleUpdate(automaticUpdate.getRoomId(), automaticUpdate.getUpdateStartTime());
    }

    public void modifyTask(Room room) {
        cancel(room.getId());
        scheduleUpdate(room.getId(), room.getReviewDeadline());
    }

    private void scheduleUpdate(long roomId, LocalDateTime updateStartTime) {
        ScheduledFuture<?> schedule = taskScheduler.schedule(
                () -> automaticUpdateExecutor.execute(roomId),
                toInstant(updateStartTime)
        );
        log.info("{}번 방 자동 상태 업데이트 예약 - 예약 시간: {}", roomId, updateStartTime);
        scheduledTasks.put(roomId, schedule);
    }

    private Instant toInstant(LocalDateTime updateStartTime) {
        return updateStartTime.atZone(ZONE_ID)
                .toInstant();
    }

    public void cancel(long roomId) {
        if (scheduledTasks.containsKey(roomId)) {
            cancelScheduledUpdate(roomId);
            return;
        }
        log.info("해당 방 아이디에 예약된 자동 상태 업데이트가 존재하지 않아 예약을 취소할 수 없습니다. 요청 방 ID={}", roomId);
    }

    private void cancelScheduledUpdate(long roomId) {
        ScheduledFuture<?> scheduledUpdate = scheduledTasks.remove(roomId);
        scheduledUpdate.cancel(true);
        log.info("{}번 방 기존 자동 상태 업데이트 예약 취소", roomId);
    }
}
