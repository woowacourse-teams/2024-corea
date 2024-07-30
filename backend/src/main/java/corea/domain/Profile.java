package corea.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Profile {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private long feedbackCount;

    private long receiveCount;

    private long deliverCount;

    private float averageRating;

    private float grass;
}
