package corea.room.service;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.matching.domain.MatchResult;
import corea.matching.repository.MatchResultRepository;
import corea.member.domain.Member;
import corea.member.repository.MemberRepository;
import corea.participation.domain.Participation;
import corea.participation.repository.ParticipationRepository;
import corea.room.domain.Room;
import corea.room.domain.RoomClassification;
import corea.room.domain.RoomStatus;
import corea.room.dto.*;
import corea.room.repository.RoomRepository;
import corea.scheduler.domain.AutomaticMatching;
import corea.scheduler.repository.AutomaticMatchingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomService {

    private static final int PLUS_HOURS_TO_MINIMUM_RECRUITMENT_DEADLINE = 1;
    private static final int PLUS_DAYS_TO_MINIMUM_REVIEW_DEADLINE = 1;
    private static final int PAGE_SIZE = 8;
    private static final int MEMBER_SIZE = 5;

    private final RoomRepository roomRepository;
    private final MemberRepository memberRepository;
    private final ParticipationRepository participationRepository;
    private final AutomaticMatchingRepository automaticMatchingRepository;
    private final MatchResultRepository matchResultRepository;

    @Transactional
    public RoomResponse create(long memberId, RoomCreateRequest request) {
        validateDeadLine(request.recruitmentDeadline(), request.reviewDeadline());

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CoreaException(ExceptionType.MEMBER_NOT_FOUND, String.format("%d에 해당하는 멤버가 없습니다.", memberId)));
        Room room = roomRepository.save(request.toEntity(member));

        long roomId = room.getId();
        participationRepository.save(new Participation(room, memberId));
        automaticMatchingRepository.save(new AutomaticMatching(roomId, request.recruitmentDeadline()));
        return RoomResponse.of(room, true);
    }

    //TODO: 검증 로직 추후 변경할게용~
    private void validateDeadLine(LocalDateTime recruitmentDeadline, LocalDateTime reviewDeadline) {
        LocalDateTime minimumRecruitmentDeadline = recruitmentDeadline.plusHours(PLUS_HOURS_TO_MINIMUM_RECRUITMENT_DEADLINE);
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
        Room room = getRoom(roomId);
        boolean isParticipated = participationRepository.existsByRoomIdAndMemberId(roomId, memberId);

        return RoomResponse.of(room, isParticipated);
    }

    public RoomResponses findParticipatedRooms(long memberId) {
        List<Participation> participations = participationRepository.findAllByMemberId(memberId);
        List<Long> roomIds = participations.stream()
                .map(Participation::getRoomsId)
                .toList();

        List<Room> rooms = roomRepository.findAllById(roomIds);
        return RoomResponses.of(rooms, true, true, 0);
    }

    public RoomResponses findOpenedRooms(long memberId, String expression, int pageNumber) {
        RoomClassification classification = RoomClassification.from(expression);
        RoomStatus status = RoomStatus.OPEN;
        PageRequest pageRequest = PageRequest.of(pageNumber, PAGE_SIZE);

        if (classification.isAll()) {
            Page<Room> roomsWithPage = roomRepository.findAllByMemberAndStatus(memberId, status, pageRequest);
            return RoomResponses.from(roomsWithPage, false, pageNumber);
        }
        Page<Room> roomsWithPage = roomRepository.findAllByMemberAndClassificationAndStatus(memberId, classification, status, pageRequest);
        return RoomResponses.from(roomsWithPage, false, pageNumber);
    }

    public RoomResponses findProgressRooms(long memberId, String expression, int pageNumber) {
        RoomClassification classification = RoomClassification.from(expression);
        RoomStatus status = RoomStatus.PROGRESS;
        PageRequest pageRequest = PageRequest.of(pageNumber, PAGE_SIZE);

        if (classification.isAll()) {
            Page<Room> roomsWithPage = roomRepository.findAllByMemberAndStatus(memberId, status, pageRequest);
            return RoomResponses.from(roomsWithPage, false, pageNumber);
        }
        Page<Room> roomsWithPage = roomRepository.findAllByMemberAndClassificationAndStatus(memberId, classification, status, pageRequest);
        return RoomResponses.from(roomsWithPage, false, pageNumber);
    }

    public RoomResponses findClosedRooms(String expression, int pageNumber) {
        RoomClassification classification = RoomClassification.from(expression);
        RoomStatus status = RoomStatus.CLOSE;
        PageRequest pageRequest = PageRequest.of(pageNumber, PAGE_SIZE);

        if (classification.isAll()) {
            Page<Room> roomsWithPage = roomRepository.findAllByStatus(status, pageRequest);
            return RoomResponses.from(roomsWithPage, false, pageNumber);
        }
        Page<Room> roomsWithPage = roomRepository.findAllByClassificationAndStatus(classification, status, pageRequest);
        return RoomResponses.from(roomsWithPage, false, pageNumber);
    }

    @Transactional
    public void delete(long roomId, long memberId) {
        Room room = getRoom(roomId);
        validateDeletionAuthority(room, memberId);

        participationRepository.deleteAllByRoomId(roomId);
        roomRepository.delete(room);
    }

    private void validateDeletionAuthority(Room room, long memberId) {
        if (room.isNotMatchingManager(memberId)) {
            log.warn("방 삭제 권한이 없습니다. 방 생성자만 방을 삭제할 수 있습니다. 방 생성자 id={}, 요청한 사용자 id={}", room.getManagerId(), memberId);
            throw new CoreaException(ExceptionType.ROOM_DELETION_AUTHORIZATION_ERROR);
        }
    }

    public RoomMemberResponses findMembers(long roomId, long memberId) {
        List<Participation> participants = new java.util.ArrayList<>(
                participationRepository.findAllByRoomId(roomId).stream()
                .filter(participation -> participation.getMemberId() != memberId)
                .toList());
        Collections.shuffle(participants);

        return new RoomMemberResponses(participants.stream()
                .limit(MEMBER_SIZE)
                .map(participation -> getRoomMemberResponse(roomId, participation))
                .toList());
    }

    private RoomMemberResponse getRoomMemberResponse(long roomId, Participation participant) {
        List<MatchResult> matchResults = matchResultRepository.findAllByRevieweeIdAndRoomId(participant.getMemberId(), roomId);
        if (matchResults.isEmpty()) {
            throw new CoreaException(ExceptionType.MEMBER_NOT_FOUND);
        }
        return new RoomMemberResponse(participant.getMemberGithubId(), matchResults.get(0).getPrLink());
    }

    public RoomResponse getRoomById(long roomId) {
        return RoomResponse.from(getRoom(roomId));
    }

    private Room getRoom(long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new CoreaException(ExceptionType.ROOM_NOT_FOUND, String.format("해당 Id의 방이 없습니다. 입력된 Id=%d", roomId)));
    }
}
