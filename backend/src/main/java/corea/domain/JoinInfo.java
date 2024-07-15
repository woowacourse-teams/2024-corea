package corea.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class JoinInfo {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private long memberId;

    private long roomId;

    public JoinInfo(final long memberId, final long roomId) {
        this(null, memberId, roomId);
    }
}
