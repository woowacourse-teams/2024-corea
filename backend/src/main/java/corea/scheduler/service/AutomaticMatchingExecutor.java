package corea.scheduler.service;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.matching.domain.PullRequestInfo;
import corea.matching.service.MatchingService;
import corea.matching.service.PullRequestProvider;
import corea.room.domain.Room;
import corea.room.repository.RoomRepository;
import corea.scheduler.domain.AutomaticMatching;
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

    @Async
    @Transactional
    public void execute(AutomaticMatching automaticMatching) {
        try {
            startMatching(automaticMatching);
        } catch (CoreaException e) {
            log.warn("매칭 실행 중 에러 발생: {}", e.getMessage(), e);
        }
    }

    private void startMatching(AutomaticMatching automaticMatching) {
        Room room = getRoom(automaticMatching.getRoomId());

        PullRequestInfo pullRequestInfo = pullRequestProvider.getUntilDeadline(room.getRepositoryLink(), room.getRecruitmentDeadline());
        matchingService.match(room.getId(), pullRequestInfo);

        automaticMatching.updateStatusToDone();
    }

    private Room getRoom(long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new CoreaException(ExceptionType.ROOM_NOT_FOUND));
    }
}
