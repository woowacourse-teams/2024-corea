package corea.fixture;

import corea.matchresult.domain.MatchResult;
import corea.matchresult.domain.ReviewStatus;
import corea.member.domain.Member;

public class MatchResultFixture {

    public static MatchResult MATCH_RESULT_DOMAIN(long roomId, Member reviewer, Member reviewee) {
        return new MatchResult(
                null,
                roomId,
                reviewer,
                reviewee,
                "https://github.com/woowacourse-teams/2024-corea/pull/99",
                "reviewLink",
                ReviewStatus.COMPLETE,
                true,
                true
        );
    }
}
