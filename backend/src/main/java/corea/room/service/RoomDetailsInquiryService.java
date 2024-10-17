package corea.room.service;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.matchresult.repository.FailedMatchingRepository;
import corea.member.domain.MemberRole;
import corea.participation.domain.Participation;
import corea.participation.domain.ParticipationStatus;
import corea.participation.repository.ParticipationRepository;
import corea.room.domain.Room;
import corea.room.dto.RoomResponse;
import corea.room.dto.RoomResponses;
import corea.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomDetailsInquiryService {

    private final RoomRepository roomRepository;
    private final ParticipationRepository participationRepository;
    private final FailedMatchingRepository failedMatchingRepository;

    public RoomResponse findOne(long roomId, long memberId) {
        Room room = getRoom(roomId);

        return participationRepository.findByRoomIdAndMemberId(roomId, memberId)
                .map(participation -> createRoomResponseWithParticipation(room, participation))
                .orElseGet(() -> RoomResponse.of(room, MemberRole.NONE, ParticipationStatus.NOT_PARTICIPATED));
    }

    private RoomResponse createRoomResponseWithParticipation(Room room, Participation participation) {
        return failedMatchingRepository.findByRoomId(room.getId())
                .map(failedMatching -> RoomResponse.of(room, participation, failedMatching))
                .orElseGet(() -> RoomResponse.of(room, participation));
    }

    public RoomResponses findParticipatedRooms(long memberId, boolean includeClosed) {
        List<Room> rooms = findFilteredParticipatedRooms(memberId, includeClosed);
        List<RoomResponse> roomResponses = getRoomResponses(rooms, memberId);

        return RoomResponses.of(roomResponses, true, 0);
    }

    private List<Room> findFilteredParticipatedRooms(long memberId, boolean includeClosed) {
        List<Room> rooms = findAllParticipatedRooms(memberId);

        if (includeClosed) {
            return rooms;
        }
        return filterOutClosedRooms(rooms);
    }

    private List<Room> findAllParticipatedRooms(long memberId) {
        return participationRepository.findAllByMemberId(memberId)
                .stream()
                .map(Participation::getRoom)
                .toList();
    }

    private List<Room> filterOutClosedRooms(List<Room> rooms) {
        return rooms.stream()
                .filter(Room::isNotClosed)
                .toList();
    }

    private List<RoomResponse> getRoomResponses(List<Room> rooms, long memberId) {
        List<Participation> participations = participationRepository.findAllByMemberId(memberId);

        return rooms.stream()
                .map(room -> getRoomResponse(participations, room))
                .toList();
    }

    private RoomResponse getRoomResponse(List<Participation> participations, Room room) {
        return participations.stream()
                .filter(participation -> participation.isParticipatedRoom(room))
                .findFirst()
                .map(participation -> RoomResponse.of(room, participation))
                .orElseGet(() -> RoomResponse.of(room, MemberRole.NONE, ParticipationStatus.NOT_PARTICIPATED));
    }

    private Room getRoom(long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new CoreaException(ExceptionType.ROOM_NOT_FOUND, String.format("해당 Id의 방이 없습니다. 입력된 Id=%d", roomId)));
    }
}
