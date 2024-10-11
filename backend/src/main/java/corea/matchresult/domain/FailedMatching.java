package corea.matchresult.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class FailedMatching {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long roomId;

    @Enumerated(EnumType.STRING)
    private MatchingFailedReason reason;

    public FailedMatching(long roomId, MatchingFailedReason reason) {
        this(null, roomId, reason);
    }
}
