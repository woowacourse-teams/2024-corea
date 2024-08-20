package corea.member.domain;

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

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private long feedbackCount;

    private long receiveCount;

    private long deliverCount;

    private float averageRating;

    private float attitude;

    protected Profile() {
        this(null, 0, 0, 0, 0, 36.5f);
    }

    public Profile(long feedbackCount, long receiveCount, long deliverCount, float averageRating, float attitude) {
        this(null, feedbackCount, receiveCount, deliverCount, averageRating, attitude);
    }

    public void increaseCount(CountType countType) {
        if (countType.isFeedback()) {
            feedbackCount++;
            return;
        }
        if (countType.isDeliver()) {
            deliverCount++;
            return;
        }
        receiveCount++;
    }
}
