package corea.room.service;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
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
import corea.scheduler.domain.AutomaticUpdate;
import corea.scheduler.repository.AutomaticMatchingRepository;
import corea.scheduler.repository.AutomaticUpdateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static corea.room.domain.ParticipationStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomService {

    private static final int PLUS_HOURS_TO_MINIMUM_RECRUITMENT_DEADLINE = 1;
    private static final int PLUS_DAYS_TO_MINIMUM_REVIEW_DEADLINE = 1;
    private static final int PAGE_DISPLAY_SIZE = 8;
    private static final int RANDOM_DISPLAY_PARTICIPANTS_SIZE = 6;

    private final RoomRepository roomRepository;
    private final MemberRepository memberRepository;
    private final MatchResultRepository matchResultRepository;
    private final ParticipationRepository participationRepository;
    private final AutomaticMatchingRepository automaticMatchingRepository;
    private final AutomaticUpdateRepository automaticUpdateRepository;

    @Transactional
    public RoomResponse create(long memberId, RoomCreateRequest request) {
        validateDeadLine(request.recruitmentDeadline(), request.reviewDeadline());

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CoreaException(ExceptionType.MEMBER_NOT_FOUND));
        Room room = roomRepository.save(request.toEntity(member));

        participationRepository.save(new Participation(room, memberId));
        automaticMatchingRepository.save(new AutomaticMatching(room.getId(), request.recruitmentDeadline()));
        automaticUpdateRepository.save(new AutomaticUpdate(room.getId(), request.reviewDeadline()));

        return RoomResponse.of(room, MANAGER);
    }

    private void validateDeadLine(LocalDateTime recruitmentDeadline, LocalDateTime reviewDeadline) {
        LocalDateTime currentDateTime = LocalDateTime.now();

        LocalDateTime minimumRecruitmentDeadline = currentDateTime.plusHours(PLUS_HOURS_TO_MINIMUM_RECRUITMENT_DEADLINE);
        if (recruitmentDeadline.isBefore(minimumRecruitmentDeadline)) {
            throw new CoreaException(ExceptionType.INVALID_RECRUITMENT_DEADLINE,
                    String.format("모집 마감 시간은 현재 시간보다 %d시간 이후여야 합니다.", PLUS_HOURS_TO_MINIMUM_RECRUITMENT_DEADLINE));
        }
        LocalDateTime minimumReviewDeadline = recruitmentDeadline.plusDays(PLUS_DAYS_TO_MINIMUM_REVIEW_DEADLINE);
        if (reviewDeadline.isBefore(minimumReviewDeadline)) {
            throw new CoreaException(ExceptionType.INVALID_REVIEW_DEADLINE,
                    String.format("리뷰 마감 시간은 모집 마감 시간보다 %d일 이후여야 합니다.", PLUS_DAYS_TO_MINIMUM_REVIEW_DEADLINE));
        }
    }

    public RoomResponse findOne(long roomId, long memberId) {
        Room room = getRoom(roomId);
        if (room.isManagerId(memberId)) {
            return RoomResponse.of(room, MANAGER);
        }
        if (participationRepository.existsByRoomIdAndMemberId(roomId, memberId)) {
            return RoomResponse.of(room, PARTICIPATED);
        }
        return RoomResponse.of(room, NOT_PARTICIPATED);
    }

    public RoomResponses findParticipatedRooms(long memberId) {
        List<Participation> participations = participationRepository.findAllByMemberId(memberId);
        List<Long> roomIds = participations.stream()
                .map(Participation::getRoomsId)
                .toList();

        List<Room> rooms = roomRepository.findAllByIdInOrderByReviewDeadlineAsc(roomIds);
        return RoomResponses.of(rooms, PARTICIPATED, true, 0);
    }

    public RoomResponses findRoomsWithRoomStatus(long memberId, int pageNumber, String expression, RoomStatus roomStatus) {
        RoomClassification classification = RoomClassification.from(expression);
        return getRoomResponses(memberId, pageNumber, classification, roomStatus);
    }

    private RoomResponses getRoomResponses(long memberId, int pageNumber, RoomClassification classification, RoomStatus status) {
        PageRequest pageRequest = PageRequest.of(pageNumber, PAGE_DISPLAY_SIZE);

        if (classification.isAll()) {
            Page<Room> roomsWithPage = roomRepository.findAllByMemberAndStatus(memberId, status, pageRequest);
            return RoomResponses.of(roomsWithPage, NOT_PARTICIPATED, pageNumber);
        }
        Page<Room> roomsWithPage = roomRepository.findAllByMemberAndClassificationAndStatus(memberId, classification, status, pageRequest);
        return RoomResponses.of(roomsWithPage, NOT_PARTICIPATED, pageNumber);
    }

    @Transactional
    public void delete(long roomId, long memberId) {
        Room room = getRoom(roomId);
        validateDeletionAuthority(room, memberId);

        roomRepository.delete(room);
        participationRepository.deleteAllByRoomId(roomId);
        automaticMatchingRepository.deleteByRoomId(roomId);
        automaticUpdateRepository.deleteByRoomId(roomId);
    }

    private void validateDeletionAuthority(Room room, long memberId) {
        if (room.isNotMatchingManager(memberId)) {
            log.warn("방 삭제 권한이 없습니다. 방 생성자만 방을 삭제할 수 있습니다. 방 생성자 id={}, 요청한 사용자 id={}", room.getManagerId(), memberId);
            throw new CoreaException(ExceptionType.ROOM_DELETION_AUTHORIZATION_ERROR);
        }
    }

    public RoomParticipantResponses findParticipants(long roomId, long memberId) {
        List<Participation> participants = new java.util.ArrayList<>(
                participationRepository.findAllByRoomId(roomId).stream()
                        .filter(participation -> participation.isNotMatchingMemberId(memberId))
                        .toList());
        Collections.shuffle(participants);

        return new RoomParticipantResponses(participants.stream()
                .limit(RANDOM_DISPLAY_PARTICIPANTS_SIZE)
                .map(participation -> getRoomMemberResponse(roomId, participation))
                .toList());
    }

    private RoomParticipantResponse getRoomMemberResponse(long roomId, Participation participant) {
        return matchResultRepository.findAllByRevieweeIdAndRoomId(participant.getMemberId(), roomId).stream()
                .findFirst()
                .map(matchResult -> new RoomParticipantResponse(
                        matchResult.getReviewee().getGithubUserId(), matchResult.getReviewee().getUsername(), matchResult.getPrLink(), matchResult.getReviewee().getThumbnailUrl()))
                .orElseThrow(() -> new CoreaException(ExceptionType.MEMBER_NOT_FOUND));
    }

    public RoomResponse getRoomById(long roomId) {
        return RoomResponse.from(getRoom(roomId));
    }

    private Room getRoom(long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new CoreaException(ExceptionType.ROOM_NOT_FOUND, String.format("해당 Id의 방이 없습니다. 입력된 Id=%d", roomId)));
    }
}
