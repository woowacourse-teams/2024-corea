package corea.evaluation.domain;

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
public class Evaluation {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;

    private int evaluatePoint;

    private EvaluateClassification classification;

    public Evaluation(Member member, int evaluatePoint, EvaluateClassification classification) {
        this(null, member, evaluatePoint, classification);
    }
}
