package corea.scheduler.service;

import corea.scheduler.domain.ScheduleStatus;
import corea.scheduler.repository.AutomaticUpdateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AutomaticUpdateExecutor {

    private final UpdateExecutor updateExecutor;
    private final AutomaticUpdateRepository automaticUpdateRepository;

    @Transactional
    public void execute(long roomId) {
        automaticUpdateRepository.findByRoomIdAndStatusForUpdate(roomId, ScheduleStatus.PENDING)
                .ifPresent(automaticUpdate -> {
                    updateExecutor.update(roomId);
                    automaticUpdate.updateStatusToDone();
                });
    }
}
