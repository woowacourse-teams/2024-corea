package corea.matching.repository;

import corea.matching.domain.MatchResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MatchResultRepository extends JpaRepository<MatchResult, Long> {

    List<MatchResult> findAllByToMemberIdAndRoomId(long memberId, long roomId);

    List<MatchResult> findAllByFromMemberIdAndRoomId(long memberId, long roomId);

    Optional<MatchResult> findByRoomIdAndFromMemberIdAndToMemberId(long roomId, long fromMemberId, long toMemberId);
}
