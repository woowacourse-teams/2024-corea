package corea.matching.dto;

import corea.matching.domain.MatchResult;
import corea.member.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "매칭 결과 응답")
public record MatchResultResponse(@Schema(description = "사용자 아이디", example = "1")
                                  long userId,

                                  @Schema(description = "사용자 이름", example = "hjk0761")
                                  String username,

                                  @Schema(description = "Pull Request 링크", example = "github.com/project/pull/122")
                                  String link,

                                  @Schema(description = "내가 상대방의 코드리뷰를 완료하였는지 여부", example = "true")
                                  boolean isReviewed,

                                  @Schema(description = "내가 상대방의 피드백 작성을 완료하였는지 여부", example = "false")
                                  boolean isWrited) {

    public static MatchResultResponse ofReviewee(MatchResult matchResult, Member member) {
        return new MatchResultResponse(member.getId(), member.getUsername(), matchResult.getReviewLink(), matchResult.isReviewed(), matchResult.isRevieweeCompletedFeedback());
    }

    public static MatchResultResponse ofReviewer(MatchResult matchResult, Member member) {
        return new MatchResultResponse(member.getId(), member.getUsername(), matchResult.getPrLink(), matchResult.isReviewed(), matchResult.isReviewerCompletedFeedback());
    }
}
