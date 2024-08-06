package corea.global.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NullHandler {

    private static final List<?> EMPTY = Collections.emptyList();

    public static <T> List<T> emptyListIfNull(List<T> data) {
        return data == null ? (List<T>) EMPTY : data;
    }
}
