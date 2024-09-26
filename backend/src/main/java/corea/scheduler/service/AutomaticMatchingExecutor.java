package corea.scheduler.service;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.matching.domain.PullRequestInfo;
import corea.matching.service.MatchingService;
import corea.matching.service.PullRequestProvider;
import corea.room.domain.Room;
import corea.room.repository.RoomRepository;
import corea.scheduler.domain.AutomaticMatching;
import corea.scheduler.repository.AutomaticMatchingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AutomaticMatchingExecutor {

    private final MatchingService matchingService;
    private final PullRequestProvider pullRequestProvider;
    private final RoomRepository roomRepository;
    private final AutomaticMatchingRepository automaticMatchingRepository;

    @Async
    @Transactional
    public void execute(long roomId) {
        try {
            startMatching(roomId);
        } catch (CoreaException e) {
            log.warn("매칭 실행 중 에러 발생: {}", e.getMessage(), e);
        }
    }

    private void startMatching(long roomId) {
        Room room = getRoom(roomId);

        PullRequestInfo pullRequestInfo = pullRequestProvider.getUntilDeadline(room.getRepositoryLink(), room.getRecruitmentDeadline());
        matchingService.match(roomId, pullRequestInfo);

        AutomaticMatching automaticMatching = getAutomaticMatchingByRoomId(roomId);
        automaticMatching.updateStatusToDone();
    }

    private Room getRoom(long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new CoreaException(ExceptionType.ROOM_NOT_FOUND));
    }

    private AutomaticMatching getAutomaticMatchingByRoomId(long roomId) {
        return automaticMatchingRepository.findByRoomId(roomId)
                .orElseThrow(() -> new CoreaException(ExceptionType.AUTOMATIC_MATCHING_NOT_FOUND));
    }
}
