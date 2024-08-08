package corea.review.service;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.matching.domain.MatchResult;
import corea.matching.repository.MatchResultRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private static final Logger log = LogManager.getLogger(ReviewService.class);
    private final MatchResultRepository matchResultRepository;

    @Transactional
    public void review(long roomId, long reviewerId, long revieweeId) {
        MatchResult matchResult = getMatchResult(roomId, reviewerId, revieweeId);
        log.info("리뷰 완료[{매칭 ID({}), 리뷰어 ID({}, 리뷰이 ID({})", matchResult.getId(), reviewerId, revieweeId);
        matchResult.reviewComplete();
    }

    private MatchResult getMatchResult(long roomId, long reviewerId, long revieweeId) {
        return matchResultRepository.findByRoomIdAndReviewerIdAndRevieweeId(roomId, reviewerId, revieweeId)
                .orElseThrow(() -> new CoreaException(ExceptionType.NOT_MATCHED_MEMBER,
                        String.format("%d와 %d는 방 %d에서 매칭된 멤버가 아닙니다.", reviewerId, revieweeId, roomId)));
    }
}
