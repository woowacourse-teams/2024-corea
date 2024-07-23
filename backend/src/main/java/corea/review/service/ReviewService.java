package corea.review.service;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.matching.domain.MatchResult;
import corea.matching.repository.MatchResultRepository;
import corea.review.domain.Review;
import corea.review.dto.ReviewResponse;
import corea.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MatchResultRepository matchResultRepository;

    public ReviewResponse review(long roomId, long reviewerId, long revieweeId) {
        validateAlreadyExist(roomId, reviewerId, revieweeId);
        MatchResult matchResult = getMatchResult(roomId, reviewerId, revieweeId);

        matchResult.reviewComplete();

        Review review = new Review(roomId, reviewerId, revieweeId);
        return ReviewResponse.from(reviewRepository.save(review));
    }

    private MatchResult getMatchResult(long roomId, long reviewerId, long revieweeId) {
        return matchResultRepository.findByRoomIdAndFromMemberIdAndToMemberId(roomId, reviewerId, revieweeId)
                .orElseThrow(() -> new CoreaException(ExceptionType.NOT_MATCHED_MEMBER,
                        String.format("%d와 %d는 방 %d에서 매칭된 멤버가 아닙니다.", reviewerId, revieweeId, roomId)));
    }

    private void validateAlreadyExist(long roomId, long reviewerId, long revieweeId) {
        if (reviewRepository.existsByRoomIdAndReviewerIdAndRevieweeId(roomId, reviewerId, revieweeId)) {
            throw new CoreaException(ExceptionType.ALREADY_REVIEW_COMPLETED);
        }
    }
}
