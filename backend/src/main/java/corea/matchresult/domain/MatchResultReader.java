package corea.matchresult.domain;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.matchresult.repository.MatchResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MatchResultReader {

    private final MatchResultRepository matchResultRepository;

    public MatchResult findOne(long roomId,long reviewerId,long revieweeId) {
        return matchResultRepository.findByRoomIdAndReviewerIdAndRevieweeId(roomId, reviewerId, revieweeId)
                .orElseThrow(() -> new CoreaException(ExceptionType.NOT_MATCHED_MEMBER));
    }
}
