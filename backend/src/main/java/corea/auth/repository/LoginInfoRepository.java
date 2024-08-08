package corea.auth.repository;

import corea.auth.domain.LoginInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginInfoRepository extends JpaRepository<LoginInfo, Long> {

    Optional<LoginInfo> findByRefreshToken(String refreshToken);

    void deleteByRefreshToken(String refreshToken);

    void deleteByMemberId(long memberId);
}
