package corea.room.domain;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
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

    public List<Room> findAll(Specification<Room> spec) {
        return roomRepository.findAll(spec, Sort.by("recruitmentDeadline").descending());
    }
}
