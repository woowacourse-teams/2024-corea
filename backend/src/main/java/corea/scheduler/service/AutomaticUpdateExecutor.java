package corea.scheduler.service;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.room.domain.Room;
import corea.room.repository.RoomRepository;
import corea.scheduler.domain.AutomaticUpdate;
import corea.scheduler.repository.AutomaticUpdateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AutomaticUpdateExecutor {

    private final RoomRepository roomRepository;
    private final AutomaticUpdateRepository automaticUpdateRepository;

    @Async
    @Transactional
    public void execute(long roomId) {
        Room room = getRoom(roomId);
        room.updateStatusToClose();

        AutomaticUpdate automaticUpdate = getAutomaticUpdateByRoomId(roomId);
        automaticUpdate.updateStatusToDone();
    }

    private Room getRoom(long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new CoreaException(ExceptionType.ROOM_NOT_FOUND));
    }

    private AutomaticUpdate getAutomaticUpdateByRoomId(long roomId) {
        return automaticUpdateRepository.findByRoomId(roomId)
                .orElseThrow(() -> new CoreaException(ExceptionType.AUTOMATIC_UPDATE_NOT_FOUND));
    }
}
