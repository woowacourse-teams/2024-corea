package corea.scheduler.service;

import corea.room.domain.Room;
import corea.scheduler.domain.AutomaticMatching;
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
public class AutomaticMatchingService {

    private static final ZoneId ZONE_ID = ZoneId.of("Asia/Seoul");

    private final TaskScheduler taskScheduler;
    private final AutomaticMatchingExecutor automaticMatchingExecutor;
    private Map<Long, ScheduledFuture<?>> scheduledTasks;

    @Autowired
    public AutomaticMatchingService(TaskScheduler taskScheduler, AutomaticMatchingExecutor automaticMatchingExecutor) {
        this.taskScheduler = taskScheduler;
        this.automaticMatchingExecutor = automaticMatchingExecutor;
        this.scheduledTasks = new ConcurrentHashMap<>();
    }

    public AutomaticMatchingService(TaskScheduler taskScheduler, AutomaticMatchingExecutor automaticMatchingExecutor, Map<Long, ScheduledFuture<?>> scheduledTasks) {
        this.taskScheduler = taskScheduler;
        this.automaticMatchingExecutor = automaticMatchingExecutor;
        this.scheduledTasks = scheduledTasks;
    }

    public void modifyTask(Room room) {
        cancelScheduledMatching(room.getId());
        scheduleMatching(room.getId(), room.getRecruitmentDeadline());
    }

    public void matchOnRecruitmentDeadline(Room room) {
        scheduleMatching(room.getId(), room.getRecruitmentDeadline());
    }

    public void matchOnRecruitmentDeadline(AutomaticMatching automaticMatching) {
        scheduleMatching(automaticMatching.getRoomId(), automaticMatching.getMatchingStartTime());
    }

    private void scheduleMatching(long roomId, LocalDateTime matchingStartTime) {
        ScheduledFuture<?> schedule = taskScheduler.schedule(
                () -> automaticMatchingExecutor.execute(roomId),
                toInstant(matchingStartTime)
        );

        log.info("{}번 방 자동 매칭 예약 - 예약 시간: {}", roomId, matchingStartTime);
        scheduledTasks.put(roomId, schedule);
    }

    private Instant toInstant(LocalDateTime matchingStartTime) {
        return matchingStartTime.atZone(ZONE_ID)
                .toInstant();
    }

    public void cancel(long roomId) {
        if (scheduledTasks.containsKey(roomId)) {
            cancelScheduledMatching(roomId);
            return;
        }
        log.info("해당 방 아이디에 예약된 자동 매칭이 존재하지 않아 예약을 취소할 수 없습니다. 요청 방 ID={}", roomId);
    }

    private void cancelScheduledMatching(long roomId) {
        ScheduledFuture<?> scheduledMatching = scheduledTasks.remove(roomId);
        scheduledMatching.cancel(true);

        log.info("{}번 방 기존 자동 매칭 예약 취소", roomId);
    }
}
