package corea.room.service;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.matchresult.repository.MatchResultRepository;
import corea.member.domain.Member;
import corea.member.domain.MemberRole;
import corea.member.repository.MemberRepository;
import corea.participation.domain.Participation;
import corea.participation.domain.ParticipationStatus;
import corea.participation.domain.ParticipationWriter;
import corea.participation.repository.ParticipationRepository;
import corea.room.domain.Room;
import corea.room.dto.*;
import corea.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomService {

    private static final int PLUS_HOURS_TO_MINIMUM_RECRUITMENT_DEADLINE = 1;
    private static final int PLUS_DAYS_TO_MINIMUM_REVIEW_DEADLINE = 1;
    private static final int RANDOM_DISPLAY_PARTICIPANTS_SIZE = 6;

    private final RoomRepository roomRepository;
    private final MemberRepository memberRepository;
    private final MatchResultRepository matchResultRepository;
    private final ParticipationRepository participationRepository;
    private final RoomAutomaticService roomAutomaticService;
    private final ParticipationWriter participationWriter;

    @Transactional
    public RoomResponse create(long memberId, RoomCreateRequest request) {
//        validateDeadLine(request.recruitmentDeadline(), request.reviewDeadline());

        Member manager = memberRepository.findById(memberId)
                .orElseThrow(() -> new CoreaException(ExceptionType.MEMBER_NOT_FOUND));
        Room room = roomRepository.save(request.toEntity(manager));
        log.info("방을 생성했습니다. 방 생성자 id={}, 요청한 사용자 id={}", room.getManagerId(), memberId);

        Participation participation = participationWriter.create(room, manager, MemberRole.REVIEWER, ParticipationStatus.MANAGER);

        participationRepository.save(participation);
        roomAutomaticService.createAutomatic(room);

        return RoomResponse.of(room, participation.getMemberRole(), ParticipationStatus.MANAGER);
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

    @Transactional
    public RoomResponse update(long memberId, RoomUpdateRequest request) {
        Room room = getRoom(request.roomId());
        if (room.isNotMatchingManager(memberId)) {
            throw new CoreaException(ExceptionType.MEMBER_IS_NOT_MANAGER);
        }
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CoreaException(ExceptionType.MEMBER_NOT_FOUND));

        Room updatedRoom = roomRepository.save(request.toEntity(member));
        Participation participation = participationRepository.findByRoomIdAndMemberId(updatedRoom.getId(), memberId)
                .orElseThrow(() -> new CoreaException(ExceptionType.NOT_ALREADY_APPLY));

        roomAutomaticService.updateTime(updatedRoom);
        return RoomResponse.of(updatedRoom, participation.getMemberRole(), ParticipationStatus.MANAGER);
    }

    @Transactional
    public void delete(long roomId, long memberId) {
        Room room = getRoom(roomId);
        validateDeletionAuthority(room, memberId);

        log.info("방을 삭제했습니다. 방 id={}, 사용자 iD={}", roomId, memberId);
        roomRepository.delete(room);
        participationRepository.deleteAllByRoomId(roomId);
        roomAutomaticService.deleteAutomatic(room);
    }

    private void validateDeletionAuthority(Room room, long memberId) {
        if (room.isNotMatchingManager(memberId)) {
            log.warn("방 삭제 권한이 없습니다. 방 생성자만 방을 삭제할 수 있습니다. 방 생성자 id={}, 요청한 사용자 id={}", room.getManagerId(), memberId);
            throw new CoreaException(ExceptionType.ROOM_DELETION_AUTHORIZATION_ERROR);
        }
    }

    public RoomParticipantResponses findParticipants(long roomId, long memberId) {
        List<Participation> participants = new ArrayList<>(participationRepository.findAllByRoomId(roomId)
                .stream()
                .filter(participation -> isValidParticipant(participation, memberId))
                .toList());

        Collections.shuffle(participants);

        return new RoomParticipantResponses(participants.stream()
                .limit(RANDOM_DISPLAY_PARTICIPANTS_SIZE)
                .map(participation -> getRoomParticipantResponse(roomId, participation))
                .toList(), participants.size());
    }

    private boolean isValidParticipant(Participation participation, long memberId) {
        return participation.isNotMatchingMemberId(memberId)
                && !participation.isReviewer()
                && !participation.isPullRequestNotSubmitted();
    }

    private RoomParticipantResponse getRoomParticipantResponse(long roomId, Participation participant) {
        return matchResultRepository.findAllByRevieweeIdAndRoomId(participant.getMembersId(), roomId)
                .stream()
                .findFirst()
                .map(matchResult -> new RoomParticipantResponse(
                        matchResult.getReviewee()
                                .getGithubUserId(), matchResult.getReviewee()
                        .getUsername(), matchResult.getPrLink(), matchResult.getReviewee()
                        .getThumbnailUrl()))
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
