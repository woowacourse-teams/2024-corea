package corea.feedback.domain;

import corea.member.domain.Member;
import corea.util.FeedbackKeywordToStringConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

// 리뷰이가 리뷰어한테 해준 피드백
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Getter
public class RevieweeToReviewerFeedback {

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

    private int evaluatePoint;

    @Convert(converter = FeedbackKeywordToStringConverter.class)
    private List<FeedbackKeyword> keywords;

    @Column(length = 512)
    private String feedBackText;
}

