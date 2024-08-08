package corea.feedback.domain;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public enum FeedbackKeyword {

    MAKE_CODE_FOR_THE_PURPOSE("방의 목적에 맞게 코드를 잘 작성했어요.", Evaluation.POSITIVE, KeywordType.DEVELOP_FEEDBACK_KEYWORD),
    EASY_TO_UNDERSTAND_THE_CODE("코드를 이해하기 쉬웠어요.", Evaluation.POSITIVE, KeywordType.DEVELOP_FEEDBACK_KEYWORD),
    RESPONSE_FAST("응답 속도가 빨랐어요.", Evaluation.POSITIVE, KeywordType.DEVELOP_FEEDBACK_KEYWORD),

    MAKE_CODE_FOR_THE_LITTLE_PURPOSE("방의 목적에 놓친 부분이 있어요.", Evaluation.NORMAL, KeywordType.DEVELOP_FEEDBACK_KEYWORD),
    NORMAL_TO_UNDERSTAND_THE_CODE("코드를 이해는 했어요.", Evaluation.NORMAL, KeywordType.DEVELOP_FEEDBACK_KEYWORD),
    RESPONSE_NORMAL("응답 속도가 적당했어요.", Evaluation.NORMAL, KeywordType.DEVELOP_FEEDBACK_KEYWORD),

    NOT_MAKE_CODE_FOR_THE_PURPOSE("방의 목적에 맞게 코드를 작성하지 않았어요.", Evaluation.NEGATIVE, KeywordType.DEVELOP_FEEDBACK_KEYWORD),
    HARD_TO_UNDERSTAND_THE_CODE("코드를 이해하기 어려웠어요.", Evaluation.NEGATIVE, KeywordType.DEVELOP_FEEDBACK_KEYWORD),
    RESPONSE_SLOW("응답 속도가 느렸어요.", Evaluation.NEGATIVE, KeywordType.DEVELOP_FEEDBACK_KEYWORD),

    KIND("친절했어요.", Evaluation.POSITIVE, KeywordType.SOCIAL_FEEDBACK_KEYWORD),
    GOOD_AT_EXPLAINING("설명이 이해가 잘 되었어요.", Evaluation.POSITIVE, KeywordType.SOCIAL_FEEDBACK_KEYWORD),
    HELPFUL("매우 도움이 되었어요.", Evaluation.POSITIVE, KeywordType.SOCIAL_FEEDBACK_KEYWORD),
    REVIEW_FAST("리뷰 속도가 빨랐어요.", Evaluation.POSITIVE, KeywordType.SOCIAL_FEEDBACK_KEYWORD),

    NORMAL_AT_EXPLAINING("설명이 이해가 잘 되지 않았어요.", Evaluation.NORMAL, KeywordType.SOCIAL_FEEDBACK_KEYWORD),
    NORMALFUL("도움이 되었어요.", Evaluation.NORMAL, KeywordType.SOCIAL_FEEDBACK_KEYWORD),
    REVIEW_NORMAL("리뷰 속도가 적당했어요.", Evaluation.NORMAL, KeywordType.SOCIAL_FEEDBACK_KEYWORD),

    UNKIND("불친절했어요.", Evaluation.NEGATIVE, KeywordType.SOCIAL_FEEDBACK_KEYWORD),
    BAD_AT_EXPLAINING("설명이 부족해요.", Evaluation.NEGATIVE, KeywordType.SOCIAL_FEEDBACK_KEYWORD),
    NOT_HELPFUL("도움이 되지 않았어요.", Evaluation.NEGATIVE, KeywordType.SOCIAL_FEEDBACK_KEYWORD),
    REVIEW_SLOW("리뷰 속도가 느렸어요.", Evaluation.NEGATIVE, KeywordType.SOCIAL_FEEDBACK_KEYWORD),
    ;

    private static final Map<String, FeedbackKeyword> CLASSIFIER = Arrays.stream(FeedbackKeyword.values())
            .collect(Collectors.toMap(Enum::name, Function.identity()));

    private final String message;
    private final Evaluation evaluation;
    private final KeywordType keywordType;

    FeedbackKeyword(String message, Evaluation evaluation, KeywordType keywordType) {
        this.message = message;
        this.evaluation = evaluation;
        this.keywordType = keywordType;
    }

    public static FeedbackKeyword classify(String name) {
        if (CLASSIFIER.containsKey(name)) {
            return CLASSIFIER.get(name);
        }
        throw new CoreaException(ExceptionType.NOT_FOUND_ERROR);
    }

    public boolean isPositive() {
        return this.evaluation.isPositive();
    }

    private enum Evaluation {
        POSITIVE, NORMAL, NEGATIVE;

        public boolean isPositive() {
            return this == POSITIVE;
        }
    }

    private enum KeywordType {
        SOCIAL_FEEDBACK_KEYWORD, DEVELOP_FEEDBACK_KEYWORD
    }
}
