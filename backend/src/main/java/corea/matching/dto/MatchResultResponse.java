package corea.matching.dto;

import corea.matching.domain.MatchResult;
import corea.member.domain.Member;

public record MatchResultResponse(long userId, String username, String link, boolean isReviewed) {

    public static MatchResultResponse of(MatchResult matchResult, Member member) {
        return new MatchResultResponse(member.getId(), member.getUsername(), matchResult.getPrLink(), matchResult.isReviewed());
    }
}
