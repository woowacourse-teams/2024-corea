package corea.member.domain;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.member.repository.MemberRepository;
import corea.member.repository.ReviewerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberReader {

    private final MemberRepository memberRepository;
    private final ReviewerRepository reviewerRepository;

    public Member findOne(long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new CoreaException(ExceptionType.MEMBER_NOT_FOUND));
    }

    public boolean isReviewer(String githubUserId) {
        return reviewerRepository.existsByGithubUserId(githubUserId);
    }
}
