package corea.profile.domain;

import corea.member.domain.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Profile {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;

    private long feedbackCount;

    private long receiveCount;

    private long deliverCount;

    private float averageRating;

    private float attitude;

    public Profile(Member member, long feedbackCount, long receiveCount, long deliverCount, float averageRating, float attitude) {
        this(null, member, feedbackCount, receiveCount, deliverCount, averageRating, attitude);
    }
}
