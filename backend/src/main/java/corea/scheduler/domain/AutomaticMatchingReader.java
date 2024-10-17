package corea.scheduler.domain;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.room.domain.Room;
import corea.scheduler.repository.AutomaticMatchingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AutomaticMatchingReader {

    private final AutomaticMatchingRepository automaticMatchingRepository;

    public AutomaticMatching findWithRoom(Room room) {
        return automaticMatchingRepository.findByRoomId(room.getId())
                .orElseThrow(() -> new CoreaException(ExceptionType.AUTOMATIC_MATCHING_NOT_FOUND));
    }

    public List<AutomaticMatching> findAllByStatus(ScheduleStatus status) {
        return automaticMatchingRepository.findAllByStatus(status);
    }
}
