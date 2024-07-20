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
    @JoinColumn(name = "from_member_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member fromMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_member_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member toMember;

    private String prLink;

    @Enumerated(EnumType.STRING)
    private ReviewStatus reviewStatus;

    public MatchResult(long roomId, Member fromMember, Member toMember, String prLink) {
        this(null, roomId, fromMember, toMember, prLink, ReviewStatus.INCOMPLETE);
    }
}
