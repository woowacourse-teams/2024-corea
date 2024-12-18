package corea.scheduler.domain;

import corea.global.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "automatic_matching",
        indexes = {
                @Index(name = "idx_room_id", columnList = "room_id"),
                @Index(name = "idx_room_status", columnList = "status")
        }
)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AutomaticMatching extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long roomId;

    private LocalDateTime matchingStartTime;

    @Enumerated(EnumType.STRING)
    private ScheduleStatus status;

    public AutomaticMatching(long roomId, LocalDateTime matchingStartTime) {
        this(null, roomId, matchingStartTime, ScheduleStatus.PENDING);
    }

    public void updateStatusToDone() {
        this.status = ScheduleStatus.DONE;
    }
}
