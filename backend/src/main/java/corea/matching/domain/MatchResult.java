package corea.matching.domain;

import corea.member.domain.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Getter
public class MatchResult {

    private static final Logger log = LoggerFactory.getLogger(MatchResult.class);

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

    private boolean isReviewerCompletedFeedback;

    private boolean isRevieweeCompletedFeedback;

    public MatchResult(long roomId, Member reviewer, Member reviewee, String prLink) {
        this(null, roomId, reviewer, reviewee, prLink, ReviewStatus.INCOMPLETE, false, false);
        log.info("매칭 결과 [방 번호 ({}) ,리뷰어({}) , 리뷰이({})]", roomId, reviewer.getUsername(), reviewee.getUsername());
    }

    public static MatchResult of(long roomId, Pair pair, String prLink) {
        return new MatchResult(roomId, pair.getDeliver(), pair.getReceiver(), prLink);
    }

    public void reviewerCompleteFeedback() {
        isReviewerCompletedFeedback = true;
    }

    public void revieweeCompleteFeedback() {
        isRevieweeCompletedFeedback = true;
    }

    public void reviewComplete() {
        reviewStatus = ReviewStatus.COMPLETE;
    }

    public boolean isReviewed() {
        return reviewStatus.isComplete();
    }
}
