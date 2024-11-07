package corea.member.domain;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.global.annotation.Reader;
import corea.member.repository.MemberRepository;
import corea.member.repository.ReviewerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Reader
@RequiredArgsConstructor
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
