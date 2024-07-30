package corea.domain;

import corea.util.StringToListConverter;
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
public class ReviewerFeedback {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private long revieweeId;

    private long reviewerId;

    private int evaluatePoint;

    @Convert(converter = StringToListConverter.class)
    private List<ReviewerFeedbackKeyword> keywords;

    @Column(length = 512)
    private String feedBackText;
}

