package corea.room.domain;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

public enum Classification {

    ALL("all"),
    ANDROID("an"),
    BACKEND("be"),
    FRONTEND("fe");

    private static final Map<String, Classification> CACHED_CLASSIFICATIONS = Arrays.stream(values())
            .collect(toMap(classification -> classification.expression, Function.identity()));

    private final String expression;

    Classification(String expression) {
        this.expression = expression;
    }

    public static Classification from(String expression) {
        if (CACHED_CLASSIFICATIONS.containsKey(expression)) {
            return CACHED_CLASSIFICATIONS.get(expression);
        }
        throw new CoreaException(ExceptionType.NOT_FOUND_ERROR);
    }

    public boolean isAll() {
        return this == ALL;
    }
}
