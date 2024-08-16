package corea.matching.domain;

import corea.participation.domain.Participation;

import java.util.List;

public interface MatchingStrategy {

    List<Pair> matchPairs(List<Participation> participations, int matchingSize);
}
