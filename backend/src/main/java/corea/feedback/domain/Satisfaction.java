package corea.feedback.domain;

import lombok.Getter;

@Getter
public enum Satisfaction {
    BAD(1),
    DISAPPOINTED(2),
    AVERAGE(3),
    SATISFIED(4),
    VERY_SATISFIED(5);

    private final int value;

    Satisfaction(int value) {
        this.value = value;
    }

    public boolean isPositive() {
        return this == SATISFIED || this == VERY_SATISFIED;
    }
}
