package corea.scheduler.service;

import corea.room.domain.Room;
import corea.scheduler.domain.AutomaticUpdate;
import corea.scheduler.repository.AutomaticUpdateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Transactional
public class AutomaticUpdateWriter {

    private final AutomaticUpdateRepository automaticUpdateRepository;

    public AutomaticUpdate updateTime(AutomaticUpdate automaticUpdate, LocalDateTime reviewDeadline) {
        AutomaticUpdate updateEntity = new AutomaticUpdate(
                automaticUpdate.getId(),
                automaticUpdate.getRoomId(),
                reviewDeadline,
                automaticUpdate.getStatus()
        );
        return automaticUpdateRepository.save(updateEntity);
    }

    public AutomaticUpdate create(Room room) {
        AutomaticUpdate createEntity = new AutomaticUpdate(
                room.getId(),
                room.getReviewDeadline()
        );
        return automaticUpdateRepository.save(createEntity);
    }

    public void delete(AutomaticUpdate automaticUpdate) {
        automaticUpdateRepository.delete(automaticUpdate);
    }
}
