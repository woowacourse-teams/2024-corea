package corea.matching.repository;

import corea.matching.domain.MatchResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MatchResultRepository extends JpaRepository<MatchResult, Long> {

    @Query("""
            SELECT COUNT(m) > 0
            FROM MatchResult m
            WHERE m.roomId = :roomId AND (m.reviewer.id = :memberId OR m.reviewee.id = :memberId)
            """)
    boolean existsByRoomIdAndMemberId(long roomId, long memberId);

    List<MatchResult> findAllByRevieweeIdAndRoomId(long revieweeId, long roomId);

    List<MatchResult> findAllByReviewerIdAndRoomId(long reviewerId, long roomId);

    Optional<MatchResult> findByRoomIdAndReviewerIdAndRevieweeId(long roomId, long reviewerId, long revieweeId);
}
