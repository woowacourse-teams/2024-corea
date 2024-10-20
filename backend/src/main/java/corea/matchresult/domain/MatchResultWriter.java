package corea.matchresult.domain;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.matchresult.repository.MatchResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
public class MatchResultWriter {

    private final MatchResultRepository matchResultRepository;

    public MatchResult completeDevelopFeedback(long roomId, long deliverId, long receiverId) {
        MatchResult matchResult = matchResultRepository.findByRoomIdAndReviewerIdAndRevieweeId(roomId, deliverId, receiverId)
                .orElseThrow(() -> new CoreaException(ExceptionType.NOT_MATCHED_MEMBER));

        matchResult.reviewerCompleteFeedback();
        return matchResult;
    }
}
