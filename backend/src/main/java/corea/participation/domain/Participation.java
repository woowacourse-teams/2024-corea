package corea.participation.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Participation {

    private static final Logger log = LogManager.getLogger(Participation.class);
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long roomId;

    private long memberId;

    public Participation(long roomId, long memberId) {
        this(null, roomId, memberId);
        log.debug("참가자 생성[방 ID={}, 멤버 ID={}",roomId,memberId);
    }
}
