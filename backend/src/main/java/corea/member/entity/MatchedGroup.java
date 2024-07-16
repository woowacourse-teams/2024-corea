package corea.member.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Getter
public class MatchedGroup {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private Long groupId;

    private Long memberId;

    public MatchedGroup(final Long groupId, final Long memberId) {
        this(null, groupId, memberId);
    }
    public boolean isEqualMember(final long memberId){
        return this.memberId.equals(memberId);
    }
}
