package corea.scheduler.service;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.room.dto.RoomResponse;
import corea.scheduler.domain.AutomaticMatching;
import corea.scheduler.domain.ScheduleStatus;
import corea.scheduler.repository.AutomaticMatchingRepository;
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
public class AutomaticMatchingService {

    private static final ZoneId ZONE_ID = ZoneId.of("Asia/Seoul");

    private final TaskScheduler taskScheduler;
    private final AutomaticMatchingExecutor automaticMatchingExecutor;
    private final AutomaticMatchingRepository automaticMatchingRepository;

    private final Map<Long, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();

    @EventListener(ApplicationReadyEvent.class)
    public void schedulePendingAutomaticMatching() {
        List<AutomaticMatching> matchings = automaticMatchingRepository.findAllByStatus(ScheduleStatus.PENDING);

        log.info("{}개의 방에 대해 자동 매칭 재예약 시작", matchings.size());

        matchings.forEach(matching -> scheduleMatching(matching, matching.getRoomId(), matching.getMatchingStartTime()));

        log.info("{}개의 방에 대해 자동 매칭 재예약 완료", matchings.size());
    }

    public void matchOnRecruitmentDeadline(RoomResponse response) {
        long roomId = response.id();
        AutomaticMatching automaticMatching = getAutomaticMatchingByRoomId(roomId);

        scheduleMatching(automaticMatching, roomId, response.recruitmentDeadline());
    }

    private AutomaticMatching getAutomaticMatchingByRoomId(long roomId) {
        return automaticMatchingRepository.findByRoomId(roomId)
                .orElseThrow(() -> new CoreaException(ExceptionType.AUTOMATIC_MATCHING_NOT_FOUND));
    }

    private void scheduleMatching(AutomaticMatching automaticMatching, long roomId, LocalDateTime matchingStartTime) {
        ScheduledFuture<?> schedule = taskScheduler.schedule(
                () -> automaticMatchingExecutor.execute(automaticMatching),
                toInstant(matchingStartTime)
        );

        log.info("{}번 방 자동 매칭 예약 - 예약 시간: {}", roomId, matchingStartTime);
        scheduledTasks.put(roomId, schedule);
    }

    private Instant toInstant(LocalDateTime recruitmentDeadline) {
        return recruitmentDeadline.atZone(ZONE_ID).toInstant();
    }

    public void cancel(long roomId) {
        if (scheduledTasks.containsKey(roomId)) {
            cancelScheduledMatching(roomId);
            return;
        }
        throw new CoreaException(
                ExceptionType.AUTOMATIC_MATCHING_NOT_FOUND,
                String.format("해당 방 아이디에 예약된 자동 매칭이 존재하지 않아 예약을 취소할 수 없습니다. 요청 방 ID=%d", roomId)
        );
    }

    private void cancelScheduledMatching(long roomId) {
        ScheduledFuture<?> scheduledMatching = scheduledTasks.remove(roomId);
        scheduledMatching.cancel(true);

        log.info("{}번 방 기존 자동 매칭 예약 취소", roomId);
    }
}
