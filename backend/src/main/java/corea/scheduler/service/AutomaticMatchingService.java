package corea.scheduler.service;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.room.dto.RoomResponse;
import corea.scheduler.domain.AutomaticMatching;
import corea.scheduler.domain.MatchingStatus;
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
                toInstance(matchingStartTime)
        );

        log.info("{}번 방 자동 매칭 예약 - 예약 시간: {}", roomId, matchingStartTime);
        scheduledTasks.put(roomId, schedule);
    }

    private Instant toInstance(LocalDateTime recruitmentDeadline) {
        return recruitmentDeadline.atZone(ZONE_ID).toInstant();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void schedulePendingAutomaticMatching() {
        List<AutomaticMatching> matchings = automaticMatchingRepository.findAllByStatus(MatchingStatus.PENDING);

        log.info("{}개의 방에 대해 자동 매칭 재예약 시작", matchings.size());

        matchings.forEach(matching -> scheduleMatching(matching, matching.getRoomId(), matching.getMatchingStartTime()));
    }
}
