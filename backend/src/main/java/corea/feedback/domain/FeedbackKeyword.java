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

    POSITIVE_DEVELOP_FEEDBACK_1("코드를 이해하기 쉬웠어요", Evaluation.POSITIVE, KeywordType.DEVELOP_FEEDBACK_KEYWORD),
    POSITIVE_DEVELOP_FEEDBACK_2("코드의 가독성이 뛰어나요", Evaluation.POSITIVE, KeywordType.DEVELOP_FEEDBACK_KEYWORD),
    POSITIVE_DEVELOP_FEEDBACK_3("코드가 일관성 있게 작성되어 있어요", Evaluation.POSITIVE, KeywordType.DEVELOP_FEEDBACK_KEYWORD),
    POSITIVE_DEVELOP_FEEDBACK_4("변수 이름이 명확하고 직관적이에요", Evaluation.POSITIVE, KeywordType.DEVELOP_FEEDBACK_KEYWORD),
    POSITIVE_DEVELOP_FEEDBACK_5("메서드 및 클래스 이름이 명확해요", Evaluation.POSITIVE, KeywordType.DEVELOP_FEEDBACK_KEYWORD),
    POSITIVE_DEVELOP_FEEDBACK_6("기능이 명확하게 잘 구현되어 있어요", Evaluation.POSITIVE, KeywordType.DEVELOP_FEEDBACK_KEYWORD),
    POSITIVE_DEVELOP_FEEDBACK_7("코드의 구조가 논리적이에요", Evaluation.POSITIVE, KeywordType.DEVELOP_FEEDBACK_KEYWORD),
    POSITIVE_DEVELOP_FEEDBACK_8("코드 재사용성이 높아요", Evaluation.POSITIVE, KeywordType.DEVELOP_FEEDBACK_KEYWORD),
    POSITIVE_DEVELOP_FEEDBACK_9("예외 처리가 잘 되어 있어 안정적이에요", Evaluation.POSITIVE, KeywordType.DEVELOP_FEEDBACK_KEYWORD),
    POSITIVE_DEVELOP_FEEDBACK_10("문서화가 잘 되어 있어요", Evaluation.POSITIVE, KeywordType.DEVELOP_FEEDBACK_KEYWORD),
    POSITIVE_DEVELOP_FEEDBACK_11("테스트가 잘 작성되어 있어요", Evaluation.POSITIVE, KeywordType.DEVELOP_FEEDBACK_KEYWORD),
    POSITIVE_DEVELOP_FEEDBACK_12("코드가 잘 최적화되어 성능이 좋아요", Evaluation.POSITIVE, KeywordType.DEVELOP_FEEDBACK_KEYWORD),
    POSITIVE_DEVELOP_FEEDBACK_13("다양한 에지 케이스를 고려한 코드에요", Evaluation.POSITIVE, KeywordType.DEVELOP_FEEDBACK_KEYWORD),
    POSITIVE_DEVELOP_FEEDBACK_14("응답 속도가 빨랐어요", Evaluation.POSITIVE, KeywordType.DEVELOP_FEEDBACK_KEYWORD),

    NORMAL_DEVELOP_FEEDBACK_1("코드가 다소 복잡하게 느껴져요", Evaluation.NORMAL, KeywordType.DEVELOP_FEEDBACK_KEYWORD),
    NORMAL_DEVELOP_FEEDBACK_2("가독성을 높이기 위한 리팩토링이 필요해요", Evaluation.NORMAL, KeywordType.DEVELOP_FEEDBACK_KEYWORD),
    NORMAL_DEVELOP_FEEDBACK_3("기능은 잘 작동하지만 코드가 길어요", Evaluation.NORMAL, KeywordType.DEVELOP_FEEDBACK_KEYWORD),
    NORMAL_DEVELOP_FEEDBACK_4("코드의 일부가 중복되어 있어요", Evaluation.NORMAL, KeywordType.DEVELOP_FEEDBACK_KEYWORD),
    NORMAL_DEVELOP_FEEDBACK_5("기능이 제한적이어서 확장성이 떨어져요", Evaluation.NORMAL, KeywordType.DEVELOP_FEEDBACK_KEYWORD),
    NORMAL_DEVELOP_FEEDBACK_6("테스트 케이스가 몇 개 더 필요해요", Evaluation.NORMAL, KeywordType.DEVELOP_FEEDBACK_KEYWORD),
    NORMAL_DEVELOP_FEEDBACK_7("예외 처리 부분이 부족해 보여요", Evaluation.NORMAL, KeywordType.DEVELOP_FEEDBACK_KEYWORD),
    NORMAL_DEVELOP_FEEDBACK_8("성능 개선이 필요한 부분이 있어요", Evaluation.NORMAL, KeywordType.DEVELOP_FEEDBACK_KEYWORD),
    NORMAL_DEVELOP_FEEDBACK_9("문서화가 부족한 부분이 있어요", Evaluation.NORMAL, KeywordType.DEVELOP_FEEDBACK_KEYWORD),
    NORMAL_DEVELOP_FEEDBACK_10("코드가 기본적인 구조는 갖추고 있어요", Evaluation.NORMAL, KeywordType.DEVELOP_FEEDBACK_KEYWORD),
    NORMAL_DEVELOP_FEEDBACK_11("코드의 일관성이 조금 부족한 것 같아요", Evaluation.NORMAL, KeywordType.DEVELOP_FEEDBACK_KEYWORD),
    NORMAL_DEVELOP_FEEDBACK_12("에러 메시지가 부족해요", Evaluation.NORMAL, KeywordType.DEVELOP_FEEDBACK_KEYWORD),
    NORMAL_DEVELOP_FEEDBACK_13("응답 속도가 적당했어요", Evaluation.NORMAL, KeywordType.DEVELOP_FEEDBACK_KEYWORD),

    NEGATIVE_DEVELOP_FEEDBACK_1("코드를 이해하기 어려웠어요", Evaluation.NEGATIVE, KeywordType.DEVELOP_FEEDBACK_KEYWORD),
    NEGATIVE_DEVELOP_FEEDBACK_2("코드의 가독성이 떨어져요", Evaluation.NEGATIVE, KeywordType.DEVELOP_FEEDBACK_KEYWORD),
    NEGATIVE_DEVELOP_FEEDBACK_4("코드가 일관성 있게 작성되지 않았어요", Evaluation.NEGATIVE, KeywordType.DEVELOP_FEEDBACK_KEYWORD),
    NEGATIVE_DEVELOP_FEEDBACK_3("변수 이름이 불명확하고 직관적이지 않아요", Evaluation.NEGATIVE, KeywordType.DEVELOP_FEEDBACK_KEYWORD),
    NEGATIVE_DEVELOP_FEEDBACK_5("메서드 및 클래스 이름이 명확하지 않아요", Evaluation.NEGATIVE, KeywordType.DEVELOP_FEEDBACK_KEYWORD),
    NEGATIVE_DEVELOP_FEEDBACK_6("기능이 명확하게 구현되지 않았어요", Evaluation.NEGATIVE, KeywordType.DEVELOP_FEEDBACK_KEYWORD),
    NEGATIVE_DEVELOP_FEEDBACK_7("코드의 구조가 비논리적이에요", Evaluation.NEGATIVE, KeywordType.DEVELOP_FEEDBACK_KEYWORD),
    NEGATIVE_DEVELOP_FEEDBACK_8("코드 재사용성이 낮아요", Evaluation.NEGATIVE, KeywordType.DEVELOP_FEEDBACK_KEYWORD),
    NEGATIVE_DEVELOP_FEEDBACK_9("예외 처리가 부족해 불안정해요", Evaluation.NEGATIVE, KeywordType.DEVELOP_FEEDBACK_KEYWORD),
    NEGATIVE_DEVELOP_FEEDBACK_10("문서화가 부족해요", Evaluation.NEGATIVE, KeywordType.DEVELOP_FEEDBACK_KEYWORD),
    NEGATIVE_DEVELOP_FEEDBACK_11("테스트가 부족해요", Evaluation.NEGATIVE, KeywordType.DEVELOP_FEEDBACK_KEYWORD),
    NEGATIVE_DEVELOP_FEEDBACK_12("코드가 최적화되지 않아 성능이 떨어져요", Evaluation.NEGATIVE, KeywordType.DEVELOP_FEEDBACK_KEYWORD),
    NEGATIVE_DEVELOP_FEEDBACK_13("다양한 에지 케이스를 고려하지 않은 코드에요", Evaluation.NEGATIVE, KeywordType.DEVELOP_FEEDBACK_KEYWORD),
    NEGATIVE_DEVELOP_FEEDBACK_14("응답 속도가 느렸어요", Evaluation.NEGATIVE, KeywordType.DEVELOP_FEEDBACK_KEYWORD),

    KIND("친절했어요", Evaluation.POSITIVE, KeywordType.SOCIAL_FEEDBACK_KEYWORD),
    GOOD_AT_EXPLAINING("설명이 이해가 잘 되었어요", Evaluation.POSITIVE, KeywordType.SOCIAL_FEEDBACK_KEYWORD),
    HELPFUL("매우 도움이 되었어요", Evaluation.POSITIVE, KeywordType.SOCIAL_FEEDBACK_KEYWORD),
    REVIEW_FAST("리뷰 속도가 빨랐어요", Evaluation.POSITIVE, KeywordType.SOCIAL_FEEDBACK_KEYWORD),

    NORMAL_AT_EXPLAINING("설명이 이해가 잘 되지 않았어요", Evaluation.NORMAL, KeywordType.SOCIAL_FEEDBACK_KEYWORD),
    NORMALFUL("도움이 되었어요", Evaluation.NORMAL, KeywordType.SOCIAL_FEEDBACK_KEYWORD),
    REVIEW_NORMAL("리뷰 속도가 적당했어요", Evaluation.NORMAL, KeywordType.SOCIAL_FEEDBACK_KEYWORD),

    UNKIND("불친절했어요", Evaluation.NEGATIVE, KeywordType.SOCIAL_FEEDBACK_KEYWORD),
    BAD_AT_EXPLAINING("설명이 부족해요", Evaluation.NEGATIVE, KeywordType.SOCIAL_FEEDBACK_KEYWORD),
    NOT_HELPFUL("도움이 되지 않았어요", Evaluation.NEGATIVE, KeywordType.SOCIAL_FEEDBACK_KEYWORD),
    REVIEW_SLOW("리뷰 속도가 느렸어요", Evaluation.NEGATIVE, KeywordType.SOCIAL_FEEDBACK_KEYWORD),
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
