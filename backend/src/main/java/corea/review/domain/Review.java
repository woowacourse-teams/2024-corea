package corea.review.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Review {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private Long roomId;

    private Long reviewerId;

    private Long revieweeId;

    public Review(final Long roomId, final Long reviewerId, final Long revieweeId) {
        this(null,roomId,reviewerId,revieweeId);
    }
}
