package corea.ranking.dto;

import corea.evaluation.domain.EvaluateClassification;
import corea.member.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "랭킹 정보 응답")
public record RankingResponse(@Schema(description = "랭킹 순위", example = "1")
                              int rank,

                              @Schema(description = "아이디", example = "ashsty")
                              String nickname,

                              @Schema(description = "프로필 이미지", example = "www.profileImage.com")
                              String profileImage,

                              @Schema(description = "깃허브 PR 링크", example = "www.github.com/sample/pull/24")
                              String githubLink,

                              @Schema(description = "리뷰 개수", example = "17")
                              long givenReviewCount,

                              @Schema(description = "평균 별점", example = "4.9")
                              float averageRating,

                              @Schema(description = "개발 분야", example = "be")
                              String classification
) {

    public static RankingResponse of(int rank, Member member, long deliverCount, float averageEvaluatePoint, EvaluateClassification classification) {
        return new RankingResponse(
                rank,
                member.getUsername(),
                member.getThumbnailUrl(),
                member.getProfileLink(),
                deliverCount,
                averageEvaluatePoint,
                classification.getExpression()
        );
    }
}
