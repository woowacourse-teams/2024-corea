package corea.scheduler.domain;

import corea.global.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "automatic_update",
        indexes = {
                @Index(name = "idx_room_id", columnList = "room_id"),
                @Index(name = "idx_room_status", columnList = "status")
        }
)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AutomaticUpdate extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long roomId;

    private LocalDateTime updateStartTime;

    @Enumerated(EnumType.STRING)
    private ScheduleStatus status;

    public AutomaticUpdate(long roomId, LocalDateTime updateStartTime) {
        this(null, roomId, updateStartTime, ScheduleStatus.PENDING);
    }

    public void updateStatusToDone() {
        this.status = ScheduleStatus.DONE;
    }
}
