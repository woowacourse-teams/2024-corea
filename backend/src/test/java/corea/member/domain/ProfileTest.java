package corea.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProfileTest {

    @Test
    @DisplayName("평균 점수를 계산하고, 피드백 개수를 증가시킨다.")
    void updateProfileWithIncreaseFeedbackCount() {
        Profile profile = new Profile();
        profile.updateProfile(4);
        profile.updateProfile(2);

        float averageRating = profile.getAverageRatingValue();
        float feedbackCount = profile.getFeedbackCount();

        assertThat(averageRating).isEqualTo(3);
        assertThat(feedbackCount).isEqualTo(2);
    }

    @Test
    @DisplayName("평균 점수를 계산할 수 있다.")
    void UpdateProfile() {
        Profile profile = new Profile();
        profile.updateProfile(4);
        profile.updateProfile(2);
        profile.updateProfile(2, 4);

        float averageRating = profile.getAverageRatingValue();
        float feedbackCount = profile.getFeedbackCount();

        assertThat(averageRating).isEqualTo(4);
        assertThat(feedbackCount).isEqualTo(2);
    }
}
