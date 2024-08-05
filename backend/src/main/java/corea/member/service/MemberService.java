package corea.member.service;

import corea.auth.domain.GithubUserInfo;
import corea.exception.CoreaException;
import corea.member.domain.Member;
import corea.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static corea.exception.ExceptionType.MEMBER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public Member login(GithubUserInfo userInfo) {
        return memberRepository.findByUsername(userInfo.login())
                .orElse(register(new Member(userInfo.login(), userInfo.avatar_url(), userInfo.name(), userInfo.email(), true, 0.0f)));
    }

    private Member register(Member member) {
        return memberRepository.save(member);
    }

    public Member findById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new CoreaException(MEMBER_NOT_FOUND));
    }
}
