package corea.review.dto;

import corea.review.domain.Review;

public record ReviewResponse(long id, long roomId, long reviewerId, long revieweeId) {
    public static ReviewResponse from(final Review review) {
        return new ReviewResponse(review.getId(), review.getRoomId(), review.getReviewerId(), review.getRevieweeId());
    }
}
