package corea.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AverageRatingTest {

    @Test
    @DisplayName("받은 총 점수를 계산할 수 있다.")
    void calculateTotalEvaluatePoint1() {
        AverageRating averageRating = new AverageRating(4.5f);

        float result = averageRating.calculateTotalEvaluatePoint(4, 5);

        assertThat(result).isEqualTo(23);
    }

    @Test
    @DisplayName("받은 총 점수를 계산할 수 있다.")
    void calculateTotalEvaluatePoint2() {
        AverageRating averageRating = new AverageRating(4.5f);

        float result = averageRating.calculateTotalEvaluatePoint(4, 5, 4);

        assertThat(result).isEqualTo(17);
    }

    @Test
    @DisplayName("받은 총 점수의 평균 값을 구할 수 있다.")
    void calculateAverageRating() {
        AverageRating averageRating = new AverageRating();

        AverageRating result = averageRating.calculateAverageRating(20, 4);

        assertThat(result.getValue()).isEqualTo(5);
    }
}
