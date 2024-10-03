package corea.matching.domain;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.member.domain.Member;
import corea.member.repository.MemberRepository;
import corea.participation.domain.Participation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class DynamicSizeMatchingStrategy {

    private final MemberRepository memberRepository;

    public List<Pair> matchPairs(List<Participation> participations) {
        Map<Long, Member> memberCache = participations.stream()
                .map(participation -> memberRepository.findById(participation.getMemberId())
                        .orElseThrow(() -> new CoreaException(ExceptionType.MEMBER_NOT_FOUND)))
                .collect(Collectors.toMap(Member::getId, member -> member));

        List<Pair> pairs = new ArrayList<>();
        generatePairs(participations, pairs, memberCache);

        return pairs;
    }

    private void generatePairs(List<Participation> participations, List<Pair> pairs, Map<Long, Member> memberCache) {
        Map<Participation, Integer> matchCount = participations.stream()
                .collect(Collectors.toMap(participation -> participation, participation -> participation.getMatchingSize() * 2));

        Set<Participation> needToMatch = new HashSet<>(participations);

        while (needToMatch.size() > 1) {
            pairs.addAll(generateRandomPairs(matchCount, needToMatch, memberCache));
            removeMatchedParticipation(matchCount, needToMatch);
        }
    }

    private List<Pair> generateRandomPairs(Map<Participation, Integer> matchCount, Set<Participation> needToMatch, Map<Long, Member> memberCache) {
        List<Participation> shuffledFirst = new ArrayList<>(needToMatch);
        List<Participation> shuffledSecond = new ArrayList<>(needToMatch);

        Collections.shuffle(shuffledFirst);
        do {
            Collections.shuffle(shuffledSecond);
        } while (hasSameValue(shuffledFirst, shuffledSecond));

        return IntStream.range(0, needToMatch.size())
                .mapToObj(i -> makePair(shuffledFirst.get(i), shuffledSecond.get(i), matchCount, memberCache))
                .toList();
    }

    private boolean hasSameValue(List<Participation> shuffledFirst, List<Participation> shuffledSecond) {
        return IntStream.range(0, shuffledFirst.size())
                .anyMatch(i -> shuffledFirst.get(i).equals(shuffledSecond.get(i)));
    }

    private Pair makePair(Participation reviewer, Participation reviewee, Map<Participation, Integer> matchCount, Map<Long, Member> memberCache) {
        matchCount.put(reviewer, matchCount.get(reviewer) - 1);
        matchCount.put(reviewee, matchCount.get(reviewee) - 1);
        return new Pair(memberCache.get(reviewer.getMemberId()), memberCache.get(reviewee.getMemberId()));
    }

    private void removeMatchedParticipation(Map<Participation, Integer> matchCount, Set<Participation> needToMatch) {
        matchCount.entrySet().stream()
                .filter(entry -> entry.getValue() == 0)
                .forEach(entry -> needToMatch.remove(entry.getKey()));
    }
}
