package corea.participation.domain;

import corea.global.BaseTimeEntity;
import corea.room.domain.Room;
import jakarta.persistence.*;
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
public class Participation extends BaseTimeEntity {

    private static final Logger log = LogManager.getLogger(Participation.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Room room;

    private long memberId;

    private String memberGithubId;

    public Participation(Room room, long memberId, String memberGithubId) {
        this(null, room, memberId, memberGithubId);
        debug(room.getId(), memberId);
    }

    public Participation(Room room, long memberId) {
        this(null, room, memberId, "");
        debug(room.getId(), memberId);
    }

    public boolean isNotMatchingMemberId(long memberId) {
        return this.memberId != memberId;
    }

    public void cancel(){
        room.cancelParticipation();
    }

    public void participate(){
        room.participate();
    }

    public long getRoomsId() {
        return room.getId();
    }

    private static void debug(long roomId, long memberId) {
        log.debug("참가자 생성[방 ID={}, 멤버 ID={}", roomId, memberId);
    }
}
