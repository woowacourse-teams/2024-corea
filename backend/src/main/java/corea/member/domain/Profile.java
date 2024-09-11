package corea.member.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@AllArgsConstructor
@Getter
public class Profile {

    private static final int DEFAULT_FEEDBACK_COUNT = 0;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private long feedbackCount;

    private long receiveCount;

    private long deliverCount;

    @Embedded
    private AverageRating averageRating;

    private float attitude;

    protected Profile() {
        this(DEFAULT_FEEDBACK_COUNT, 0, 0, 0, 36.5f);
    }

    public Profile(long feedbackCount, long receiveCount, long deliverCount, float averageRating, float attitude) {
        this(null, feedbackCount, receiveCount, deliverCount, new AverageRating(averageRating), attitude);
    }

    public void increaseCount(ProfileCountType profileCountType) {
        if (profileCountType.isDeliver()) {
            deliverCount++;
            return;
        }
        receiveCount++;
    }

    public void updateProfile(int evaluatePoint) {
        float totalEvaluatePoint = averageRating.calculateTotalEvaluatePoint(feedbackCount, evaluatePoint);
        feedbackCount++;

        this.averageRating = averageRating.calculateAverageRating(totalEvaluatePoint, feedbackCount);
    }

    public void updateProfile(int preEvaluatePoint, int evaluatePoint) {
        float totalEvaluatePoint = averageRating.calculateTotalEvaluatePoint(feedbackCount, preEvaluatePoint, evaluatePoint);
        this.averageRating = averageRating.calculateAverageRating(totalEvaluatePoint, feedbackCount);
    }

    public float getAverageRatingValue() {
        return averageRating.getValue();
    }
}
