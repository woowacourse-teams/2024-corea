package corea.feedback.domain;

import corea.util.FeedbackKeywordToStringConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

// 리뷰어가 리뷰이한테 해준 피드백
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Getter
public class ReviewerToReviewee {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private long revieweeId;

    private long reviewerId;

    private int evaluatePoint;

    @Convert(converter = FeedbackKeywordToStringConverter.class)
    private List<FeedbackKeyword> keywords;

    @Column(length = 512)
    private String feedBackText;

    private int recommendationPoint;

    public ReviewerToReviewee(long revieweeId, long reviewerId, int evaluatePoint, List<FeedbackKeyword> keywords, String feedBackText, int recommendationPoint) {
        this(null, revieweeId, reviewerId, evaluatePoint, keywords, feedBackText, recommendationPoint);
    }
}

