package corea.profile.dto;

import corea.profile.domain.Profile;

import java.util.List;

public record ProfileResponse(
        String profileImage,
        String nickname,
        long receivedReviewCount,
        long givenReviewCount,
        long feedbackCount,
        float averageRating,
        List<String> feedbackKeywords,
        float attitudeScore
) {

    public static ProfileResponse of(Profile profile, List<String> topThreeFeedbackKeywords) {
        return new ProfileResponse(
                profile.getMember().getThumbnailUrl(),
                profile.getMember().getUsername(),
                profile.getReceiveCount(),
                profile.getDeliverCount(),
                profile.getFeedbackCount(),
                profile.getAverageRating(),
                topThreeFeedbackKeywords,
                profile.getAttitude()
        );
    }
}
