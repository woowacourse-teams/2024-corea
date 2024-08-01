package corea.feedback.service;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.feedback.domain.ReviewerToRevieweeFeedback;
import corea.feedback.dto.ReviewerToRevieweeRequest;
import corea.feedback.dto.ReviewerToRevieweeResponse;
import corea.feedback.repository.ReviewerToRevieweeFeedbackRepository;
import corea.matching.domain.MatchResult;
import corea.matching.repository.MatchResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewerToRevieweeFeedbackService {

    private final MatchResultRepository matchResultRepository;
    private final ReviewerToRevieweeFeedbackRepository reviewerToRevieweeFeedbackRepository;

    @Transactional
    public ReviewerToRevieweeResponse create(long roomId, long reviewerId, ReviewerToRevieweeRequest request) {
        validateAlreadyExist(roomId, reviewerId, request.revieweeId());
        ReviewerToRevieweeFeedback reviewerToRevieweeFeedback = reviewerToRevieweeFeedbackRepository.save(createEntity(roomId, reviewerId, request));
        return ReviewerToRevieweeResponse.of(reviewerToRevieweeFeedback);
    }

    @Transactional
    public ReviewerToRevieweeResponse update(long feedbackId, long roomId, long reviewerId, ReviewerToRevieweeRequest request) {
        validateNotExist(feedbackId);
        ReviewerToRevieweeFeedback reviewerToRevieweeFeedback = reviewerToRevieweeFeedbackRepository.save(createEntity(feedbackId, roomId, reviewerId, request));
        return ReviewerToRevieweeResponse.of(reviewerToRevieweeFeedback);
    }

    public ReviewerToRevieweeResponse findReviewerToReviewee(long roomId, long reviewerid, String username) {
        return ReviewerToRevieweeResponse
                .of(reviewerToRevieweeFeedbackRepository.findByRoomIdAndReviewerIdAndRevieweeUsername(roomId, reviewerid, username)
                        .orElseThrow(() -> new CoreaException(ExceptionType.FEEDBACK_NOT_FOUND)));
    }

    private void validateNotExist(long feedbackId) {
        if (!reviewerToRevieweeFeedbackRepository.existsById(feedbackId)) {
            throw new CoreaException(ExceptionType.FEEDBACK_NOT_FOUND);
        }
    }

    private void validateAlreadyExist(long roomId, long reviewerId, long revieweeId) {
        if (reviewerToRevieweeFeedbackRepository.existsByRoomIdAndReviewerIdAndRevieweeId(roomId, reviewerId, revieweeId)) {
            throw new CoreaException(ExceptionType.ALREADY_COMPLETED_FEEDBACK);
        }
    }

    private ReviewerToRevieweeFeedback createEntity(long feedbackId, long roomId, long reviewerId, ReviewerToRevieweeRequest request) {
        MatchResult matchResult = matchResultRepository.findByRoomIdAndReviewerIdAndRevieweeId(roomId, reviewerId, request.revieweeId())
                .orElseThrow(() -> new CoreaException(ExceptionType.NOT_MATCHED_MEMBER));

        return request.toEntity(feedbackId, roomId, matchResult.getReviewer(), matchResult.getReviewee());
    }

    private ReviewerToRevieweeFeedback createEntity(long roomId, long reviewerId, ReviewerToRevieweeRequest request) {
        MatchResult matchResult = matchResultRepository.findByRoomIdAndReviewerIdAndRevieweeId(roomId, reviewerId, request.revieweeId())
                .orElseThrow(() -> new CoreaException(ExceptionType.NOT_MATCHED_MEMBER));

        return request.toEntity(roomId, matchResult.getReviewer(), matchResult.getReviewee());
    }
}
