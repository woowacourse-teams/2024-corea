package corea.room.service;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.matching.domain.Participation;
import corea.matching.repository.ParticipationRepository;
import corea.member.domain.Member;
import corea.room.domain.Room;
import corea.room.dto.RoomCreateRequest;
import corea.room.dto.RoomResponse;
import corea.room.dto.RoomResponses;
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
    private final ParticipationRepository participationRepository;

    public RoomResponse create(RoomCreateRequest request) {
        Room room = roomRepository.save(request.toEntity());
        return RoomResponse.from(room);
    }

    public RoomResponse findOne(long id) {
        Room room = getRoom(id);
        return RoomResponse.from(room);
    }

    public RoomResponses findParticipatedRooms(long memberId) {
        List<Participation> participations = participationRepository.findAllByMemberId(memberId);

        return participations.stream()
                .map(Participation::getRoomId)
                .map(this::getRoom)
                .collect(collectingAndThen(toList(), RoomResponses::from));
    }

    public RoomResponses findAll() {
        final List<Room> rooms = roomRepository.findAll();
        return RoomResponses.from(rooms);
    }

    private Room getRoom(long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new CoreaException(ExceptionType.ROOM_NOT_FOUND, String.format("해당 Id의 방이 없습니다. 입력된 Id=%d", roomId)));
    }
}
