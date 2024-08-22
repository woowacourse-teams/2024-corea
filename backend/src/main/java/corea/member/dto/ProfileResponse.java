package corea.member.dto;

import corea.member.domain.Member;
import corea.member.domain.Profile;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "프로필 정보 응답")
public record ProfileResponse(@Schema(description = "프로필 이미지", example = "www.myProfile.jpg")
                              String profileImage,

                              @Schema(description = "별명", example = "ashsty")
                              String nickname,

                              @Schema(description = "받은 리뷰 개수", example = "2")
                              long receivedReviewCount,

                              @Schema(description = "내가 리뷰한 코드 개수", example = "20")
                              long givenReviewCount,

                              @Schema(description = "받은 피드백 개수", example = "16")
                              long feedbackCount,

                              @Schema(description = "평균 별점", example = "4.7")
                              float averageRating,

                              @Schema(description = "받은 긍정적 피드백 키워드 중 상위 3개", example = "[\"리뷰가 빨랐어요\", \"코드를 이해하기 쉬웠어요\", \"컨벤션이 잘 지켜졌어요\"]")
                              List<String> feedbackKeywords,

                              @Schema(description = "잔디색", example = "70.7")
                              float attitudeScore
) {

    public static ProfileResponse of(Member member, List<String> topThreeFeedbackKeywords) {
        Profile profile = member.getProfile();

        return new ProfileResponse(
                member.getThumbnailUrl(),
                member.getUsername(),
                profile.getReceiveCount(),
                profile.getDeliverCount(),
                profile.getFeedbackCount(),
                profile.getAverageRatingValue(),
                topThreeFeedbackKeywords,
                profile.getAttitude()
        );
    }
}
