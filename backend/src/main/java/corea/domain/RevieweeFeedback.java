package corea.domain;

import corea.util.StringToListConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

// 리뷰어가 리뷰이한테 해준 피드백
@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Getter
public class RevieweeFeedback {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private long revieweeId;

    private long reviewerId;

    private int evaluatePoint;

    @Convert(converter = StringToListConverter.class)
    private List<RevieweeFeedbackKeyword> keywords;

    @Column(length = 512)
    private String feedBackText;

    private int recommendationPoint;
}

