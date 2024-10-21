package corea.room.domain;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoomReader {

    private final RoomRepository roomRepository;

    public boolean isNotStatus(long roomId, RoomStatus status) {
        Room room = find(roomId);
        return !room.isStatus(status);
    }

    public Room find(long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new CoreaException(ExceptionType.ROOM_NOT_FOUND, String.format("해당 Id의 방이 없습니다. 입력된 Id=%d", roomId)));
    }
}
