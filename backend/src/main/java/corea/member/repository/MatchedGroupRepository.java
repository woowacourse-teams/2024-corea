package corea.member.repository;

import corea.member.entity.MatchedGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MatchedGroupRepository extends JpaRepository<MatchedGroup, Long> {

    Optional<MatchedGroup> findOneByMemberId(final long memberId);

    List<MatchedGroup> findByGroupId(final long groupId);
}
