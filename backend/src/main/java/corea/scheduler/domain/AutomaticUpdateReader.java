package corea.scheduler.domain;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.room.domain.Room;
import corea.scheduler.repository.AutomaticUpdateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AutomaticUpdateReader {

    private final AutomaticUpdateRepository automaticUpdateRepository;

    public AutomaticUpdate findWithRoom(Room room) {
        return automaticUpdateRepository.findByRoomId(room.getId())
                .orElseThrow(() -> new CoreaException(ExceptionType.AUTOMATIC_UPDATE_NOT_FOUND));
    }

    public List<AutomaticUpdate> findAllByStatus(ScheduleStatus status) {
        return automaticUpdateRepository.findAllByStatus(status);
    }
}
