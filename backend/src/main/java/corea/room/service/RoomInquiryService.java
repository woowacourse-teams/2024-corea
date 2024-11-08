package corea.room.service;

import corea.member.domain.MemberRole;
import corea.participation.domain.ParticipationStatus;
import corea.participation.repository.ParticipationRepository;
import corea.room.domain.Room;
import corea.room.domain.RoomClassification;
import corea.room.domain.RoomReader;
import corea.room.domain.RoomStatus;
import corea.room.dto.RoomResponse;
import corea.room.dto.RoomResponses;
import corea.room.dto.RoomSearchResponses;
import corea.room.repository.RoomRepository;
import corea.room.repository.RoomSpec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomInquiryService {

    private static final int PAGE_DISPLAY_SIZE = 8;

    private final RoomReader roomReader;
    private final RoomRepository roomRepository;
    private final ParticipationRepository participationRepository;

    public RoomSearchResponses search(long memberId, RoomStatus status, RoomClassification classification, String keywordTitle) {
        Specification<Room> spec = getSearchSpecification(status, classification, keywordTitle);
        List<Room> rooms = roomReader.findAll(spec);

        List<RoomResponse> roomResponses = getRoomResponses(rooms, memberId);
        return RoomSearchResponses.of(roomResponses);
    }

    private Specification<Room> getSearchSpecification(RoomStatus status, RoomClassification classification, String keywordTitle) {
        Specification<Room> spec = Specification.where(RoomSpec.equalStatus(status));
        if (classification.isNotAll()) {
            spec = spec.and(RoomSpec.equalClassification(classification));
        }
        spec = spec.and(RoomSpec.likeTitle(keywordTitle));
        return spec;
    }

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
        return rooms.stream()
                .map(room -> getRoomResponse(room, memberId))
                .toList();
    }

    private RoomResponse getRoomResponse(Room room, long memberId) {
        return participationRepository.findByRoomIdAndMemberId(room.getId(), memberId)
                .map(participation -> RoomResponse.of(room, participation))
                .orElseGet(() -> RoomResponse.of(room, MemberRole.NONE, ParticipationStatus.NOT_PARTICIPATED));
    }
}
