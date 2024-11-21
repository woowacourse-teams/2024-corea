package corea.room.domain;

import org.springframework.stereotype.Component;

@Component
public class RoomSortStrategyFactory {

    public RoomSortStrategy getRoomSortStrategy(RoomStatus status) {
        if (status.isClosed()) {
            return RoomSortStrategy.REVIEW_DEADLINE_DESC;
        }
        return RoomSortStrategy.RECRUITMENT_DEADLINE_DESC;
    }
}
