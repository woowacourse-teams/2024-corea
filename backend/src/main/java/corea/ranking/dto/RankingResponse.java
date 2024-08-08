package corea.ranking.dto;

import corea.evaluation.domain.EvaluateClassification;
import corea.member.domain.Member;

public record RankingResponse(
        String nickname,
        String githubLink,
        long givenReviewCount,
        float averageRating,
        String classification
) {

    public static RankingResponse of(Member member, long deliverCount, float averageEvaluatePoint, EvaluateClassification classification) {
        return new RankingResponse(
                member.getUsername(),
                member.getProfileLink(),
                deliverCount,
                averageEvaluatePoint,
                classification.getExpression()
        );
    }
}
