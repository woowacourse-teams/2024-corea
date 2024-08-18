package corea.global.aspect.query.inspect;

import corea.global.aspect.query.QueryInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Profile({"inspect", "test"})
@Getter
public class InspectQueryInfo implements QueryInfo {

    private static final int MIN_WARN_SIZE = 10;
    private Map<String, Integer> data = new HashMap<>();

    @Override
    public void increaseQueryCount(String query) {
        data.merge(query, 1, Integer::sum);
    }

    @Override
    public String toFormatString() {
        StringBuilder sb = new StringBuilder();
        data.forEach((key, value) ->
                sb.append(key)
                        .append(" : ")
                        .append(value)
                        .append(System.lineSeparator())
        );
        return sb.toString();
    }

    @Override
    public int getCount() {
        return sum();
    }

    @Override
    public boolean isExceedQuery() {
        return sum() >= MIN_WARN_SIZE;
    }

    @Override
    public void clear() {
        data = new HashMap<>();
    }

    private int sum() {
        return data.values()
                .stream()
                .mapToInt(Integer::intValue)
                .sum();
    }
}
