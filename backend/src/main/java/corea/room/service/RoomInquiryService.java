package corea.room.service;

import corea.member.domain.MemberRole;
import corea.participation.domain.Participation;
import corea.participation.domain.ParticipationStatus;
import corea.participation.repository.ParticipationRepository;
import corea.room.domain.Room;
import corea.room.domain.RoomClassification;
import corea.room.domain.RoomStatus;
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
        RoomClassification classification = RoomClassification.from(expression);
        return getRoomResponses(memberId, pageNumber, classification, roomStatus);
    }

    private RoomResponses getRoomResponses(long memberId, int pageNumber, RoomClassification classification, RoomStatus status) {
        PageRequest pageRequest = PageRequest.of(pageNumber, PAGE_DISPLAY_SIZE);
        Page<Room> roomsWithPage = getPaginatedRooms(memberId, classification, status, pageRequest);

        return RoomResponses.of(roomsWithPage, MemberRole.NONE, ParticipationStatus.NOT_PARTICIPATED, pageNumber);
    }

    private Page<Room> getPaginatedRooms(long memberId, RoomClassification classification, RoomStatus status, PageRequest pageRequest) {
        if (classification.isAll()) {
            return roomRepository.findAllByMemberAndStatus(memberId, status, pageRequest);
        }
        return roomRepository.findAllByMemberAndClassificationAndStatus(memberId, classification, status, pageRequest);
    }

    public RoomResponses findParticipatedRooms(long memberId, boolean includeClosed) {
        List<Room> rooms = findFilteredParticipatedRooms(memberId, includeClosed);
        return RoomResponses.of(rooms, MemberRole.NONE, ParticipationStatus.PARTICIPATED, true, 0);
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
