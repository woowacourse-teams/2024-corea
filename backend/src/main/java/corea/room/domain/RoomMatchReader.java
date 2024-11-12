package corea.room.domain;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.global.annotation.Reader;
import corea.room.repository.RoomMatchInfoRepository;
import lombok.RequiredArgsConstructor;

@Reader
@RequiredArgsConstructor
public class RoomMatchReader {

    private final RoomMatchInfoRepository roomMatchInfoRepository;

    public boolean isPublicRoom(Room room) {
        return roomMatchInfoRepository.findByRoomId(room.getId())
                .map(RoomMatchInfo::isPublic)
                .orElse(true);
    }
}
