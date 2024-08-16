package corea.room.service;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.member.domain.Member;
import corea.member.repository.MemberRepository;
import corea.participation.domain.Participation;
import corea.participation.repository.ParticipationRepository;
import corea.room.domain.Room;
import corea.room.domain.RoomClassification;
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

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomService {

    private static final int PLUS_HOURS_TO_MINIMUM_RECRUITMENT_DEADLINE = 1;
    private static final int PLUS_DAYS_TO_MINIMUM_REVIEW_DEADLINE = 1;
    private static final int PAGE_SIZE = 8;

    private final RoomRepository roomRepository;
    private final MemberRepository memberRepository;
    private final ParticipationRepository participationRepository;

    @Transactional
    public RoomResponse create(long memberId, RoomCreateRequest request) {
        validateDeadLine(request.recruitmentDeadline(), request.reviewDeadline());

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CoreaException(ExceptionType.MEMBER_NOT_FOUND, String.format("%d에 해당하는 멤버가 없습니다.", memberId)));
        Room room = roomRepository.save(request.toEntity(member));
        participationRepository.save(new Participation(room.getId(), memberId));
        return RoomResponse.of(room, true);
    }

    //TODO: 검증 로직 추후 변경할게용~
    private void validateDeadLine(LocalDateTime recruitmentDeadline, LocalDateTime reviewDeadline) {
        LocalDateTime minimumRecruitmentDeadline = reviewDeadline.plusHours(PLUS_HOURS_TO_MINIMUM_RECRUITMENT_DEADLINE);
        if (reviewDeadline.isBefore(minimumRecruitmentDeadline)) {
            throw new CoreaException(ExceptionType.INVALID_RECRUITMENT_DEADLINE,
                    String.format("모집 마감 시간은 현재 시간보다 %d시간 이후여야 합니다.", PLUS_HOURS_TO_MINIMUM_RECRUITMENT_DEADLINE));
        }

        LocalDateTime minimumReviewDeadLine = recruitmentDeadline.plusDays(PLUS_DAYS_TO_MINIMUM_REVIEW_DEADLINE);
        if (reviewDeadline.isBefore(minimumReviewDeadLine)) {
            throw new CoreaException(ExceptionType.INVALID_REVIEW_DEADLINE,
                    String.format("리뷰 마감 시간은 모집 마감 시간보다 %d일 이후여야 합니다.", PLUS_DAYS_TO_MINIMUM_REVIEW_DEADLINE));
        }
    }

    public RoomResponse findOne(long roomId, long memberId) {
        Room room = findRoomInfo(roomId);
        boolean isParticipated = participationRepository.existsByRoomIdAndMemberId(roomId, memberId);

        return RoomResponse.of(room, isParticipated);
    }

    public RoomResponses findParticipatedRooms(long memberId) {
        List<Participation> participations = participationRepository.findAllByMemberId(memberId);

        return participations.stream()
                .map(Participation::getRoomId)
                .map(this::findRoomInfo)
                .collect(collectingAndThen(toList(), rooms -> RoomResponses.of(rooms, true, true)));
    }

    public RoomResponses findOpenedRooms(long memberId, String expression, int pageNumber) {
        RoomClassification classification = RoomClassification.from(expression);
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
        RoomClassification classification = RoomClassification.from(expression);
        RoomStatus status = RoomStatus.CLOSED;
        PageRequest pageRequest = PageRequest.of(pageNumber, PAGE_SIZE);

        if (classification.isAll()) {
            Page<Room> roomsWithPage = roomRepository.findAllByStatus(status, pageRequest);
            return RoomResponses.from(roomsWithPage);
        }
        Page<Room> roomsWithPage = roomRepository.findAllByClassificationAndStatus(classification, status, pageRequest);
        return RoomResponses.from(roomsWithPage);
    }

    public RoomResponse getRoomById(long roomId) {
        return RoomResponse.of(findRoomInfo(roomId));
    }

    private Room findRoomInfo(long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new CoreaException(ExceptionType.ROOM_NOT_FOUND, String.format("해당 Id의 방이 없습니다. 입력된 Id=%d", roomId)));
    }
}
