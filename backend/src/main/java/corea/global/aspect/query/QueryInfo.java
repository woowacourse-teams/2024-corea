package corea.global.aspect.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Profile({"local", "dev"})
@RequestScope
@Getter
public class QueryInfo {

    private static final int MIN_WARN_SIZE = 10;
    private static final String NEWLINE = System.lineSeparator();
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
                        .append(NEWLINE)
        );
        return sb.toString();
    }

    public boolean isExceedQuery() {
        return this.sum() >= MIN_WARN_SIZE;
    }

    public void clear() {
        data.clear();
    }

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
