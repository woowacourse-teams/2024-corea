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

    private int ranking;

    private float averageEvaluatePoint;

    private LocalDate date;

    private EvaluateClassification classification;

    public Ranking(Member member, int ranking, float averageEvaluatePoint, LocalDate date, EvaluateClassification classification) {
        this(null, member, ranking, averageEvaluatePoint, date, classification);
    }

    @Override
    public String toString() {
        return "Ranking{" +
                "id=" + id +
                ", ranking=" + ranking +
                ", averageEvaluatePoint=" + averageEvaluatePoint +
                ", date=" + date +
                ", classification=" + classification +
                '}';
    }
}
