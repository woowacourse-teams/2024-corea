package corea.matching.domain;

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
public class MatchResult {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private long roomId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member reviewer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewee_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member reviewee;

    private String prLink;

    @Enumerated(EnumType.STRING)
    private ReviewStatus reviewStatus;

    public MatchResult(long roomId, Member reviewer, Member reviewee, String prLink) {
        this(null, roomId, reviewer, reviewee, prLink, ReviewStatus.INCOMPLETE);
    }

    public void reviewComplete() {
        reviewStatus = ReviewStatus.COMPLETE;
    }

    public boolean isReviewed() {
        return reviewStatus.isComplete();
    }
}
