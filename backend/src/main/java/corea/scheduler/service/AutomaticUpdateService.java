package corea.scheduler.service;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.room.dto.RoomResponse;
import corea.scheduler.domain.AutomaticUpdate;
import corea.scheduler.domain.ScheduleStatus;
import corea.scheduler.repository.AutomaticUpdateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AutomaticUpdateService {

    private static final ZoneId ZONE_ID = ZoneId.of("Asia/Seoul");

    private final TaskScheduler taskScheduler;
    private final AutomaticUpdateExecutor automaticUpdateExecutor;
    private final AutomaticUpdateRepository automaticUpdateRepository;

    private final Map<Long, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();

    @EventListener(ApplicationReadyEvent.class)
    public void schedulePendingAutomaticUpdate() {
        List<AutomaticUpdate> updates = automaticUpdateRepository.findAllByStatus(ScheduleStatus.PENDING);

        log.info("{}개의 방에 대해 자동 상태 업데이트 재예약 시작", updates.size());

        updates.forEach(update -> scheduleUpdate(update, update.getRoomId(), update.getUpdateStartTime()));

        log.info("{}개의 방에 대해 자동 상태 업데이트 재예약 완료", updates.size());
    }

    public void updateAtReviewDeadline(RoomResponse response) {
        long roomId = response.id();
        AutomaticUpdate automaticUpdate = getAutomaticUpdateByRoomId(roomId);

        scheduleUpdate(automaticUpdate, roomId, response.reviewDeadline());
    }

    private AutomaticUpdate getAutomaticUpdateByRoomId(long roomId) {
        return automaticUpdateRepository.findByRoomId(roomId)
                .orElseThrow(() -> new CoreaException(ExceptionType.AUTOMATIC_UPDATE_NOT_FOUND));
    }

    private void scheduleUpdate(AutomaticUpdate automaticUpdate, long roomId, LocalDateTime updateStartTime) {
        ScheduledFuture<?> schedule = taskScheduler.schedule(
                () -> automaticUpdateExecutor.execute(automaticUpdate),
                toInstant(updateStartTime)
        );

        log.info("{}번 방 자동 상태 업데이트 예약 - 예약 시간: {}", roomId, updateStartTime);
        scheduledTasks.put(roomId, schedule);
    }

    private Instant toInstant(LocalDateTime updateStartTime) {
        return updateStartTime.atZone(ZONE_ID).toInstant();
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
