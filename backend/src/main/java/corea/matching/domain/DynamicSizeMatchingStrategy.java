package corea.matching.domain;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.member.domain.Member;
import corea.member.repository.MemberRepository;
import corea.participation.domain.Participation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
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
                .collect(Collectors.toMap(Member::getId, Function.identity()));

        List<Pair> pairs = new ArrayList<>();
        generatePairs(participations, pairs, memberCache);

        return pairs;
    }

    private void generatePairs(List<Participation> participations, List<Pair> pairs, Map<Long, Member> memberCache) {
        Map<Participation, Integer> reviewerCount = participations.stream()
                .collect(Collectors.toMap(Function.identity(), Participation::getMatchingSize));
        Map<Participation, Integer> revieweeCount = participations.stream()
                .collect(Collectors.toMap(Function.identity(), this::getRevieweeMatchingCount));

        Set<Participation> needToMatch = new HashSet<>(participations);

        while (needToMatch.size() > 1) {
            pairs.addAll(generateRandomPairs(reviewerCount, revieweeCount, needToMatch, memberCache));
            removeMatchedParticipation(reviewerCount, revieweeCount, needToMatch);
        }
    }

    private int getRevieweeMatchingCount(Participation participation) {
        if (participation.getMemberRole().isReviewer()) {
            return 0;
        }
        return participation.getMatchingSize();
    }

    private List<Pair> generateRandomPairs(Map<Participation, Integer> reviewerCount, Map<Participation, Integer> revieweeCount, Set<Participation> needToMatch, Map<Long, Member> memberCache) {
        List<Participation> shuffledReviewer = new ArrayList<>(needToMatch);
        List<Participation> shuffledReviewee = new ArrayList<>(needToMatch);

        Collections.shuffle(shuffledReviewer);
        do {
            Collections.shuffle(shuffledReviewee);
        } while (hasSameValue(shuffledReviewer, shuffledReviewee));

        return IntStream.range(0, needToMatch.size())
                .filter(i -> !shuffledReviewee.get(i).getMemberRole().isReviewer())
                .mapToObj(i -> makePair(shuffledReviewer.get(i), shuffledReviewee.get(i), reviewerCount, revieweeCount, memberCache))
                .toList();
    }

    private boolean hasSameValue(List<Participation> shuffledFirst, List<Participation> shuffledSecond) {
        return IntStream.range(0, shuffledFirst.size())
                .anyMatch(i -> shuffledFirst.get(i).equals(shuffledSecond.get(i)));
    }

    private Pair makePair(Participation reviewer, Participation reviewee, Map<Participation, Integer> reviewerCount, Map<Participation, Integer> revieweeCount, Map<Long, Member> memberCache) {
        reviewerCount.put(reviewer, reviewerCount.get(reviewer) - 1);
        revieweeCount.put(reviewee, revieweeCount.get(reviewee) - 1);
        return new Pair(memberCache.get(reviewer.getMemberId()), memberCache.get(reviewee.getMemberId()));
    }

    private void removeMatchedParticipation(Map<Participation, Integer> reviewerCount, Map<Participation, Integer> revieeweeCount, Set<Participation> needToMatch) {
        reviewerCount.entrySet().stream()
                .filter(entry -> entry.getValue() == 0)
                .forEach(entry -> needToMatch.remove(entry.getKey()));
        revieeweeCount.entrySet().stream()
                .filter(entry -> entry.getValue() == 0 && !entry.getKey().getMemberRole().isReviewer())
                .forEach(entry -> needToMatch.remove(entry.getKey()));
    }
}
