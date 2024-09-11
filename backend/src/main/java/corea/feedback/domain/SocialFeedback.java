package corea.feedback.domain;

import corea.feedback.dto.SocialFeedbackRequest;
import corea.feedback.util.FeedbackKeywordConverter;
import corea.member.domain.Member;
import corea.member.domain.Profile;
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
public class SocialFeedback {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private long roomId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deliver_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member deliver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member receiver;

    private int evaluatePoint;

    @Convert(converter = FeedbackKeywordToStringConverter.class)
    private List<FeedbackKeyword> keywords;

    @Column(length = 512)
    private String feedBackText;

    public SocialFeedback(long roomId, Member deliver, Member receiver, int evaluatePoint, List<FeedbackKeyword> keywords, String feedBackText) {
        this(null, roomId, deliver, receiver, evaluatePoint, keywords, feedBackText);
    }

    public void update(SocialFeedbackRequest request) {
        this.evaluatePoint = request.evaluationPoint();
        this.keywords = FeedbackKeywordConverter.convertToKeywords(request.feedbackKeywords());
        this.feedBackText = request.feedbackText();
    }

    public Profile getReceiverProfile() {
        return receiver.getProfile();
    }
}

