package corea.room.service;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.participation.domain.Participation;
import corea.participation.repository.ParticipationRepository;
import corea.room.domain.Classification;
import corea.room.domain.Room;
import corea.room.domain.RoomStatus;
import corea.room.dto.RoomCreateRequest;
import corea.room.dto.RoomResponse;
import corea.room.dto.RoomResponses;
import corea.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomService {

    private static final int PAGE_SIZE = 8;

    private final RoomRepository roomRepository;
    private final ParticipationRepository participationRepository;

    public RoomResponse create(RoomCreateRequest request) {
        Room room = roomRepository.save(request.toEntity());
        return RoomResponse.of(room, true);
    }

    public RoomResponse findOne(long roomId, long memberId) {
        Room room = getRoom(roomId);
        boolean isParticipated = participationRepository.existsByRoomIdAndMemberId(roomId, memberId);

        return RoomResponse.of(room, isParticipated);
    }

    public RoomResponses findParticipatedRooms(long memberId) {
        List<Participation> participations = participationRepository.findAllByMemberId(memberId);

        return participations.stream()
                .map(Participation::getRoomId)
                .map(this::getRoom)
                .collect(collectingAndThen(toList(), rooms -> RoomResponses.of(rooms, true, true)));
    }

    public RoomResponses findOpenedRooms(long memberId, String expression, int pageNumber) {
        Classification classification = Classification.from(expression);
        RoomStatus status = RoomStatus.OPENED;
        PageRequest pageRequest = PageRequest.of(pageNumber, PAGE_SIZE);

        if (classification.isAll()) {
            Page<Room> roomsWithPage = roomRepository.findAllByMemberAndStatus(memberId, status, pageRequest);
            return RoomResponses.from(roomsWithPage);
        }
        Page<Room> roomsWithPage = roomRepository.findAllByMemberAndClassificationAndStatus(memberId, classification, status, pageRequest);
        return RoomResponses.from(roomsWithPage);
    }

    public RoomResponses findClosedRooms(String expression, int pageNumber) {
        Classification classification = Classification.from(expression);
        RoomStatus status = RoomStatus.CLOSED;
        PageRequest pageRequest = PageRequest.of(pageNumber, PAGE_SIZE);

        if (classification.isAll()) {
            Page<Room> roomsWithPage = roomRepository.findAllByStatus(status, pageRequest);
            return RoomResponses.from(roomsWithPage);
        }
        Page<Room> roomsWithPage = roomRepository.findAllByClassificationAndStatus(classification, status, pageRequest);
        return RoomResponses.from(roomsWithPage);
    }

    public Room getRoom(long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new CoreaException(ExceptionType.ROOM_NOT_FOUND, String.format("해당 Id의 방이 없습니다. 입력된 Id=%d", roomId)));
    }
}
