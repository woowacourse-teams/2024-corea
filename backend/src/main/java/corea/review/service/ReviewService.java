package corea.review.service;

import corea.auth.dto.GithubPullRequestReview;
import corea.auth.service.GithubOAuthProvider;
import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.matching.domain.MatchResult;
import corea.matching.repository.MatchResultRepository;
import corea.member.domain.Member;
import corea.member.domain.ProfileCountType;
import corea.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private static final Logger log = LogManager.getLogger(ReviewService.class);

    private final MatchResultRepository matchResultRepository;
    private final MemberRepository memberRepository;
    private final GithubOAuthProvider githubOAuthProvider;

    @Transactional
    public void review(long roomId, long reviewerId, long revieweeId) {
        MatchResult matchResult = getMatchResult(roomId, reviewerId, revieweeId);
        matchResult.reviewComplete();
        reviewLinkUpdate(matchResult, reviewerId);

        increaseReviewCount(reviewerId, ProfileCountType.DELIVER);
        increaseReviewCount(revieweeId, ProfileCountType.RECEIVE);

        log.info("리뷰 완료[{매칭 ID({}), 리뷰어 ID({}, 리뷰이 ID({})", matchResult.getId(), reviewerId, revieweeId);
    }

    private void reviewLinkUpdate(MatchResult matchResult, long reviewerId) {
        Member reviewer = memberRepository.findById(reviewerId)
                .orElseThrow(() -> new CoreaException(ExceptionType.MEMBER_NOT_FOUND, String.format("%d에 해당하는 멤버가 없습니다.", reviewerId)));
        String userName = reviewer.getUsername();
        String newReviewLink = findReviewLink(userName, matchResult.getPrLink());
        matchResult.updateReviewLink(newReviewLink);
    }

    private String findReviewLink(String userName, String prLink) {
        GithubPullRequestReview[] githubPullRequestReviews = githubOAuthProvider.getPullRequestReview(prLink);
        return Stream.of(githubPullRequestReviews)
                .filter(review -> review.user().login().equals(userName))
                .findFirst()
                .map(GithubPullRequestReview::html_url)
                .orElse(prLink);
    }

    public MatchResult getMatchResult(long roomId, long reviewerId, long revieweeId) {
        return matchResultRepository.findByRoomIdAndReviewerIdAndRevieweeId(roomId, reviewerId, revieweeId)
                .orElseThrow(() -> new CoreaException(ExceptionType.NOT_MATCHED_MEMBER,
                        String.format("%d와 %d는 방 %d에서 매칭된 멤버가 아닙니다.", reviewerId, revieweeId, roomId)));
    }

    private void increaseReviewCount(long memberId, ProfileCountType profileCountType) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CoreaException(ExceptionType.MEMBER_NOT_FOUND, String.format("%d에 해당하는 멤버가 없습니다.", memberId)));

        member.increaseCount(profileCountType);
    }
}
