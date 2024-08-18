package corea.global.aspect.query.dev;

import corea.global.aspect.query.QueryInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Profile({"dev", "local"})
@RequestScope
@Getter
public class DevQueryInfo implements QueryInfo {

    private static final int MIN_WARN_SIZE = 10;
    private Map<String, Integer> data = new HashMap<>();

    public void increaseQueryCount(String query) {
        data.merge(query, 1, Integer::sum);
    }

    public String toFormatString() {
        StringBuilder sb = new StringBuilder();
        data.forEach((key, value) ->
                sb.append(key)
                        .append(" : ")
                        .append(value)
                        .append(System.lineSeparator())
                        .append(System.lineSeparator())
        );
        return sb.toString();
    }

    @Override
    public boolean isExceedQuery() {
        return data.values()
                .stream()
                .reduce(0, Integer::sum) >= MIN_WARN_SIZE;
    }

    @Override
    public void clear() {
        data.clear();
    }

    @Override
    public int getCount() {
        return sum();
    }

    private int sum() {
        return data.values()
                .stream()
                .mapToInt(Integer::intValue)
                .sum();
    }
}
