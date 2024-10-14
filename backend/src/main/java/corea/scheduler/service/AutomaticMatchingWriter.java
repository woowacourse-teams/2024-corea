package corea.scheduler.service;

import corea.room.domain.Room;
import corea.scheduler.domain.AutomaticMatching;
import corea.scheduler.repository.AutomaticMatchingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Transactional
public class AutomaticMatchingWriter {

    private final AutomaticMatchingRepository automaticMatchingRepository;

    public AutomaticMatching updateTime(AutomaticMatching automaticMatching, LocalDateTime matchingStartTime) {
        AutomaticMatching updateEntity = new AutomaticMatching(
                automaticMatching.getId(),
                automaticMatching.getRoomId(),
                matchingStartTime,
                automaticMatching.getStatus()
        );
        return automaticMatchingRepository.save(updateEntity);
    }

    public AutomaticMatching create(Room room) {
        AutomaticMatching entity = new AutomaticMatching(room.getId(),room.getRecruitmentDeadline());
        return automaticMatchingRepository.save(entity);
    }

    public void delete(AutomaticMatching automaticMatching) {
        automaticMatchingRepository.delete(automaticMatching);
    }
}
