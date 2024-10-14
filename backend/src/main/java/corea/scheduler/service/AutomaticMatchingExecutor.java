package corea.scheduler.service;

import corea.scheduler.domain.ScheduleStatus;
import corea.scheduler.repository.AutomaticMatchingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AutomaticMatchingExecutor {

    private final MatchingExecutor matchingExecutor;
    private final AutomaticMatchingRepository automaticMatchingRepository;

    @Transactional
    public void execute(long roomId) {
        automaticMatchingRepository.findByRoomIdAndStatusForUpdate(roomId, ScheduleStatus.PENDING)
                .ifPresent(automaticMatching -> {
                    matchingExecutor.match(roomId);
                    automaticMatching.updateStatusToDone();
                });
    }
}
