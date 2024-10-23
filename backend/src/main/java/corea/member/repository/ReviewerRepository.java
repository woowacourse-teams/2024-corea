package corea.member.repository;

import corea.member.domain.Reviewer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewerRepository extends JpaRepository<Reviewer, Long> {

    boolean existsByGithubUserId(String githubUserId);
}
