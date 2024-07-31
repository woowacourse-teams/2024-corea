package corea.feedback.service;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.feedback.domain.ReviewerToReviewee;
import corea.feedback.dto.ReviewerToRevieweeRequest;
import corea.feedback.dto.ReviewerToRevieweeResponse;
import corea.feedback.repository.ReviewerToRevieweeRepository;
import corea.matching.domain.MatchResult;
import corea.matching.repository.MatchResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewerToRevieweeService {

    private final MatchResultRepository matchResultRepository;
    private final ReviewerToRevieweeRepository reviewerToRevieweeRepository;

    public ReviewerToRevieweeResponse create(long roomId, long reviewerId, ReviewerToRevieweeRequest request) {
        validateAlreadyExist(roomId, reviewerId, request.revieweeId());
        ReviewerToReviewee reviewerToReviewee = createEntity(roomId, reviewerId, request);
        return ReviewerToRevieweeResponse
                .of(reviewerToRevieweeRepository.save(reviewerToReviewee));
    }

    public ReviewerToRevieweeResponse update(long feedbackId, long roomId, long reviewerId, ReviewerToRevieweeRequest request) {
        validateNotExist(feedbackId);
        ReviewerToReviewee reviewerToReviewee = createEntity(feedbackId, roomId, reviewerId, request);
        return ReviewerToRevieweeResponse
                .of(reviewerToRevieweeRepository.save(reviewerToReviewee));
    }

    public ReviewerToRevieweeResponse findReviewerToReviewee(long roomId, long reviewerid, String username) {
        return ReviewerToRevieweeResponse
                .of(reviewerToRevieweeRepository.findByRoomIdAndReviewerIdAndRevieweeUsername(roomId, reviewerid, username)
                        .orElseThrow(() -> new CoreaException(ExceptionType.FEEDBACK_NOT_FOUND)));
    }

    private void validateNotExist(long feedbackId) {
        if (!reviewerToRevieweeRepository.existsById(feedbackId)) {
            throw new CoreaException(ExceptionType.FEEDBACK_NOT_FOUND);
        }
    }

    private void validateAlreadyExist(long roomId, long reviewerId, long revieweeId) {
        if (reviewerToRevieweeRepository.existsByRoomIdAndReviewerIdAndRevieweeId(roomId, reviewerId, revieweeId)) {
            throw new CoreaException(ExceptionType.ALREADY_COMPLETED_FEEDBACK);
        }
    }

    private ReviewerToReviewee createEntity(long feedbackId, long roomId, long reviewerId, ReviewerToRevieweeRequest request) {
        MatchResult matchResult = matchResultRepository.findByRoomIdAndReviewerIdAndRevieweeId(roomId, reviewerId, request.revieweeId())
                .orElseThrow(() -> new CoreaException(ExceptionType.NOT_MATCHED_MEMBER));

        return request.toEntity(feedbackId, roomId, matchResult.getReviewer(), matchResult.getReviewee());
    }

    private ReviewerToReviewee createEntity(long roomId, long reviewerId, ReviewerToRevieweeRequest request) {
        MatchResult matchResult = matchResultRepository.findByRoomIdAndReviewerIdAndRevieweeId(roomId, reviewerId, request.revieweeId())
                .orElseThrow(() -> new CoreaException(ExceptionType.NOT_MATCHED_MEMBER));

        return request.toEntity(roomId, matchResult.getReviewer(), matchResult.getReviewee());
    }
}
