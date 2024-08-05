package corea.ranking.domain;

import corea.evaluation.domain.EvaluateClassification;
import corea.member.domain.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Ranking {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;

    private int rank;

    private float averageEvaluatePoint;

    private LocalDate date;

    private EvaluateClassification classification;

    public Ranking(Member member, int rank, float averageEvaluatePoint, LocalDate date, EvaluateClassification classification) {
        this(null, member, rank, averageEvaluatePoint, date, classification);
    }
}
