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

@Component
@RequiredArgsConstructor
public class DynamicSizeMatchingStrategy {

    private final MatchingStrategy strategy;

    private final MemberRepository memberRepository;

    public List<Pair> matchPairs(List<Participation> participations, int roomMatchingSize) {
        List<Participation> participationWithoutReviewer = participations.stream()
                .filter(participation -> !participation.getMemberRole().isReviewer()).toList();
        List<Pair> pairs = strategy.matchPairs(participationWithoutReviewer, roomMatchingSize);
        additionalMatching(participations, participationWithoutReviewer, pairs, roomMatchingSize);
        return pairs;
    }

    private void additionalMatching(List<Participation> participations, List<Participation> participationsWithoutReviewer, List<Pair> pairs, int roomMatchingSize) {
        Map<Long, Member> memberCache = participations.stream()
                .map(participation -> memberRepository.findById(participation.getMemberId())
                        .orElseThrow(() -> new CoreaException(ExceptionType.MEMBER_NOT_FOUND)))
                .collect(Collectors.toMap(Member::getId, Function.identity()));

        int max = participations.stream().map(Participation::getMatchingSize).mapToInt(x -> x).max().orElseThrow(() -> new CoreaException(ExceptionType.MEMBER_NOT_FOUND));

        participations = optimizeParticipation(participations, roomMatchingSize);
        participationsWithoutReviewer = optimizeParticipation(participationsWithoutReviewer, roomMatchingSize);

        int count = roomMatchingSize + 1;

        while (count <= max && !participationsWithoutReviewer.isEmpty()) {
            additionalMatchingCycle(participations, participationsWithoutReviewer, pairs, memberCache);
            participations = optimizeParticipation(participations, count);
            participationsWithoutReviewer = optimizeParticipation(participationsWithoutReviewer, count);
            count += 1;
        }
    }

    private void additionalMatchingCycle(List<Participation> participations, List<Participation> participationWithoutReviewer, List<Pair> pairs, Map<Long, Member> memberCache) {
        List<Long> reviewerIds = new ArrayList<>(participations.stream().map(Participation::getMemberId).toList());
        Collections.shuffle(reviewerIds);
        ArrayDeque<Long> reviewerShuffledIds = new ArrayDeque<>(reviewerIds);
        List<Long> revieweeIds = new ArrayList<>(participationWithoutReviewer.stream().map(Participation::getMemberId).toList());
        Collections.shuffle(revieweeIds);
        ArrayDeque<Long> revieweeShuffledIds = new ArrayDeque<>(revieweeIds);

        while (!revieweeShuffledIds.isEmpty()) {
            long reviewerId = reviewerShuffledIds.pollFirst();
            long revieweeId = revieweeShuffledIds.pollFirst();

            int count = 0;
            int originSize = reviewerShuffledIds.size();

            while (count++ < originSize && !possiblePair(reviewerId, revieweeId, pairs)) {
                reviewerShuffledIds.add(reviewerId);
                reviewerId = reviewerShuffledIds.pollFirst();
            }

            if (count <= originSize) {
                pairs.add(new Pair(memberCache.get(reviewerId), memberCache.get(revieweeId)));
            }
        }
    }

    private boolean possiblePair(long reviewerId, long revieweeId, List<Pair> pairs) {
        if (reviewerId == revieweeId) {
            return false;
        }

        return pairs.stream().noneMatch(
                pair -> pair.getDeliver().getId().equals(reviewerId) &&
                        pair.getReceiver().getId().equals(revieweeId));
    }

    private List<Participation> optimizeParticipation(List<Participation> participations, int count) {
        return participations.stream()
                .filter(participation -> participation.getMatchingSize() > count)
                .toList();
    }
}
