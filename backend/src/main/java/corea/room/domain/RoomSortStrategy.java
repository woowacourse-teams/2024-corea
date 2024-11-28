package corea.room.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

@RequiredArgsConstructor
public enum RoomSortStrategy {

    RECRUITMENT_DEADLINE_DESC("roomDeadline.recruitmentDeadline", Sort.Direction.ASC),
    REVIEW_DEADLINE_DESC("roomDeadline.reviewDeadline", Sort.Direction.DESC),
    ;

    private final String property;
    private final Sort.Direction direction;

    public static RoomSortStrategy from(RoomStatus status) {
        if (status.isClosed()) {
            return RoomSortStrategy.REVIEW_DEADLINE_DESC;
        }
        return RoomSortStrategy.RECRUITMENT_DEADLINE_DESC;
    }

    public Sort toSort() {
        return Sort.by(direction, property);
    }
}
