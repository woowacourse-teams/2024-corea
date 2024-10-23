package corea.member.service;

import corea.member.domain.MemberReader;
import corea.member.dto.MemberRoleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberReader memberReader;

    public MemberRoleResponse getMemberRoleWithGithubUserId(String githubUserId) {
        boolean isReviewer = memberReader.isReviewer(githubUserId);
        return MemberRoleResponse.from(isReviewer);
    }
}
