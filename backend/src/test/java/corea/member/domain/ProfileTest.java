package corea.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProfileTest {

    @Test
    @DisplayName("평균 점수를 계산하고, 피드백 개수를 증가시킨다.")
    void updateProfileWithIncreaseFeedbackCount() {
        Profile profile = new Profile();
        profile.updateAverageRating(4);
        profile.updateAverageRating(2);

        float averageRating = profile.getAverageRatingValue();
        float feedbackCount = profile.getFeedbackCount();

        assertThat(averageRating).isEqualTo(3);
        assertThat(feedbackCount).isEqualTo(2);
    }
}
