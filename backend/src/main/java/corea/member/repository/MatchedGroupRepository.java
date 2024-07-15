package corea.member.repository;

import corea.member.entity.MatchedGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchedGroupRepository extends JpaRepository<MatchedGroup, Long> {

    Long findGroupIdByMemberId(final Long memberId);

    List<Long> findMemberIdsByGroupId(final Long groupId);
}
