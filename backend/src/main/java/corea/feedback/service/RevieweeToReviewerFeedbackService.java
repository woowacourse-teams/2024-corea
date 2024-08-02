package corea.feedback.service;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.feedback.domain.RevieweeToReviewerFeedback;
import corea.feedback.dto.RevieweeToReviewerFeedbackRequest;
import corea.feedback.dto.RevieweeToReviewerResponse;
import corea.feedback.repository.RevieweeToReviewerFeedbackRepository;
import corea.matching.domain.MatchResult;
import corea.matching.repository.MatchResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RevieweeToReviewerFeedbackService {

    private final RevieweeToReviewerFeedbackRepository revieweeToReviewerFeedbackRepository;
    private final MatchResultRepository matchResultRepository;

    @Transactional
    public RevieweeToReviewerResponse create(long roomId, long revieweeId, RevieweeToReviewerFeedbackRequest request) {
        validateAlreadyExist(roomId, request.reviewerId(), revieweeId);
        RevieweeToReviewerFeedback revieweeToReviewerFeedback = revieweeToReviewerFeedbackRepository.save(createEntity(roomId, revieweeId, request));
        return RevieweeToReviewerResponse.of(revieweeToReviewerFeedback);
    }

    @Transactional
    public RevieweeToReviewerResponse update(long feedbackId, long roomId, long revieweeId, RevieweeToReviewerFeedbackRequest request) {
        validateNotExist(feedbackId);
        RevieweeToReviewerFeedback revieweeToReviewerFeedback = revieweeToReviewerFeedbackRepository.save(createEntity(feedbackId, roomId, revieweeId, request));
        return RevieweeToReviewerResponse.of(revieweeToReviewerFeedback);
    }

    public RevieweeToReviewerResponse findRevieweeToReviewerFeedback(long roomId, long revieweeId, String username) {
        return RevieweeToReviewerResponse
                .of(revieweeToReviewerFeedbackRepository.findByRoomIdAndRevieweeIdAndReviewerUsername(roomId, revieweeId, username)
                        .orElseThrow(() -> new CoreaException(ExceptionType.FEEDBACK_NOT_FOUND)));
    }

    private void validateNotExist(long feedbackId) {
        if (!revieweeToReviewerFeedbackRepository.existsById(feedbackId)) {
            throw new CoreaException(ExceptionType.FEEDBACK_NOT_FOUND);
        }
    }

    private void validateAlreadyExist(long roomId, long reviewerId, long revieweeId) {
        if (revieweeToReviewerFeedbackRepository.existsByRoomIdAndReviewerIdAndRevieweeId(roomId, reviewerId, revieweeId)) {
            throw new CoreaException(ExceptionType.ALREADY_COMPLETED_FEEDBACK);
        }
    }

    private RevieweeToReviewerFeedback createEntity(long feedbackId, long roomId, long revieweeId, RevieweeToReviewerFeedbackRequest request) {
        MatchResult matchResult = matchResultRepository.findByRoomIdAndReviewerIdAndRevieweeId(roomId, request.reviewerId(), revieweeId)
                .orElseThrow(() -> new CoreaException(ExceptionType.NOT_MATCHED_MEMBER));

        return request.toEntity(feedbackId, roomId, matchResult.getReviewer(), matchResult.getReviewee());
    }

    private RevieweeToReviewerFeedback createEntity(long roomId, long revieweeId, RevieweeToReviewerFeedbackRequest request) {
        MatchResult matchResult = matchResultRepository.findByRoomIdAndReviewerIdAndRevieweeId(roomId, request.reviewerId(), revieweeId)
                .orElseThrow(() -> new CoreaException(ExceptionType.NOT_MATCHED_MEMBER));

        return request.toEntity(roomId, matchResult.getReviewer(), matchResult.getReviewee());
    }
}
