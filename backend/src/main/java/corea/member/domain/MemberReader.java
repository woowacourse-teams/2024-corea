package corea.member.domain;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.global.annotation.Reader;
import corea.member.repository.MemberRepository;
import corea.member.repository.ReviewerRepository;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Reader
@RequiredArgsConstructor
public class MemberReader {

    private final MemberRepository memberRepository;
    private final ReviewerRepository reviewerRepository;

    public Member findOne(long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new CoreaException(ExceptionType.MEMBER_NOT_FOUND));
    }

    public Map<Long, Member> findMembersMappedById(Iterable<Long> memberIds) {
        return memberRepository.findAllById(memberIds)
                .stream()
                .collect(Collectors.toMap(Member::getId, Function.identity()));
    }

    public boolean isReviewer(String githubUserId) {
        return reviewerRepository.existsByGithubUserId(githubUserId);
    }
}
