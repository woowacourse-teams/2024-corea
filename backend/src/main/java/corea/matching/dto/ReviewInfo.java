package corea.matching.dto;

import corea.matching.domain.MatchResult;
import corea.matching.domain.ReviewStatus;
import corea.member.domain.Member;

public record ReviewInfo(long userId, String username, String link, ReviewStatus isReviewed) {

    public static ReviewInfo of(MatchResult matchResult, Member member) {
        return new ReviewInfo(member.getId(), member.getUserName(), matchResult.getPrLink(), matchResult.getReviewStatus());
    }
}
