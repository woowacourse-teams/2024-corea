package corea.global.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MapHandler {
    public static <T> Stream<T> extractDistinctKeyStreams(Map<? extends T, ?>... maps) {
        return (Stream<T>) Arrays.stream(maps).flatMap(map -> map.keySet().stream())
                .distinct();
    }
}
