package corea.auth.service;

import corea.auth.repository.LoginInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LogoutService {

    private final LoginInfoRepository loginInfoRepository;

    @Transactional
    public void logoutByUser(long memberId) {
        loginInfoRepository.deleteByMemberId(memberId);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void logoutByExpiredRefreshToken(String token) {
        loginInfoRepository.deleteByRefreshToken(token);
    }
}
