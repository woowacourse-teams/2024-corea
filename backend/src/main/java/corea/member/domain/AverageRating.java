package corea.member.domain;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import jakarta.persistence.Embeddable;

@Embeddable
public class AverageRating {

    private static final int MINIMUM_AVERAGE_RATING = 0;
    private static final int DEFAULT_FEEDBACK_COUNT = 0;

    private float averageRating;

    protected AverageRating() {
        this(MINIMUM_AVERAGE_RATING);
    }

    public AverageRating(float averageRating) {
        validateAverageRating(averageRating);
        this.averageRating = averageRating;
    }

    private void validateAverageRating(float averageRating) {
        if (averageRating < MINIMUM_AVERAGE_RATING) {
            throw new CoreaException(ExceptionType.INVALID_VALUE, String.format("평균 점수가 %d보다 작을 수 없습니다.", MINIMUM_AVERAGE_RATING));
        }
    }

    public float calculateTotalEvaluatePoint(long feedbackCount, int evaluatePoint) {
        return averageRating * feedbackCount + evaluatePoint;
    }

    public float calculateTotalEvaluatePoint(long feedbackCount, int preEvaluatePoint, int evaluatePoint) {
        return averageRating * feedbackCount + evaluatePoint - preEvaluatePoint;
    }

    public AverageRating calculateAverageRating(float totalEvaluatePoint, long feedbackCount) {
        validateFeedbackCount(feedbackCount);

        float value = totalEvaluatePoint / feedbackCount;
        return new AverageRating(value);
    }

    private void validateFeedbackCount(long feedbackCount) {
        if (feedbackCount == DEFAULT_FEEDBACK_COUNT) {
            throw new CoreaException(ExceptionType.INVALID_CALCULATION_FORMULA, String.format("%d으로 값을 나눌 수 없습니다.", DEFAULT_FEEDBACK_COUNT));
        }
    }

    public float getValue() {
        return averageRating;
    }
}
