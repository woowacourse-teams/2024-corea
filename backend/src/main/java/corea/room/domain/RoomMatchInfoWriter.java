package corea.room.domain;

import corea.global.annotation.Writer;
import corea.room.repository.RoomMatchInfoRepository;
import lombok.RequiredArgsConstructor;

@Writer
@RequiredArgsConstructor
public class RoomMatchInfoWriter {

    private final RoomMatchInfoRepository roomMatchInfoRepository;

    public RoomMatchInfo create(Room room, boolean isPrivate) {
        return roomMatchInfoRepository.save(new RoomMatchInfo(room.getId(), isPrivate));
    }
}
