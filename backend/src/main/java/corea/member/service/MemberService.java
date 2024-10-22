package corea.member.service;

import corea.member.domain.Member;
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
        Member member = memberReader.findOneByGithubUserId(githubUserId);
        return MemberRoleResponse.from(member);
    }
}
