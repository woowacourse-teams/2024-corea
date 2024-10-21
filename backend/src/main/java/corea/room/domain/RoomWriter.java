package corea.room.domain;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.member.domain.Member;
import corea.participation.domain.ParticipationWriter;
import corea.room.dto.RoomCreateRequest;
import corea.room.dto.RoomUpdateRequest;
import corea.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
public class RoomWriter {

    private static final int PLUS_HOURS_TO_MINIMUM_RECRUITMENT_DEADLINE = 1;
    private static final int PLUS_DAYS_TO_MINIMUM_REVIEW_DEADLINE = 1;

    private final RoomRepository roomRepository;
    private final ParticipationWriter participationWriter;

    // TODO 서비스 용 DTO 만들어야함
    public Room create(Member manager, RoomCreateRequest request) {
//        validateDeadLine(request.recruitmentDeadline(), request.reviewDeadline());
        Room room = roomRepository.save(request.toEntity(manager));
        log.info("방을 생성했습니다. 방 생성자 id={}, 요청한 사용자 id={}", room.getId(), room.getManagerId());
        return room;
    }

    public Room update(Room room, Member manager, RoomUpdateRequest request) {
        validate(room, manager);
        return roomRepository.save(request.toEntity(room, manager));
    }

    public void delete(Room room, Member manager) {
        validate(room, manager);
        roomRepository.delete(room);
        participationWriter.deleteAllByRoom(room);
        log.info("방을 삭제했습니다. 방 id={}, 사용자 iD={}", room.getId(), manager.getId());
    }

    private void validate(Room room, Member member) {
        if (room.isNotMatchingManager(member.getId())) {
            log.warn("인증되지 않은 방 변경 시도 방 생성자 id={}, 요청한 사용자 id={}", room.getId(), member.getId());
            throw new CoreaException(ExceptionType.ROOM_MODIFY_AUTHORIZATION_ERROR);
        }
        if (room.isNotOpened()) {
            throw new CoreaException(ExceptionType.ROOM_STATUS_INVALID);
        }
    }

//    private void validateDeadLine(LocalDateTime recruitmentDeadline, LocalDateTime reviewDeadline) {
//        LocalDateTime currentDateTime = LocalDateTime.now();
//
//        LocalDateTime minimumRecruitmentDeadline = currentDateTime.plusHours(PLUS_HOURS_TO_MINIMUM_RECRUITMENT_DEADLINE);
//        if (recruitmentDeadline.isBefore(minimumRecruitmentDeadline)) {
//            throw new CoreaException(ExceptionType.INVALID_RECRUITMENT_DEADLINE,
//                    String.format("모집 마감 시간은 현재 시간보다 %d시간 이후여야 합니다.", PLUS_HOURS_TO_MINIMUM_RECRUITMENT_DEADLINE));
//        }
//        LocalDateTime minimumReviewDeadline = recruitmentDeadline.plusDays(PLUS_DAYS_TO_MINIMUM_REVIEW_DEADLINE);
//        if (reviewDeadline.isBefore(minimumReviewDeadline)) {
//            throw new CoreaException(ExceptionType.INVALID_REVIEW_DEADLINE,
//                    String.format("리뷰 마감 시간은 모집 마감 시간보다 %d일 이후여야 합니다.", PLUS_DAYS_TO_MINIMUM_REVIEW_DEADLINE));
//        }
//    }
}
