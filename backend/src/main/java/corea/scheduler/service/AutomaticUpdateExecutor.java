package corea.scheduler.service;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.matching.domain.MatchResult;
import corea.matching.domain.ReviewStatus;
import corea.matching.repository.MatchResultRepository;
import corea.member.domain.Member;
import corea.member.domain.MemberRole;
import corea.room.domain.Room;
import corea.room.repository.RoomRepository;
import corea.scheduler.domain.AutomaticUpdate;
import corea.scheduler.repository.AutomaticUpdateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AutomaticUpdateExecutor {

    private final RoomRepository roomRepository;
    private final MatchResultRepository matchResultRepository;
    private final AutomaticUpdateRepository automaticUpdateRepository;

    @Async
    @Transactional
    public void execute(long roomId) {
        Room room = getRoom(roomId);
        room.updateStatusToClose();

        matchResultRepository.findAllByRoomIdAndReviewStatus(roomId, ReviewStatus.COMPLETE)
                .forEach(this::increaseMembersReviewCountIn);

        AutomaticUpdate automaticUpdate = getAutomaticUpdateByRoomId(roomId);
        automaticUpdate.updateStatusToDone();
    }

    private void increaseMembersReviewCountIn(MatchResult matchResult) {
        Member reviewer = matchResult.getReviewer();
        reviewer.increaseReviewCount(MemberRole.REVIEWER);

        Member reviewee = matchResult.getReviewee();
        reviewee.increaseReviewCount(MemberRole.REVIEWEE);
    }

    private Room getRoom(long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new CoreaException(ExceptionType.ROOM_NOT_FOUND));
    }

    private AutomaticUpdate getAutomaticUpdateByRoomId(long roomId) {
        return automaticUpdateRepository.findByRoomId(roomId)
                .orElseThrow(() -> new CoreaException(ExceptionType.AUTOMATIC_UPDATE_NOT_FOUND));
    }
}
