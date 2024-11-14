package corea.room.repository;

import corea.room.domain.Room;
import corea.room.domain.RoomClassification;
import corea.room.domain.RoomStatus;
import org.springframework.data.jpa.domain.Specification;

public class RoomSpec {

    public static Specification<Room> equalStatus(RoomStatus searchStatus) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), searchStatus));
    }

    public static Specification<Room> equalClassification(RoomClassification searchClassification) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("classification"), searchClassification));
    }

    public static Specification<Room> likeTitle(String searchKeyword) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), "%" + searchKeyword + "%"));
    }
}
