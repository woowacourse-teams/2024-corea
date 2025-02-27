package corea.room.domain;

import corea.global.annotation.Writer;
import corea.room.repository.RoomMatchInfoRepository;
import lombok.RequiredArgsConstructor;

@Writer
@RequiredArgsConstructor
public class RoomMatchInfoWriter {

    private final RoomMatchInfoRepository roomMatchInfoRepository;

    public RoomMatchInfo create(Room room, boolean isPublic) {
        return roomMatchInfoRepository.save(new RoomMatchInfo(room.getId(), isPublic));
    }
}
