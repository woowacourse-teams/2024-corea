package corea.evaluation.domain;

import lombok.Getter;

@Getter
public enum EvaluateClassification {

    REVIEW("re"),
    ANDROID("an"),
    BACKEND("be"),
    FRONTEND("fe");

    private final String expression;

    EvaluateClassification(String expression) {
        this.expression = expression;
    }
}
