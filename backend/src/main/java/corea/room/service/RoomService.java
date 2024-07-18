package corea.room.service;

import corea.room.domain.Room;
import corea.room.dto.RoomCreateRequest;
import corea.room.dto.RoomResponse;
import corea.room.dto.RoomResponses;
import corea.member.repository.MemberRepository;
import corea.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomService {

    private final RoomRepository roomRepository;
    private final MemberRepository memberRepository;

    public RoomResponse create(final RoomCreateRequest request) {
        final Room room = roomRepository.save(request.toEntity());
        return toRoomResponse(room);
    }

    public RoomResponse findOne(final long id) {
        final Room room = getRoom(id);
        return toRoomResponse(room);
    }

    public RoomResponses findAll() {
        final List<Room> rooms = roomRepository.findAll();

        return rooms.stream()
                .map(this::toRoomResponse)
                .collect(collectingAndThen(toList(), RoomResponses::new));
    }

    private RoomResponse toRoomResponse(final Room room) {
        return RoomResponse.of(room);
    }

    private Room getRoom(final long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("해당 Id의 방이 없습니다. 입력된 Id=%d", roomId)));
    }
}
