package corea.room.domain;

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
public class RoomMatchInfo {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private long roomId;

    private boolean isPublic;

    public RoomMatchInfo(final long roomId, final boolean isPublic) {
        this(null, roomId, isPublic);
    }

    public boolean isPublic() {
        return isPublic;
    }
}
