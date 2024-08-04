package corea.global.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NullHandler {

    private static final List<?> EMPTY = List.of();

    public static <T> List<T> emptyIfNull(List<T> data) {
        return data == null ? (List<T>) EMPTY : data;
    }
}
