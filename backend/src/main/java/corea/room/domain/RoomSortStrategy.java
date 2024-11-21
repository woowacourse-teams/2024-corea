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

    public Sort toSort() {
        return Sort.by(direction, property);
    }
}
