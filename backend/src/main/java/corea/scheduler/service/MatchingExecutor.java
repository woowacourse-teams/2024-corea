package corea.scheduler.service;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.matching.domain.PullRequestInfo;
import corea.matching.service.MatchingService;
import corea.matching.service.PullRequestProvider;
import corea.room.domain.Room;
import corea.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class MatchingExecutor {

    private final PlatformTransactionManager transactionManager;
    private final PullRequestProvider pullRequestProvider;
    private final MatchingService matchingService;
    private final RoomRepository roomRepository;

    @Async
    @Transactional
    public void match(long roomId) {
        //TODO: 트랜잭션 분리
        TransactionTemplate template = new TransactionTemplate(transactionManager);

        try {
            template.execute(status -> {
                startMatching(roomId);
                return null;
            });
        } catch (CoreaException e) {
            log.warn("매칭 실행 중 에러 발생: {}", e.getMessage(), e);
            updateRoomStatusToFail(roomId);
        }
    }

    private void startMatching(long roomId) {
        Room room = getRoom(roomId);
        PullRequestInfo pullRequestInfo = pullRequestProvider.getUntilDeadline(room.getRepositoryLink(), room.getRecruitmentDeadline());

        matchingService.match(roomId, pullRequestInfo);
    }


    private void updateRoomStatusToFail(long roomId) {
        //TODO: 위와 동일
        TransactionTemplate template = new TransactionTemplate(transactionManager);
        template.execute(status -> {
            Room room = getRoom(roomId);
            room.updateStatusToFail();
            return null;
        });
    }

    private Room getRoom(long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new CoreaException(ExceptionType.ROOM_NOT_FOUND));
    }
}
