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

    MAKE_CODE_FOR_THE_PURPOSE("방의 목적에 맞게 코드를 작성했어요.", Satisfaction.SATISFIED, KeywordType.REVIEWEE_FEEDBACK_KEYWORD),
    EASY_TO_UNDERSTAND_THE_CODE("코드를 이해하기 쉬웠어요.", Satisfaction.SATISFIED, KeywordType.REVIEWEE_FEEDBACK_KEYWORD),
    RESPONSE_WAS_FAST("응답이 빨랐어요.", Satisfaction.SATISFIED, KeywordType.REVIEWEE_FEEDBACK_KEYWORD),

    NOT_MAKE_CODE_FOR_THE_PURPOSE("방의 목적에 맞게 코드를 작성하지 않았어요.", Satisfaction.BAD, KeywordType.REVIEWEE_FEEDBACK_KEYWORD),
    HARD_TO_UNDERSTAND_THE_CODE("코드를 이해하기 어려웠어요.", Satisfaction.BAD, KeywordType.REVIEWEE_FEEDBACK_KEYWORD),
    RESPONSE_WAS_SLOW("응답이 느렸어요.", Satisfaction.BAD, KeywordType.REVIEWEE_FEEDBACK_KEYWORD),

    VERY_KIND("매우 친절했어요.", Satisfaction.VERY_SATISFIED, KeywordType.REVIEWER_FEEDBACK_KEYWORD),
    VERY_GOOD_AT_EXPLAINING("설명이 이해가 잘 되었어요.", Satisfaction.VERY_SATISFIED, KeywordType.REVIEWER_FEEDBACK_KEYWORD),
    VERY_HELPFUL("많은 도움이 되었어요.", Satisfaction.VERY_SATISFIED, KeywordType.REVIEWER_FEEDBACK_KEYWORD),
    VERY_REVIEW_WAS_FAST("리뷰가 빨랐어요.", Satisfaction.VERY_SATISFIED, KeywordType.REVIEWER_FEEDBACK_KEYWORD),

    KIND("친절했어요.", Satisfaction.SATISFIED, KeywordType.REVIEWER_FEEDBACK_KEYWORD),
    GOOD_AT_EXPLAINING("설명이 이해가 잘 되었어요.", Satisfaction.SATISFIED, KeywordType.REVIEWER_FEEDBACK_KEYWORD),
    HELPFUL("도움이 되었어요.", Satisfaction.SATISFIED, KeywordType.REVIEWER_FEEDBACK_KEYWORD),
    REVIEW_WAS_FAST("리뷰가 빨랐어요.", Satisfaction.SATISFIED, KeywordType.REVIEWER_FEEDBACK_KEYWORD),

    UNKIND("불친절했어요.", Satisfaction.BAD, KeywordType.REVIEWER_FEEDBACK_KEYWORD),
    BAD_AT_EXPLAINING("설명이 이해가 잘 되지 않았어요.", Satisfaction.BAD, KeywordType.REVIEWER_FEEDBACK_KEYWORD),
    NOT_HELPFUL("도움이 되지 않았어요.", Satisfaction.BAD, KeywordType.REVIEWER_FEEDBACK_KEYWORD),
    REVIEW_WAS_SLOW("리뷰가 느렸어요.", Satisfaction.BAD, KeywordType.REVIEWER_FEEDBACK_KEYWORD),
    ;

    private static final Map<String, FeedbackKeyword> CLASSIFIER = Arrays.stream(FeedbackKeyword.values())
            .collect(Collectors.toMap(Enum::name, Function.identity()));

    private final String message;
    private final Satisfaction satisfaction;
    private final KeywordType keywordType;

    FeedbackKeyword(String message, Satisfaction satisfaction, KeywordType keywordType) {
        this.message = message;
        this.satisfaction = satisfaction;
        this.keywordType = keywordType;
    }

    public static FeedbackKeyword classify(String name) {
        if (CLASSIFIER.containsKey(name)) {
            return CLASSIFIER.get(name);
        }
        throw new CoreaException(ExceptionType.NOT_FOUND_ERROR);
    }

    public boolean isPositive() {
        return this.satisfaction.isPositive();
    }

    private enum KeywordType {
        REVIEWEE_FEEDBACK_KEYWORD, REVIEWER_FEEDBACK_KEYWORD
    }
}
