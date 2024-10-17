package corea.room.service;

import corea.member.domain.MemberRole;
import corea.participation.domain.Participation;
import corea.participation.domain.ParticipationStatus;
import corea.participation.repository.ParticipationRepository;
import corea.room.domain.Room;
import corea.room.domain.RoomClassification;
import corea.room.domain.RoomStatus;
import corea.room.dto.RoomResponse;
import corea.room.dto.RoomResponses;
import corea.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomInquiryService {

    private static final int PAGE_DISPLAY_SIZE = 8;

    private final RoomRepository roomRepository;
    private final ParticipationRepository participationRepository;

    public RoomResponses findRoomsWithRoomStatus(long memberId, int pageNumber, String expression, RoomStatus roomStatus) {
        Page<Room> roomsWithPage = getPaginatedRooms(pageNumber, expression, roomStatus);
        List<RoomResponse> roomResponses = getRoomResponses(roomsWithPage.getContent(), memberId);

        return RoomResponses.of(roomResponses, roomsWithPage.isLast(), pageNumber);
    }

    private Page<Room> getPaginatedRooms(int pageNumber, String expression, RoomStatus status) {
        RoomClassification classification = RoomClassification.from(expression);
        PageRequest pageRequest = PageRequest.of(pageNumber, PAGE_DISPLAY_SIZE);

        if (classification.isAll()) {
            return roomRepository.findAllByStatusOrderByRecruitmentDeadline(status, pageRequest);
        }
        return roomRepository.findAllByClassificationAndStatusOrderByRecruitmentDeadline(classification, status, pageRequest);
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
}
