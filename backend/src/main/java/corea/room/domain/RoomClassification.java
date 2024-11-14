package corea.room.domain;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

public enum RoomClassification {

    ALL("all"),
    ANDROID("an"),
    BACKEND("be"),
    FRONTEND("fe");

    private static final Map<String, RoomClassification> CACHED_CLASSIFICATIONS = Arrays.stream(values())
            .collect(toMap(classification -> classification.expression, Function.identity()));

    private final String expression;

    RoomClassification(String expression) {
        this.expression = expression;
    }

    public static RoomClassification from(String expression) {
        if (CACHED_CLASSIFICATIONS.containsKey(expression)) {
            return CACHED_CLASSIFICATIONS.get(expression);
        }
        throw new CoreaException(ExceptionType.NOT_FOUND_ERROR);
    }

    public boolean isAll() {
        return this == ALL;
    }

    public boolean isNotAll() {
        return this != ALL;
    }
}
