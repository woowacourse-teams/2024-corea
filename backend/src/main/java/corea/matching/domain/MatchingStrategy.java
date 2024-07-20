package corea.matching.domain;

import java.util.List;

public interface MatchingStrategy {

    List<Pair> matchPairs(List<Long> memberIds, int matchingSize);
}
