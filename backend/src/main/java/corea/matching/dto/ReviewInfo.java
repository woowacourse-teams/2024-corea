package corea.matching.dto;

import corea.matching.domain.MatchResult;
import corea.matching.domain.ReviewStatus;
import corea.member.domain.Member;

public record ReviewInfo(Long userId, String username, String link, ReviewStatus isReviewed) {

    public static ReviewInfo from(MatchResult matchResult, Member member) {
        return new ReviewInfo(member.getId(), member.getName(), matchResult.getPrLink(), matchResult.getReviewStatus());
    }
}
